package com.ajr.atmajayarental.screen;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.api.CustomerApi;
import com.ajr.atmajayarental.api.DriverApi;
import com.ajr.atmajayarental.api.MobilApi;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.CustomerResponse;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.DriverResponse;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.ajr.atmajayarental.preferences.DriverPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DriverProfileFragment extends Fragment {

    private TextView textNama, textEmail, textStatusKetersediaan, textNomorTelepon,
            textTanggalLahir, textJenisKelamin, textAlamat, textInggris;
    private ImageView fotoDriver;
    CardView cardStatus;
    private ImageButton btnEdit;
    private DriverPreferences driverPreferences;
    private Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textNama = view.findViewById(R.id.textNamaDriver);
        textEmail = view.findViewById(R.id.textEmailDriver);
        textStatusKetersediaan = view.findViewById(R.id.textStatusTersedia);
        textNomorTelepon = view.findViewById(R.id.textTeleponDriver);
        textTanggalLahir = view.findViewById(R.id.textTanggalDriver);
        textJenisKelamin = view.findViewById(R.id.textKelaminDriver);
        textAlamat = view.findViewById(R.id.textAlamatDriver);
        textInggris = view.findViewById(R.id.textBahasaInggris);
        btnEdit = view.findViewById(R.id.btnEdit);
        cardStatus = view.findViewById(R.id.cardStatusD);
        fotoDriver = view.findViewById(R.id.fotoDriver);

        driverPreferences = new DriverPreferences(getContext());
        driver = driverPreferences.getDriverLogin();

        showDriverData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "HELLO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDriverData(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                DriverApi.GET_DRIVER_DATA + driver.getId_driver(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);
                Driver foundedDriverData = driverResponse.getDriver();
                Glide.with(getContext())
                        .load(DriverApi.BASE_URL_FOTO + "storage/"+ foundedDriverData.getUrl_foto_driver())
                        .placeholder(R.drawable.no_image)
                        .into(fotoDriver);
                textNama.setText(foundedDriverData.getNama_driver());
                textEmail.setText(foundedDriverData.getEmail_driver());
                textStatusKetersediaan.setText(foundedDriverData.getStatus_ketersediaan_driver());
                if(foundedDriverData.getStatus_ketersediaan_driver().equalsIgnoreCase("Tersedia")){
                    cardStatus.setCardBackgroundColor(Color.parseColor("#FFC8E6C9"));
                    textStatusKetersediaan.setTextColor(Color.parseColor("#1B5E20"));
                }
                else if(foundedDriverData.getStatus_ketersediaan_driver().equalsIgnoreCase("Tidak Tersedia")){
                    cardStatus.setCardBackgroundColor(Color.parseColor("#FFB71C1C"));
                    textStatusKetersediaan.setTextColor(Color.parseColor("#FFFFCDD2"));
                }
                textNomorTelepon.setText(foundedDriverData.getNo_telp_driver());
                textTanggalLahir.setText(foundedDriverData.getTanggal_lahir_driver());
                textJenisKelamin.setText(foundedDriverData.getJenis_kelamin());
                textAlamat.setText(foundedDriverData.getAlamat_driver());

                if(foundedDriverData.isEnglish() == 1){
                    textInggris.setText("Bisa");
                }
                else{
                    textInggris.setText("Tidak Bisa");
                }
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
                headers.put("Authorization", "Bearer "+ driverPreferences.getDriverLogin().getAccess_token());
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}