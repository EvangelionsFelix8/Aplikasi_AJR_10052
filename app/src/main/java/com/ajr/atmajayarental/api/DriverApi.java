package com.ajr.atmajayarental.api;

public class DriverApi {
    public static String BASE_URL = "https://atmajogjarental.felixyehdeya.xyz/api/";
    public static String BASE_URL_FOTO = "https://atmajogjarental.felixyehdeya.xyz/storage/";

    public static final String GET_DRIVER_DATA = BASE_URL + "driver/";
    public static final String GET_RATA_RATING_DRIVER = BASE_URL + "getreratadriverbyid/";
    public static final String UPDATE_STATUS_DRIVER = BASE_URL + "updateketersediaandriver/";
    public static final String UPDATE_DATA_DRIVER = BASE_URL + "updatedriver/";

    // RIWAYAT TRANSAKSI
    public static final String SHOW_RIWAYAT = BASE_URL + "showbydriver/";
}
