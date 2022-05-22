package com.ajr.atmajayarental.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ajr.atmajayarental.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        String strRiwayatTrans = getIntent().getStringExtra("detailRiwayat");
        Gson gson = new Gson();
        riwayatTrans = gson.fromJson(strRiwayatTrans, RiwayatTrans.class);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        textIdTransaksi = findViewById(R.id.textID_transaksi);
        textTanggalTransaksi = findViewById(R.id.textTanggalTransaksi);
        textTanggalMulai = findViewById(R.id.textTanggalMulai);
        textTanggalSelesai = findViewById(R.id.textTanggalSelesai);
        textDurasi = findViewById(R.id.textDurasi);
        textTanggalKembali = findViewById(R.id.textTanggalKembali);
        textCs = findViewById(R.id.textNamaPegawai);
        textJenisPromo = findViewById(R.id.textJenisPromo);
        textNamaMobil = findViewById(R.id.textNamaMobil);
        textNamaDriver = findViewById(R.id.textNamaDriver);

        textIdTransaksi.setText(riwayatTrans.getId_transaksi());
        textTanggalTransaksi.setText(riwayatTrans.getTanggal_transaksi());
        textTanggalMulai.setText(riwayatTrans.getTanggal_mulai());
        textTanggalSelesai.setText(riwayatTrans.getTanggal_selesai());
        LocalDateTime mulai = LocalDateTime.parse(riwayatTrans.getTanggal_mulai(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime selesai = LocalDateTime.parse(riwayatTrans.getTanggal_selesai(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Duration duration = Duration.between(mulai, selesai);
        textDurasi.setText(String.valueOf(duration.toDays()) + " hari");
        textTanggalKembali.setText(riwayatTrans.getTanggal_pengembalian());
        textCs.setText(riwayatTrans.getNama_pegawai());

        if(riwayatTrans.getId_promo() == null){
            textJenisPromo.setText("-");
        }
        else{
            textJenisPromo.setText(riwayatTrans.getJenis_promo());
        }

        textNamaMobil.setText(riwayatTrans.getNama_mobil());
        textNamaDriver.setText(riwayatTrans.getNama_driver());
    }
}