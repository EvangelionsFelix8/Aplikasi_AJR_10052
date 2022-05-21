package com.ajr.atmajayarental.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ajr.atmajayarental.models.Customer;

import java.util.Date;

public class CustomerPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public static final String IS_LOGIN = "isLogin";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String KEY_ID = "id_customer";
    public static final String KEY_NAME = "nama_customer";
    public static final String KEY_ALAMAT = "alamat_customer";
    public static final String KEY_LAHIR = "tanggal_lahir_customer";
    public static final String KEY_KELAMIN = "jenis_kelamin";
    public static final String KEY_EMAIL = "email_customer";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TELEPON = "no_telp_customer";
    public static final String KEY_BERKAS = "status_berkas";
    public static final String KEY_PENGENAL = "no_tanda_pengenal";
    public static final String KEY_SIM = "no_sim";

    public CustomerPreferences(Context context){
        this.context = context;

        sharedPreferences = context.getSharedPreferences("customerPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(String access_token, String id_customer, String nama_customer, String alamat_customer, String tanggal_lahir_customer,
                         String jenis_kelamin, String email_customer, String password, String no_telp_customer, String status_berkas,
                         String no_tanda_pengenal, String no_sim){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ACCESS_TOKEN, access_token);
        editor.putString(KEY_ID, id_customer);
        editor.putString(KEY_NAME, nama_customer);
        editor.putString(KEY_ALAMAT, alamat_customer);
        editor.putString(KEY_LAHIR, tanggal_lahir_customer);
        editor.putString(KEY_KELAMIN, jenis_kelamin);
        editor.putString(KEY_EMAIL, email_customer);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_TELEPON, no_telp_customer);
        editor.putString(KEY_BERKAS, status_berkas);
        editor.putString(KEY_PENGENAL, no_tanda_pengenal);
        editor.putString(KEY_SIM, no_sim);

        editor.commit();
    }

    public Customer getCustomerLogin(){
        String access_token, alamat_customer, password, nama_customer, jenis_kelamin, imgUrl, email_customer;
        String id_customer, no_telp_customer, status_berkas, no_tanda_pengenal, no_sim;
        String tanggal_lahir_customer;

        id_customer = sharedPreferences.getString(KEY_ID,null);
        access_token = sharedPreferences.getString(ACCESS_TOKEN, null);
        alamat_customer = sharedPreferences.getString(KEY_ALAMAT, null);
        password = sharedPreferences.getString(KEY_PASSWORD, null);
        nama_customer = sharedPreferences.getString(KEY_NAME, null);
        tanggal_lahir_customer = sharedPreferences.getString(KEY_LAHIR, null);
        jenis_kelamin = sharedPreferences.getString(KEY_KELAMIN, null);
        email_customer = sharedPreferences.getString(KEY_EMAIL,null);
        no_telp_customer = sharedPreferences.getString(KEY_TELEPON,null);
        status_berkas = sharedPreferences.getString(KEY_BERKAS,null);
        no_tanda_pengenal = sharedPreferences.getString(KEY_PENGENAL,null);
        no_sim = sharedPreferences.getString(KEY_SIM,null);

        return new Customer(access_token, id_customer,nama_customer, alamat_customer, tanggal_lahir_customer,
                jenis_kelamin, email_customer, password,
                no_telp_customer, status_berkas, no_tanda_pengenal, no_sim);
    }

    public boolean checkLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
