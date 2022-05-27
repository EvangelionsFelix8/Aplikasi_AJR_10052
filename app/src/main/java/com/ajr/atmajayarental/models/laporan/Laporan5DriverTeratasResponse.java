package com.ajr.atmajayarental.models.laporan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Laporan5DriverTeratasResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Laporan5DriverTeratas> laporan5DriverTeratasList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Laporan5DriverTeratas> getLaporan5DriverTeratasList() {
        return laporan5DriverTeratasList;
    }

    public void setLaporan5DriverTeratasList(List<Laporan5DriverTeratas> laporan5DriverTeratasList) {
        this.laporan5DriverTeratasList = laporan5DriverTeratasList;
    }
}
