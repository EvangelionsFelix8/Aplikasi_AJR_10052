package com.ajr.atmajayarental.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.api.MobilApi;
import com.ajr.atmajayarental.databinding.ActivityDetailRiwayatDriverBinding;
import com.ajr.atmajayarental.models.RiwayatTrans;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class DetailRiwayatDriverActivity extends AppCompatActivity {

    private RiwayatTrans riwayatTrans;
    private ImageButton btnBack;

    private TextView textTanggalTransaksi, textTanggalMulai, textTanggalSelesai, textDurasi,
                    textPegawai,textNamaMobil, textPlatMobil, textHargaMobil, textId;
    private RatingBar ratingDriver;
    private ImageView fotoMobil;
    private RiwayatTrans riwayatTransbaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_driver);

        textId = findViewById(R.id.textID_transaksi);
        textTanggalTransaksi = findViewById(R.id.textTanggalTransaksi);
        textTanggalMulai = findViewById(R.id.textTanggalMulai);
        textTanggalSelesai = findViewById(R.id.textTanggalSelesai);
        textDurasi = findViewById(R.id.textDurasi);
        textPegawai = findViewById(R.id.textNamaPegawai);
        textNamaMobil = findViewById(R.id.textNamaMobil);
        textPlatMobil = findViewById(R.id.textPlatNomor);
        textHargaMobil = findViewById(R.id.textHargaMobil);
        ratingDriver = findViewById(R.id.rataRating);
        fotoMobil = findViewById(R.id.fotoMobil);

        String strRiwayatTrans = getIntent().getStringExtra("detailRiwayatDriver");
        Gson gson = new Gson();
        riwayatTrans = gson.fromJson(strRiwayatTrans, RiwayatTrans.class);

        textId.setText(riwayatTrans.getId_transaksi());
        textTanggalTransaksi.setText(riwayatTrans.getTanggal_transaksi());
        textTanggalMulai.setText(riwayatTrans.getTanggal_mulai());
        textTanggalSelesai.setText(riwayatTrans.getTanggal_selesai());
        textDurasi.setText(riwayatTrans.getDurasi()+ " Hari");
        textPegawai.setText(riwayatTrans.getNama_pegawai());
        textNamaMobil.setText(riwayatTrans.getNama_mobil());
        textPlatMobil.setText(riwayatTrans.getPlat_nomor());
        textHargaMobil.setText(riwayatTrans.getHarga_sewa_mobil() + "/hari");
        ratingDriver.setRating(riwayatTrans.getRating_driver());
        Glide.with(getApplicationContext())
                .load(riwayatTrans.getUrl_foto_mobil())
                .placeholder(R.drawable.no_image)
                .into(fotoMobil);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}