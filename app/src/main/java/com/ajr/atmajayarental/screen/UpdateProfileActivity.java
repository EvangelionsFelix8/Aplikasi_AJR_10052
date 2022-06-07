package com.ajr.atmajayarental.screen;

import static com.android.volley.Request.Method.PUT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.api.DriverApi;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.DriverResponse;
import com.ajr.atmajayarental.models.RiwayatTrans;
import com.ajr.atmajayarental.preferences.DriverPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//public class UpdateProfileActivity extends AppCompatActivity {

    private Driver driver, driverReturn;
    private ImageView fotoDriver;
    private EditText textEditNama, textEditEmail, textEditNomor, textEditKelamin, textEditAlamat;
    private AutoCompleteTextView textEditTanggal;
    private Spinner textEditInggris;
//    private AutoCompleteTextView textEditInggris;
    private Button btnSaveEdit;
    private ImageButton btnBack;
    private String tempInggris;

    private DriverPreferences driverPreferences;

    private static final String[] EnglishArray = new String[]{
            "Bisa","Tidak Bisa"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        driverPreferences = new DriverPreferences(getApplicationContext());
        driver = driverPreferences.getDriverLogin();

        fotoDriver = findViewById(R.id.fotoDriverE);
        textEditNama = findViewById(R.id.textNamaDriverE);
        textEditEmail = findViewById(R.id.textEmailDriverE);
        textEditNomor = findViewById(R.id.textTeleponDriverE);
        textEditTanggal = findViewById(R.id.textTanggalDriverE);
        textEditKelamin = findViewById(R.id.textKelaminDriverE);
        textEditAlamat = findViewById(R.id.textAlamatDriverE);
        textEditInggris = findViewById(R.id.textBahasaInggrisE);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);

        ArrayAdapter<String> adapterInggris = new ArrayAdapter<>(this, R.layout.list_item, EnglishArray);
        textEditInggris.setAdapter(adapterInggris);
        textEditInggris.setOnItemSelectedListener(this);

        String strProfile = getIntent().getStringExtra("editProfile");
        Gson gson = new Gson();
        driver = gson.fromJson(strProfile, Driver.class);

        Glide.with(getApplicationContext())
                .load(DriverApi.BASE_URL_FOTO + driver.getUrl_foto_driver())
                .placeholder(R.drawable.no_image)
                .into(fotoDriver);
        textEditNama.setText(driver.getNama_driver());
        textEditEmail.setText(driver.getEmail_driver());
        textEditNomor.setText(driver.getNo_telp_driver());
        textEditTanggal.setText(driver.getTanggal_lahir_driver());
        textEditKelamin.setText(driver.getJenis_kelamin());
        textEditAlamat.setText(driver.getAlamat_driver());
        if(driver.isEnglish() == 1){
            int spinnerPosition = adapterInggris.getPosition("Bisa");
            textEditInggris.setSelection(spinnerPosition);
        }
        else{
            int spinnerPosition = adapterInggris.getPosition("Tidak Bisa");
            textEditInggris.setSelection(spinnerPosition);
        }

//        if(driver.isEnglish() == 1){
//            textEditInggris.setText("Bisa");
//        }
//        else{
//            textEditInggris.setText("Tidak Bisa");
//        }

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dikasih array karena kalo getText.toString nya gak dipakein error, (BISA JALAN)
                final String[] nama = new String[1];
                final String[] email = new String[1];
                final String[] nomorTelepon = new String[1];
                final String[] alamat = new String[1];
                final int[] inggris = new int[1];

//                Toast.makeText(getApplicationContext(), "HELo", Toast.LENGTH_SHORT).show();

                final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();

                new AlertDialog.Builder(UpdateProfileActivity.this)
                        .setTitle("WARNING")
                        .setMessage("Apakah Anda Yakin ingin mengedit Profile Anda?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                nama[0] = textEditNama.getText().toString().trim();
                                email[0] = textEditEmail.getText().toString().trim();
                                nomorTelepon[0] = textEditNomor.getText().toString().trim();
                                alamat[0] = textEditAlamat.getText().toString().trim();

                                if(tempInggris.equalsIgnoreCase("Bisa")){
                                    inggris[0] = 1;
                                }
                                else{
                                    inggris[0] = 2;
                                }

                                updateDriver(nama[0], email[0], nomorTelepon[0], alamat[0], inggris[0]);
                                alertDialog.dismiss();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed1() {
        Intent i = new Intent();
        i.putExtra("getEdit", driverReturn);
        setResult(RESULT_OK, i);
        finish();
    }

    private void updateDriver(String nama, String email, String noTelp, String alamat, int inggris){

        Driver updatedDriver = new Driver(
                nama,
                email,
                noTelp,
                alamat,
                inggris
        );

        StringRequest stringRequest = new StringRequest(PUT,
                DriverApi.UPDATE_DATA_DRIVER + driver.getId_driver(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);
                Toast.makeText(getApplicationContext(), driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                driverReturn = updatedDriver;
                driverPreferences.setEditDataLogin(
                        nama,
                        email,
                        noTelp,
                        alamat,
                        inggris
                );
                onBackPressed1();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    if(nama.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    if(email.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    if(noTelp.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Nomor Telepon Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    if(alamat.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Alamat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(getApplicationContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                String requestBody = gson.toJson(updatedDriver);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tempInggris = textEditInggris.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}