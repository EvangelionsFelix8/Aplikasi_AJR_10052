package com.ajr.atmajayarental.models.laporan;

public class LaporanPerformaDriver {
    String id_driver, nama_driver;
    int jumlah_Transaksi;
    double rerata_rating_driver;

    public LaporanPerformaDriver(String id_driver, String nama_driver, int jumlah_Transaksi, double rerata_rating_driver) {
        this.id_driver = id_driver;
        this.nama_driver = nama_driver;
        this.jumlah_Transaksi = jumlah_Transaksi;
        this.rerata_rating_driver = rerata_rating_driver;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public int getJumlah_Transaksi() {
        return jumlah_Transaksi;
    }

    public void setJumlah_Transaksi(int jumlah_Transaksi) {
        this.jumlah_Transaksi = jumlah_Transaksi;
    }

    public double getRerata_rating_driver() {
        return rerata_rating_driver;
    }

    public void setRerata_rating_driver(double rerata_rating_driver) {
        this.rerata_rating_driver = rerata_rating_driver;
    }
}
