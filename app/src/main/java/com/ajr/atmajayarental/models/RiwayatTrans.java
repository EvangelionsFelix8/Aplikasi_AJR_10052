package com.ajr.atmajayarental.models;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.ajr.atmajayarental.api.DriverApi;
import com.ajr.atmajayarental.api.MobilApi;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RiwayatTrans {
    private String id_transaksi, id_driver, id_customer;
    private Long id_mobil, id_pegawai, id_promo;
    private String tanggal_transaksi, tanggal_pengembalian, tanggal_mulai, tanggal_selesai, status_transaksi,
            metode_pembayaran, bukti_bayar;
    private double total_harga, total_sewa_mobil, total_sewa_driver, total_denda, potongan_promo;
    private int rating_driver, rating_ajr, besar_diskon_promo;
    private String nama_pegawai, nama_mobil, nama_driver;
    private String jenis_promo, plat_nomor;
    private Duration durasi;
    private double harga_sewa_mobil, tarif_sewa_driver;
    private String url_foto_mobil, url_foto_driver;

    @BindingAdapter("android:loadImageMobil")
    public static void loadImage(ImageView imageView, String imgURL){
        Glide.with(imageView)
                .load(imgURL)
                .into(imageView);
    }

    @BindingAdapter("android:loadImageDriver")
    public static void loadImageDriver(ImageView imageView, String imgURL){
        Glide.with(imageView)
                .load(imgURL)
                .into(imageView);
    }

    public String getUrl_foto_mobil() {
        String tempFoto = MobilApi.BASE_URL_FOTO + "storage/"+ url_foto_mobil;
        Log.i("tempfoto", tempFoto);
        return tempFoto;
    }

    public void setUrl_foto_mobil(String url_foto_mobil) {
        this.url_foto_mobil = url_foto_mobil;
    }

    public String getUrl_foto_driver() {
        String tempFoto = DriverApi.BASE_URL_FOTO + "storage/"+ url_foto_driver;
        return tempFoto;
    }

    public void setUrl_foto_driver(String url_foto_driver) {
        this.url_foto_driver = url_foto_driver;
    }

    public String formatKurs(double inputan){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(inputan);
    }

    public String getHarga_sewa_mobil() {
        return formatKurs(harga_sewa_mobil);
    }

    public void setHarga_sewa_mobil(double harga_sewa_mobil) {
        this.harga_sewa_mobil = harga_sewa_mobil;
    }

    public String getPlat_nomor() {
        return plat_nomor;
    }

    public void setPlat_nomor(String plat_nomor) {
        this.plat_nomor = plat_nomor;
    }

    public int getDurasi() {
        LocalDateTime mulai = LocalDateTime.parse(getTanggal_mulai(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime selesai = LocalDateTime.parse(getTanggal_selesai(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Duration durasi = Duration.between(mulai, selesai);
        return (int) durasi.toDays();
    }

    public void setDurasi(Duration durasi) {
        this.durasi = durasi;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getJenis_promo() {
        return jenis_promo;
    }

    public void setJenis_promo(String jenis_promo) {
        this.jenis_promo = jenis_promo;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public RiwayatTrans(String id_transaksi, String id_driver, String id_customer, Long id_mobil, Long id_pegawai, Long id_promo,
                        String tanggal_transaksi, String tanggal_pengembalian, String tanggal_mulai, String tanggal_selesai,
                        String status_transaksi, String metode_pembayaran, String bukti_bayar, double total_harga,
                        double total_sewa_mobil, double total_sewa_driver, double total_denda, double potongan_promo,
                        int rating_driver, int rating_ajr) {
        this.id_transaksi = id_transaksi;
        this.id_driver = id_driver;
        this.id_customer = id_customer;
        this.id_mobil = id_mobil;
        this.id_pegawai = id_pegawai;
        this.id_promo = id_promo;
        this.tanggal_transaksi = tanggal_transaksi;
        this.tanggal_pengembalian = tanggal_pengembalian;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_selesai = tanggal_selesai;
        this.status_transaksi = status_transaksi;
        this.metode_pembayaran = metode_pembayaran;
        this.bukti_bayar = bukti_bayar;
        this.total_harga = total_harga;
        this.total_sewa_mobil = total_sewa_mobil;
        this.total_sewa_driver = total_sewa_driver;
        this.total_denda = total_denda;
        this.potongan_promo = potongan_promo;
        this.rating_driver = rating_driver;
        this.rating_ajr = rating_ajr;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public Long getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(Long id_mobil) {
        this.id_mobil = id_mobil;
    }

    public Long getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(Long id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public Long getId_promo() {
        return id_promo;
    }

    public void setId_promo(Long id_promo) {
        this.id_promo = id_promo;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getTanggal_pengembalian() {
        return tanggal_pengembalian;
    }

    public void setTanggal_pengembalian(String tanggal_pengembalian) {
        this.tanggal_pengembalian = tanggal_pengembalian;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setMetode_pembayaran(String metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }

    public String getBukti_bayar() {
        return bukti_bayar;
    }

    public void setBukti_bayar(String bukti_bayar) {
        this.bukti_bayar = bukti_bayar;
    }

    public String getTotal_hargaBinding() {
        return formatKurs(total_harga);
    }

    public String getTotalKeseluruhan(){
        double tempTotal = total_harga - potongan_promo;
        return formatKurs(tempTotal);
    }

    public double getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(double total_harga) {
        this.total_harga = total_harga;
    }

    public String getTotal_sewa_mobil() {
        return formatKurs(total_sewa_mobil);
    }

    public void setTotal_sewa_mobil(double total_sewa_mobil) {
        this.total_sewa_mobil = total_sewa_mobil;
    }

    public String getTotal_sewa_driver() {
        return formatKurs(total_sewa_driver);
    }

    public void setTotal_sewa_driver(double total_sewa_driver) {
        this.total_sewa_driver = total_sewa_driver;
    }

    public String getTotal_denda() {
        return formatKurs(total_denda);
    }

    public void setTotal_denda(double total_denda) {
        this.total_denda = total_denda;
    }

    public String getPotongan_promo() {
        return formatKurs(potongan_promo);
    }

    public void setPotongan_promo(double potongan_promo) {
        this.potongan_promo = potongan_promo;
    }

    public int getRating_driver() {
        return rating_driver;
    }

    public void setRating_driver(int rating_driver) {
        this.rating_driver = rating_driver;
    }

    public int getRating_ajr() {
        return rating_ajr;
    }

    public void setRating_ajr(int rating_ajr) {
        this.rating_ajr = rating_ajr;
    }

    public String getTarif_sewa_driver() {
        return formatKurs(tarif_sewa_driver);
    }

    public void setTarif_sewa_driver(double tarif_sewa_driver) {
        this.tarif_sewa_driver = tarif_sewa_driver;
    }

    public int getBesar_diskon_promo() {
        return besar_diskon_promo;
    }

    public void setBesar_diskon_promo(int besar_diskon_promo) {
        this.besar_diskon_promo = besar_diskon_promo;
    }
}
