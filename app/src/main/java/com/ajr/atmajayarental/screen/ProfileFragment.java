package com.ajr.atmajayarental.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.api.CustomerApi;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.CustomerResponse;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private TextView textNama, textEmail, textNomorTelepon, textTanggalLahir, textJenisKelamin, textAlamat;
    private ImageButton btnEdit;
    private CustomerPreferences customerPreferences;
    private Customer customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textNama = view.findViewById(R.id.textNama);
        textEmail = view.findViewById(R.id.textEmail);
        textNomorTelepon = view.findViewById(R.id.textNomorTelepon);
        textTanggalLahir = view.findViewById(R.id.textTanggalLahir);
        textJenisKelamin = view.findViewById(R.id.textJenisKelamin);
        textAlamat = view.findViewById(R.id.textAlamat);
        btnEdit = view.findViewById(R.id.btnEdit);

        customerPreferences = new CustomerPreferences(getContext());
        customer = customerPreferences.getCustomerLogin();

        showCustomerData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "HELLO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomerData(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                CustomerApi.GET_CUSTOMER_DATA + customer.getId_customer(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                CustomerResponse customerResponse = gson.fromJson(response, CustomerResponse.class);
                Customer foundedCustomerData = customerResponse.getCustomer();
                textNama.setText(foundedCustomerData.getNama_customer());
                textEmail.setText(foundedCustomerData.getEmail_customer());
                textNomorTelepon.setText(foundedCustomerData.getNo_telp_customer());
                textTanggalLahir.setText(foundedCustomerData.getTanggal_lahir_customer());
                textJenisKelamin.setText(foundedCustomerData.getJenis_kelamin());
                textAlamat.setText(foundedCustomerData.getAlamat_customer());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+ customerPreferences.getCustomerLogin().getAccess_token());
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}