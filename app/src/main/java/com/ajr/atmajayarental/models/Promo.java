package com.ajr.atmajayarental.models;

public class Promo {
    private String kode_promo;
    private String jenis_promo;
    private String keterangan;
    private int besar_diskon_promo;
    private String status_promo;

    public Promo(String kode_promo, String jenis_promo, String keterangan, int besar_diskon_promo, String status_promo) {
        this.kode_promo = kode_promo;
        this.jenis_promo = jenis_promo;
        this.keterangan = keterangan;
        this.besar_diskon_promo = besar_diskon_promo;
        this.status_promo = status_promo;
    }

    public String getKode_promo() {
        return kode_promo;
    }

    public void setKode_promo(String kode_promo) {
        this.kode_promo = kode_promo;
    }

    public String getJenis_promo() {
        return jenis_promo;
    }

    public void setJenis_promo(String jenis_promo) {
        this.jenis_promo = jenis_promo;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getBesar_diskon_promo() {
        return besar_diskon_promo;
    }

    public void setBesar_diskon_promo(int besar_diskon_promo) {
        this.besar_diskon_promo = besar_diskon_promo;
    }

    public String getStatus_promo() {
        return status_promo;
    }

    public void setStatus_promo(String status_promo) {
        this.status_promo = status_promo;
    }
}
