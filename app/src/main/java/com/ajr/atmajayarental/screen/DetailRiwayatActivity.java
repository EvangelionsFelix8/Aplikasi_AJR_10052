package com.ajr.atmajayarental.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.databinding.ActivityDetailRiwayatBinding;
import com.ajr.atmajayarental.models.RiwayatTrans;
import com.google.gson.Gson;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DetailRiwayatActivity extends AppCompatActivity {

    private RiwayatTrans riwayatTrans;
    TextView textIdTransaksi, textTanggalTransaksi, textTanggalMulai, textTanggalSelesai, textStatusTrans, textDurasi, textTanggalKembali;
    TextView textCs, textJenisPromo, textBesarPromo, textMetodeBayar, textTotalMobil, textTotalDriver, textTotalHarga, textTotalDenda, textPotonganPromo;
    TextView textNamaMobil, textNamaDriver;
    private ImageButton btnBack;

    private ActivityDetailRiwayatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_riwayat);

        // Ambil Data dari Riwayat Adapter
        String strRiwayatTrans = getIntent().getStringExtra("detailRiwayat");
        Gson gson = new Gson();
        riwayatTrans = gson.fromJson(strRiwayatTrans, RiwayatTrans.class);

        binding.setRiwayat(riwayatTrans);

        if(riwayatTrans.getId_driver() != null){
            binding.hiddenDataDriver.setVisibility(View.VISIBLE);
        }
        else{
            binding.hiddenDataDriver.setVisibility(View.GONE);
        }

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}