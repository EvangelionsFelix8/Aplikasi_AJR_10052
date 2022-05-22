package com.ajr.atmajayarental.screen;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.adapters.MobilAdapter;
import com.ajr.atmajayarental.api.MobilApi;
import com.ajr.atmajayarental.models.Mobil;
import com.ajr.atmajayarental.models.MobilResponse;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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

public class MobilFragment extends Fragment {

    ArrayList<Mobil> listMobil;

    private SwipeRefreshLayout srMobil;
    private RecyclerView rvMobil;
    private MobilAdapter adapter;
    private SearchView svMobil;
    private LinearProgressIndicator layoutLoading;
    RecyclerView.LayoutManager layoutManager;
//    public RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobil,container,false);
        rvMobil = view.findViewById(R.id.rvMobil);
        layoutLoading = view.findViewById(R.id.linearLoading);
        svMobil = view.findViewById(R.id.searchMobil);
        srMobil = view.findViewById(R.id.srMobil);

        svMobil.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        srMobil.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getActiveMobil();
            }
        });

        rvMobil.setLayoutManager(new LinearLayoutManager(getContext()));

        listMobil = new ArrayList<>();
        adapter = new MobilAdapter(getContext(),listMobil);
        rvMobil.setAdapter(adapter);
        getActiveMobil();
        return view;
    }

    public void getActiveMobil(){
        setLoading(true);
        srMobil.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MobilApi.GET_ACTIVE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                MobilResponse mobilResponse = gson.fromJson(response, MobilResponse.class);
                adapter.setMobilList(mobilResponse.getMobilList());
                rvMobil.setAdapter(adapter);
                adapter.getFilter().filter(svMobil.getQuery());
                Toast toast = Toast.makeText(getContext(), mobilResponse.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
                setLoading(false);
                srMobil.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srMobil.setRefreshing(false);
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
            rvMobil.setLayoutManager(layoutManager);
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(getContext(), 1);
            rvMobil.setLayoutManager(layoutManager);
        }
    }
}