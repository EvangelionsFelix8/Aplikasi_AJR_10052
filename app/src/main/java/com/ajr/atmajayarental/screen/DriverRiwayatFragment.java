package com.ajr.atmajayarental.screen;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.adapters.RiwayatAdapter;
import com.ajr.atmajayarental.adapters.RiwayatDriverAdapter;
import com.ajr.atmajayarental.api.CustomerApi;
import com.ajr.atmajayarental.api.DriverApi;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.RiwayatTrans;
import com.ajr.atmajayarental.models.RiwayatTransResponse;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.ajr.atmajayarental.preferences.DriverPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DriverRiwayatFragment extends Fragment {

    ArrayList<RiwayatTrans> listRiwayat;

    private SwipeRefreshLayout srRiwayat;
    private RecyclerView rvRiwayat;
    private RiwayatDriverAdapter adapter;
    private SearchView svRiwayat;
    private LinearProgressIndicator layoutLoading;
    public RequestQueue queue;
    RecyclerView.LayoutManager layoutManager;
    Configuration newConfig;
    private DriverPreferences driverPreferences;
    private Driver driver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_riwayat,container,false);
        rvRiwayat = view.findViewById(R.id.rvRiwayatDriver);
        layoutLoading = view.findViewById(R.id.linearLoading);
        svRiwayat = view.findViewById(R.id.searchRiwayatDriver);
        srRiwayat = view.findViewById(R.id.srRiwayatDriver);

        driverPreferences = new DriverPreferences(getContext());
        driver = driverPreferences.getDriverLogin();

        listRiwayat = new ArrayList<>();

        svRiwayat.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        srRiwayat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRiwayatList();
            }
        });

        rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new RiwayatDriverAdapter(getContext(), listRiwayat);
        rvRiwayat.setAdapter(adapter);
        getRiwayatList();
        return view;
    }

    public void getRiwayatList(){
        setLoading(true);
        srRiwayat.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                DriverApi.SHOW_RIWAYAT + driver.getId_driver(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RiwayatTransResponse riwayatTransResponse = gson.fromJson(response, RiwayatTransResponse.class);
                if(riwayatTransResponse.getRiwayatTransList().size() > 0){
                    adapter.setRiwayatTransListList(riwayatTransResponse.getRiwayatTransList());
                    adapter.getFilter().filter(svRiwayat.getQuery());
                    Toast toast = Toast.makeText(getContext(), riwayatTransResponse.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                    setLoading(false);
                    srRiwayat.setRefreshing(false);
                }
                else {
                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                    View emptyView = layoutInflater.inflate(R.layout.empty_layout, null);
                    final android.app.AlertDialog alertDialog = new AlertDialog
                            .Builder(emptyView.getContext()).create();
                    alertDialog.setView(emptyView);
                    alertDialog.show();
                    svRiwayat.setVisibility(View.GONE);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.layout_fragment, new EmptyFragmentLayout())
                            .commit();
                    setLoading(false);
                    srRiwayat.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srRiwayat.setRefreshing(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
//                headers.put("Authorization", "Bearer "+ userPreferences.getUserLogin().getAccessToken());  //nanti ini token ambil dari userPreference
                return headers;
            }
        };
//        queue.add(stringRequest);
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    // Fungsi untuk menampilkan layout loading
    private void setLoading(boolean isLoading) {
        if (isLoading) {
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = new GridLayoutManager(getContext(), 2);
            rvRiwayat.setLayoutManager(layoutManager);
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(getContext(), 1);
            rvRiwayat.setLayoutManager(layoutManager);
        }
    }
}