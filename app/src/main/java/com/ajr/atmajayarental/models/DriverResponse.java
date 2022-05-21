package com.ajr.atmajayarental.models;

import com.google.gson.annotations.SerializedName;

public class DriverResponse {
    private String message;

    @SerializedName("data")
    private Driver driver;

    @SerializedName("token")
    private String token;

    private int kode;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public DriverResponse(String message, Driver driver, String token, int kode) {
        this.message = message;
        this.driver = driver;
        this.token = token;
        this.kode = kode;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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
