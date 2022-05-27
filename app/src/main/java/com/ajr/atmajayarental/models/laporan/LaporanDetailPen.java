package com.ajr.atmajayarental.models.laporan;

public class LaporanDetailPen {
    String nama_customer, nama_mobil, Jenis_Transaksi;
    int jumlah_Transaksi;
    double pendapatan;

    public LaporanDetailPen(String nama_customer, String nama_mobil, String jenis_Transaksi, int jumlah_Transaksi, double pendapatan) {
        this.nama_customer = nama_customer;
        this.nama_mobil = nama_mobil;
        Jenis_Transaksi = jenis_Transaksi;
        this.jumlah_Transaksi = jumlah_Transaksi;
        this.pendapatan = pendapatan;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getJenis_Transaksi() {
        return Jenis_Transaksi;
    }

    public void setJenis_Transaksi(String jenis_Transaksi) {
        Jenis_Transaksi = jenis_Transaksi;
    }

    public int getJumlah_Transaksi() {
        return jumlah_Transaksi;
    }

    public void setJumlah_Transaksi(int jumlah_Transaksi) {
        this.jumlah_Transaksi = jumlah_Transaksi;
    }

    public double getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(double pendapatan) {
        this.pendapatan = pendapatan;
    }
}
