package com.ajr.atmajayarental.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.api.DriverApi;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.RiwayatTrans;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class UpdateProfileActivity extends AppCompatActivity {

    private Driver driver;
    private ImageView fotoDriver;
    private EditText textEditNama, textEditEmail, textEditNomor, textEditTanggal, textEditKelamin, textEditAlamat, textEditInggris;
    private Button btnSaveEdit;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        fotoDriver = findViewById(R.id.fotoDriverE);
        textEditNama = findViewById(R.id.textNamaDriverE);
        textEditEmail = findViewById(R.id.textEmailDriverE);
        textEditNomor = findViewById(R.id.textTeleponDriverE);
        textEditTanggal = findViewById(R.id.textTanggalDriverE);
        textEditKelamin = findViewById(R.id.textKelaminDriverE);
        textEditAlamat = findViewById(R.id.textAlamatDriverE);
        textEditInggris = findViewById(R.id.textBahasaInggrisE);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);

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
            textEditInggris.setText("Bisa");
        }
        else{
            textEditInggris.setText("Tidak Bisa");
        }

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Berhasil Edit Data", Toast.LENGTH_SHORT).show();
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
}