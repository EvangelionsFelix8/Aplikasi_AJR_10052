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
import com.ajr.atmajayarental.adapters.PromoAdapter;
import com.ajr.atmajayarental.api.CustomerApi;
import com.ajr.atmajayarental.models.Promo;
import com.ajr.atmajayarental.models.PromoResponse;
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

public class PromoFragment extends Fragment {

    ArrayList<Promo> listPromo;

    private SwipeRefreshLayout srPromo;
    private RecyclerView rvPromo;
    private PromoAdapter adapter;
    private SearchView svPromo;
    private LinearProgressIndicator layoutLoading;
    public RequestQueue queue;
    RecyclerView.LayoutManager layoutManager;
    Configuration newConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo,container,false);
        rvPromo = view.findViewById(R.id.rvPromo);
        layoutLoading = view.findViewById(R.id.linearLoading);
        svPromo = view.findViewById(R.id.searchPromo);
        srPromo = view.findViewById(R.id.srPromo);


        svPromo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        srPromo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getActivePromo();
            }
        });

//        userPreferences = new UserPreferences(this.getContext());
//        user = userPreferences.getUserLogin();
//        rvPromo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.placeholder));

        rvPromo.setLayoutManager(new LinearLayoutManager(getContext()));

        listPromo = new ArrayList<>();
        adapter = new PromoAdapter(getContext(),listPromo);
        rvPromo.setAdapter(adapter);

        getActivePromo();
        return view;
    }

    public void getActivePromo(){
        setLoading(true);
        srPromo.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CustomerApi.GET_ACTIVE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                PromoResponse promoResponse = gson.fromJson(response, PromoResponse.class);
                adapter.setPromoList(promoResponse.getPromoList());
                rvPromo.setAdapter(adapter);
                adapter.getFilter().filter(svPromo.getQuery());
                Toast.makeText(getContext(), promoResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
                srPromo.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srPromo.setRefreshing(false);
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
            rvPromo.setLayoutManager(layoutManager);
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(getContext(), 1);
            rvPromo.setLayoutManager(layoutManager);
        }
    }
}