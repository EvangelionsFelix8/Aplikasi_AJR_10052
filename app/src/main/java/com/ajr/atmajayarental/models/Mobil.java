package com.ajr.atmajayarental.models;
public class Mobil {
    private String url_foto_mobil;
    private String nama_mobil;
    private double harga_sewa_mobil;
    private int kapasitas_penumpang;
    private String tipe_mobil;
    private String jenis_bahan_bakar;
    private String jenis_transmisi;
    private String fasilitas;

    public Mobil(String url_foto_mobil, String nama_mobil, double harga_sewa_mobil, int kapasitas_penumpang, String tipe_mobil, String jenis_bahan_bakar, String jenis_transmisi, String fasilitas) {
        this.url_foto_mobil = url_foto_mobil;
        this.nama_mobil = nama_mobil;
        this.harga_sewa_mobil = harga_sewa_mobil;
        this.kapasitas_penumpang = kapasitas_penumpang;
        this.tipe_mobil = tipe_mobil;
        this.jenis_bahan_bakar = jenis_bahan_bakar;
        this.jenis_transmisi = jenis_transmisi;
        this.fasilitas = fasilitas;
    }

    public String getJenis_transmisi() {
        return jenis_transmisi;
    }

    public void setJenis_transmisi(String jenis_transmisi) {
        this.jenis_transmisi = jenis_transmisi;
    }

    public String getUrl_foto_mobil() {
        return url_foto_mobil;
    }

    public void setUrl_foto_mobil(String url_foto_mobil) {
        this.url_foto_mobil = url_foto_mobil;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public double getHarga_sewa_mobil() {
        return harga_sewa_mobil;
    }

    public void setHarga_sewa_mobil(double harga_sewa_mobil) {
        this.harga_sewa_mobil = harga_sewa_mobil;
    }

    public int getKapasitas_penumpang() {
        return kapasitas_penumpang;
    }

    public void setKapasitas_penumpang(int kapasitas_penumpang) {
        this.kapasitas_penumpang = kapasitas_penumpang;
    }

    public String getTipe_mobil() {
        return tipe_mobil;
    }

    public void setTipe_mobil(String tipe_mobil) {
        this.tipe_mobil = tipe_mobil;
    }

    public String getJenis_bahan_bakar() {
        return jenis_bahan_bakar;
    }

    public void setJenis_bahan_bakar(String jenis_bahan_bakar) {
        this.jenis_bahan_bakar = jenis_bahan_bakar;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }
}
