package com.ajr.atmajayarental.api;

public class PegawaiApi {
    public static String BASE_URL = "https://atmajogjarental.felixyehdeya.xyz/api/";

    public static final String GET_PEGAWAI_DATA = BASE_URL + "pegawai/";

    // LAPORAN
    public static final String GET_LAPORAN_SEWA_MOBIL = BASE_URL + "laporanpenyewaan/";
    public static final String GET_LAPORAN_DETAIL_PENDAPATAN = BASE_URL + "laporandetailpendapatan/";
    public static final String GET_5_DRIVER_TERATAS = BASE_URL + "laporan5driverteratas/";
    public static final String GET_LAPORAN_PERFORMA_DRIVER = BASE_URL + "Laporanperformadriver/";
    public static final String GET_5_CUSTOMER_TERBANYAK = BASE_URL + "Laporan5terajin/";
}
