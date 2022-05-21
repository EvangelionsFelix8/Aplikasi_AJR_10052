package com.ajr.atmajayarental.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RiwayatTransResponse {
    private String message;

    @SerializedName("data")
    private List<RiwayatTrans> riwayatTransList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RiwayatTrans> getPromoList() {
        return riwayatTransList;
    }

    public void setPromoList(List<RiwayatTrans> riwayatTransList) {
        this.riwayatTransList = riwayatTransList;
    }
}
