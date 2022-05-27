package com.ajr.atmajayarental.models.laporan;

public class Laporan5DriverTeratas {
    String id_driver, nama_driver;
    int jumlah_Transaksi;

    public Laporan5DriverTeratas(String id_driver, String nama_driver, int jumlah_Transaksi) {
        this.id_driver = id_driver;
        this.nama_driver = nama_driver;
        this.jumlah_Transaksi = jumlah_Transaksi;
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
}
