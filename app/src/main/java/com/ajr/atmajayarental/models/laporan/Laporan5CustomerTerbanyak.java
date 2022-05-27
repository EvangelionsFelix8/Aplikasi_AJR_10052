package com.ajr.atmajayarental.models.laporan;

public class Laporan5CustomerTerbanyak {
    String nama_customer;
    int jumlah_Transaksi;

    public Laporan5CustomerTerbanyak(String nama_customer, int jumlah_Transaksi) {
        this.nama_customer = nama_customer;
        this.jumlah_Transaksi = jumlah_Transaksi;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public int getJumlah_Transaksi() {
        return jumlah_Transaksi;
    }

    public void setJumlah_Transaksi(int jumlah_Transaksi) {
        this.jumlah_Transaksi = jumlah_Transaksi;
    }
}
