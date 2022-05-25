package com.ajr.atmajayarental.screen;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.Request.Method.PUT;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.api.DriverApi;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.DriverResponse;
import com.ajr.atmajayarental.models.RerataRating;
import com.ajr.atmajayarental.models.RerataRatingResponse;
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
    private Button btnTersedia, btnTidakTersedia;
    private DriverPreferences driverPreferences;
    private Driver driver;
    private RatingBar rerataRating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_profile, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Driver driverGet = data.getParcelableExtra("getEdit");
                textNama.setText(driverGet.getNama_driver());
                textEmail.setText(driverGet.getEmail_driver());
                textNomorTelepon.setText(driverGet.getNo_telp_driver());
                textAlamat.setText(driverGet.getAlamat_driver());
                if(driverGet.isEnglish() == 1){
                    textInggris.setText("Bisa");
                }
                else{
                    textInggris.setText("Tidak Bisa");
                }
                changeFragment(new DriverProfileFragment());
            }
        }
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
        rerataRating = view.findViewById(R.id.rataRating);

        driverPreferences = new DriverPreferences(getContext());
        driver = driverPreferences.getDriverLogin();

        showDriverData();
        showDriverRating();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateProfile = new Intent(getContext(), UpdateProfileActivity.class);
//                Mengubah Objek pegawai menjadi format JSON string dengan GSON
                Gson gson = new Gson();
                String strProfile = gson.toJson(driver);

//                Menyisipkan data json string ke intent
                updateProfile.putExtra("editProfile", strProfile);

                startActivityForResult(updateProfile, 1);
//                getContext().startA(updateProfile);
            }
        });
        
        cardStatus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View viewStatus = layoutInflater.inflate(R.layout.layout_status_ketersediaan, null);

                final AlertDialog alertDialog = new AlertDialog.Builder(viewStatus.getContext()).create();
                btnTersedia = viewStatus.findViewById(R.id.btnTersedia);
                btnTidakTersedia = viewStatus.findViewById(R.id.btnTidakTer);
                btnTersedia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialog("Apakah Yakin ingin mengubah Status Menjadi Tersedia?", alertDialog, 1);
                    }
                });

                btnTidakTersedia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialog("Apakah Yakin ingin mengubah Status Menjadi Tidak Tersedia?", alertDialog, 2);
                    }
                });
                alertDialog.setView(viewStatus);
                alertDialog.show();
                return false;
            }
        });
    }

    public void showAlertDialog(String message, AlertDialog alertDialog, int kode){
        new AlertDialog.Builder(getContext())
                .setTitle("WARNING")
                .setMessage(message)

                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(kode == 1){
                            updateStatus("Tersedia");
                        }
                        else if(kode == 2){
                            updateStatus("Tidak Tersedia");
                        }
                        alertDialog.dismiss();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
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
                        .load(DriverApi.BASE_URL_FOTO + foundedDriverData.getUrl_foto_driver())
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
                    cardStatus.setCardBackgroundColor(Color.parseColor("#FFFFCDD2"));
                    textStatusKetersediaan.setTextColor(Color.parseColor("#FFB71C1C"));
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

    public void showDriverRating(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                DriverApi.GET_RATA_RATING_DRIVER + driver.getId_driver(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RerataRatingResponse rerataRatingResponse = gson.fromJson(response, RerataRatingResponse.class);
                RerataRating rata_rating = rerataRatingResponse.getRata_rating();
                rerataRating.setRating(rata_rating.getRata_rating());
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

    private void updateStatus(String statusInputan){

        Driver updateDriver = new Driver(
                statusInputan
        );

        StringRequest stringRequest = new StringRequest(PUT,
                DriverApi.UPDATE_STATUS_DRIVER + driver.getId_driver(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);
                Toast.makeText(getActivity(), driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                changeFragment(new DriverProfileFragment());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                headers.put("Authorization", "Bearer "+ driverPreferences.getDriverLogin().getAccess_token());
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(updateDriver);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    // Method untuk Berganti Fragment
    public void changeFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.layout_fragment, fragment)
                .commit();
    }
}