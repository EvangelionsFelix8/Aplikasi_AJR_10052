package com.ajr.atmajayarental.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RerataRatingResponse {
    private String message;

    private RerataRating rata_rating;

    public RerataRatingResponse(String message, RerataRating rata_rating) {
        this.message = message;
        this.rata_rating = rata_rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RerataRating getRata_rating() {
        return rata_rating;
    }

    public void setRata_rating(RerataRating rata_rating) {
        this.rata_rating = rata_rating;
    }
}
