package com.ajr.atmajayarental.models;

import com.google.gson.annotations.SerializedName;

public class PegawaiResponse {
    private String message;

    @SerializedName("data")
    private Pegawai pegawai;

    @SerializedName("token")
    private String token;

    private int kode;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public PegawaiResponse(String message, Pegawai pegawai, String token, int kode) {
        this.message = message;
        this.pegawai = pegawai;
        this.token = token;
        this.kode = kode;
    }

    public Pegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
