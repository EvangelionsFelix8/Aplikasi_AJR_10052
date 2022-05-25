package com.ajr.atmajayarental.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.Pegawai;

public class DriverPreferences {

        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        Context context;

        public static final String IS_LOGIN = "isLogin";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String KEY_ID = "id_driver";
        public static final String KEY_NAME = "nama_driver";
        public static final String KEY_ALAMAT = "alamat_driver";
        public static final String KEY_EMAIL = "email_driver";
        public static final String KEY_TERSEDIA = "status_ketersediaan_driver";
        public static final String KEY_BERKAS = "status_berkas";
        public static final String KEY_ENGLISH = "isEnglish";
        public static final String KEY_LAHIR = "tanggal_lahir_driver";
        public static final String KEY_KELAMIN = "jenis_kelamin";
        public static final String KEY_TELEPON = "no_telp_driver";
        public static final String KEY_FOTO = "url_foto_pegawai";
        public static final String KEY_PASSWORD = "password";
        public static final String KEY_TARIF = "tarif_sewa_driver";
        public static final String KEY_NAPZA = "berkas_bebas_napza";
        public static final String KEY_SIM = "berkas_sim";
        public static final String KEY_JIWA = "berkas_sehat_jiwa";
        public static final String KEY_JASMANI = "berkas_sehat_jasmani";
        public static final String KEY_SKCK = "berkas_skck";
        public static final String KEY_RERATA = "rerata_rating_driver";
        public static final String KEY_AKTIF = "isAktif";

        public DriverPreferences(Context context){
                this.context = context;

                sharedPreferences = context.getSharedPreferences("driverPreferences", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
        }

        public void setLogin(String access_token, String id_driver, String nama_driver, String alamat_driver, String email_driver, String status_ketersediaan_driver,
                             String status_berkas, int isEnglish, String tanggal_lahir_driver, String jenis_kelamin,
                             String no_telp_driver, String url_foto_driver, String password, float tarif_sewa_driver,
                             String berkas_bebas_napza, String berkas_sim, String berkas_sehat_jiwa, String berkas_sehat_jasmani,
                             String berkas_skck, float rerata_rating_driver, int isAktif){
                editor.putBoolean(IS_LOGIN, true);
                editor.putString(ACCESS_TOKEN, access_token);
                editor.putString(KEY_ID, id_driver);
                editor.putString(KEY_NAME, nama_driver);
                editor.putString(KEY_ALAMAT, alamat_driver);
                editor.putString(KEY_EMAIL, email_driver);
                editor.putString(KEY_TERSEDIA, status_ketersediaan_driver);
                editor.putString(KEY_BERKAS, status_berkas);
                editor.putInt(KEY_ENGLISH, isEnglish);
                editor.putString(KEY_LAHIR, tanggal_lahir_driver);
                editor.putString(KEY_KELAMIN, jenis_kelamin);
                editor.putString(KEY_TELEPON, no_telp_driver);
                editor.putString(KEY_FOTO, url_foto_driver);
                editor.putString(KEY_PASSWORD, password);
                editor.putFloat(KEY_TARIF, tarif_sewa_driver);
                editor.putString(KEY_NAPZA, berkas_bebas_napza);
                editor.putString(KEY_SIM, berkas_sim);
                editor.putString(KEY_JIWA, berkas_sehat_jiwa);
                editor.putString(KEY_JASMANI, berkas_sehat_jasmani);
                editor.putString(KEY_SKCK, berkas_skck);
                editor.putFloat(KEY_RERATA, rerata_rating_driver);
                editor.putInt(KEY_AKTIF, isAktif);

                editor.commit();
        }

        public void setEditDataLogin(String nama_driver, String email_driver,String no_telp_driver, String alamat_driver, int isEnglish){
                editor.putString(KEY_NAME, nama_driver);
                editor.putString(KEY_ALAMAT, alamat_driver);
                editor.putString(KEY_EMAIL, email_driver);
                editor.putInt(KEY_ENGLISH, isEnglish);
                editor.putString(KEY_TELEPON, no_telp_driver);

                editor.commit();
        }

        public Driver getDriverLogin(){
                String access_token;
                String id_driver, nama_driver, alamat_driver, email_driver, status_ketersediaan_driver, status_berkas;
                int isEnglish;
                String tanggal_lahir_driver, jenis_kelamin, no_telp_driver, url_foto_driver, password;
                float tarif_sewa_driver;
                String berkas_bebas_napza, berkas_sim, berkas_sehat_jiwa, berkas_sehat_jasmani, berkas_skck;
                float rerata_rating_driver;
                int isAktif;

                access_token = sharedPreferences.getString(ACCESS_TOKEN, null);
                id_driver = sharedPreferences.getString(KEY_ID,null);
                nama_driver = sharedPreferences.getString(KEY_NAME, null);
                alamat_driver = sharedPreferences.getString(KEY_ALAMAT, null);
                email_driver = sharedPreferences.getString(KEY_EMAIL, null);
                status_ketersediaan_driver = sharedPreferences.getString(KEY_TERSEDIA, null);
                status_berkas = sharedPreferences.getString(KEY_BERKAS, null);
                isEnglish = sharedPreferences.getInt(KEY_ENGLISH, 2);
                tanggal_lahir_driver = sharedPreferences.getString(KEY_LAHIR,null);
                jenis_kelamin = sharedPreferences.getString(KEY_KELAMIN,null);
                no_telp_driver = sharedPreferences.getString(KEY_TELEPON,null);
                url_foto_driver = sharedPreferences.getString(KEY_FOTO,null);
                password = sharedPreferences.getString(KEY_PASSWORD, null);
                tarif_sewa_driver = sharedPreferences.getFloat(KEY_TARIF, 0);
                berkas_bebas_napza = sharedPreferences.getString(KEY_NAPZA, null);
                berkas_sim = sharedPreferences.getString(KEY_SIM, null);
                berkas_sehat_jiwa = sharedPreferences.getString(KEY_JIWA, null);
                berkas_sehat_jasmani = sharedPreferences.getString(KEY_JASMANI, null);
                berkas_skck = sharedPreferences.getString(KEY_SKCK, null);
                rerata_rating_driver = sharedPreferences.getFloat(KEY_RERATA, 0);
                isAktif = sharedPreferences.getInt(KEY_AKTIF,2);

                return new Driver(access_token, id_driver, nama_driver, alamat_driver, email_driver, status_ketersediaan_driver,
                         status_berkas, isEnglish, tanggal_lahir_driver, jenis_kelamin,
                        no_telp_driver, url_foto_driver, password, tarif_sewa_driver,
                        berkas_bebas_napza, berkas_sim, berkas_sehat_jiwa, berkas_sehat_jasmani,
                        berkas_skck, rerata_rating_driver, isAktif);
        }

        public boolean checkLogin(){
                return sharedPreferences.getBoolean(IS_LOGIN, false);
        }

        public void logout(){
                editor.clear();
                editor.commit();
        }
}
