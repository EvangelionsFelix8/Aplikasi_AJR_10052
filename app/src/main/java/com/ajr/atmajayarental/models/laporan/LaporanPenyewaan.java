package com.ajr.atmajayarental.models.laporan;

public class LaporanPenyewaan {
    private String tipe_mobil, nama_mobil;
    private int jumlah_peminjaman;
    private double pendapatan;

    public LaporanPenyewaan(String tipe_mobil, String nama_mobil, int jumlah_peminjaman, double pendapatan) {
        this.tipe_mobil = tipe_mobil;
        this.nama_mobil = nama_mobil;
        this.jumlah_peminjaman = jumlah_peminjaman;
        this.pendapatan = pendapatan;
    }

    public String getTipe_mobil() {
        return tipe_mobil;
    }

    public void setTipe_mobil(String tipe_mobil) {
        this.tipe_mobil = tipe_mobil;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public int getJumlah_peminjaman() {
        return jumlah_peminjaman;
    }

    public void setJumlah_peminjaman(int jumlah_peminjaman) {
        this.jumlah_peminjaman = jumlah_peminjaman;
    }

    public double getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(double pendapatan) {
        this.pendapatan = pendapatan;
    }
}
