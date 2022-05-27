package com.ajr.atmajayarental.models.laporan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaporanDetailPenResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<LaporanDetailPen> laporanDetailPenList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LaporanDetailPen> getLaporanDetailPenList() {
        return laporanDetailPenList;
    }

    public void setLaporanDetailPenList(List<LaporanDetailPen> laporanDetailPenList) {
        this.laporanDetailPenList = laporanDetailPenList;
    }
}
