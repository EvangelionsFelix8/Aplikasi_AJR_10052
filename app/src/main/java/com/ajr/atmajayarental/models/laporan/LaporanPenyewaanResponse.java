package com.ajr.atmajayarental.models.laporan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaporanPenyewaanResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<LaporanPenyewaan> laporanPenyewaanList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LaporanPenyewaan> getLaporanPenyewaanList() {
        return laporanPenyewaanList;
    }

    public void setLaporanPenyewaanFromJSONList(List<LaporanPenyewaan> laporanPenyewaanList) {
        this.laporanPenyewaanList = laporanPenyewaanList;
    }
}
