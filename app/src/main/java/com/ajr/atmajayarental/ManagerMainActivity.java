package com.ajr.atmajayarental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.api.CustomerApi;
import com.ajr.atmajayarental.api.PegawaiApi;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.CustomerResponse;
import com.ajr.atmajayarental.models.Pegawai;
import com.ajr.atmajayarental.models.PegawaiResponse;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.ajr.atmajayarental.preferences.PegawaiPreferences;
import com.ajr.atmajayarental.screen.LoginActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ManagerMainActivity extends AppCompatActivity{

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView textTanggalMulai, textTanggalSelesai;
    TextInputLayout textInputLayoutMulai, textInputLayoutSelesai;
    private TextView textNama;
    private ImageButton btnLogout;
    private PegawaiPreferences pegawaiPreferences;
    private Pegawai pegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        pegawaiPreferences = new PegawaiPreferences(getApplicationContext());
        pegawai = pegawaiPreferences.getPegawaiLogin();

        textTanggalMulai = findViewById(R.id.autoTanggalMulai);
        textTanggalSelesai = findViewById(R.id.autoTanggalSelesai);
        textNama = findViewById(R.id.textNamaManager);
        btnLogout = findViewById(R.id.btnLogout);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        showPegawaiData();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegawaiPreferences.logout();
                Toast.makeText(ManagerMainActivity.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
                checkLogin();
            }
        });

        textTanggalMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ManagerMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year+"/"+month+"/"+day;
                        textTanggalMulai.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        textTanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ManagerMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        textTanggalSelesai.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void checkLogin() {
//        Fungsi ini akan mengecek jika user login,
//        akan memunculkan toast jika tidak redirect ke login activity
        if (!pegawaiPreferences.checkLogin()) {
            startActivity(new Intent(ManagerMainActivity.this, LoginActivity.class));
            finish();
        }
        else {
            Toast.makeText(ManagerMainActivity.this.getApplicationContext(), "Login !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPegawaiData(){
        Log.i("id_pegawai", pegawai.getId_pegawai() + "");
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                PegawaiApi.GET_PEGAWAI_DATA + pegawai.getId_pegawai(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                PegawaiResponse pegawaiResponse = gson.fromJson(response, PegawaiResponse.class);
                Pegawai foundedPegawaiData = pegawaiResponse.getPegawai();
                textNama.setText(foundedPegawaiData.getNama_pegawai());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getApplicationContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+ pegawaiPreferences.getPegawaiLogin().getAccess_token());
                return headers;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}