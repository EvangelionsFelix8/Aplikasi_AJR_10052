package com.ajr.atmajayarental.models.laporan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaporanPerformaDriverResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<LaporanPerformaDriver> laporanPerformaDriverList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LaporanPerformaDriver> getLaporanPerformaDriverList() {
        return laporanPerformaDriverList;
    }

    public void setLaporanPerformaDriverList(List<LaporanPerformaDriver> laporanPerformaDriverList) {
        this.laporanPerformaDriverList = laporanPerformaDriverList;
    }
}
