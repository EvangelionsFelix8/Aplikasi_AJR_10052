package com.ajr.atmajayarental.models;

import java.util.ArrayList;

public class promo_dummy {
    public ArrayList<Promo> listPromo;

    public promo_dummy(){
        listPromo = new ArrayList();
        listPromo.add(PROMO_1);
        listPromo.add(PROMO_2);
    }

    public static final Promo PROMO_1 = new Promo(
            "MHS",
            "Pelajar & Mahasiswa",
            "Promo bagi customer yang berusia mulai dari 17-22 tahun dan memiliki kartu identitas pelajar/mahasiswa, mendapat diskon sebesar 20%",
            20,
            "Aktif"
    );

    public static final Promo PROMO_2 = new Promo(
            "BDAY",
            "Ulang Tahun",
            "Promo bagi customer yang sedang berulang tahun, mendapat diskon sebesar 15%",
            15,
            "Aktif"
    );
}
