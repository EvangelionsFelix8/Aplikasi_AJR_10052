package com.ajr.atmajayarental.models.laporan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Laporan5CustomerTerbanyakResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Laporan5CustomerTerbanyak> laporan5CustomerTerbanyakList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Laporan5CustomerTerbanyak> getLaporan5CustomerTerbanyakList() {
        return laporan5CustomerTerbanyakList;
    }

    public void setLaporan5CustomerTerbanyakList(List<Laporan5CustomerTerbanyak> laporan5CustomerTerbanyakList) {
        this.laporan5CustomerTerbanyakList = laporan5CustomerTerbanyakList;
    }
}
