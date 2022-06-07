package com.ajr.atmajayarental.api;

public class CustomerApi {
//    public static String BASE_URL = "http://192.168.34.65:8000/api/";

//    public static String BASE_URL = "http://192.168.128.65:8000/api/";
    public static String BASE_URL = "http://192.168.32.65:8000/api/";

    public static final String GET_CUSTOMER_DATA = BASE_URL + "customer/";
    public static final String UPDATE_CUSTOMER_DATA = BASE_URL + "customer/";

    // SHOW TRANSAKSI
    public static final String GET_RIWAYAT_DATA = BASE_URL + "showbycustomer/";

    // SHOW PROMO
    public static final String GET_ACTIVE_URL = BASE_URL + "showbystatuspromo";

    // CHANGE PASSWORD
    public static final String UPDATE_PASSWORD = BASE_URL + "updatepassword/";
}
