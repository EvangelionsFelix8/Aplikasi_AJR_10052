package com.ajr.atmajayarental.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.Pegawai;

import java.math.BigInteger;

public class PegawaiPreferences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public static final String IS_LOGIN = "isLogin";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String KEY_ID = "id_pegawai";
    public static final String KEY_ID_ROLE = "id_role";
    public static final String KEY_NAME = "nama_pegawai";
    public static final String KEY_ALAMAT = "alamat_pegawai";
    public static final String KEY_EMAIL = "email_pegawai";
    public static final String KEY_LAHIR = "tanggal_lahir_pegawai";
    public static final String KEY_KELAMIN = "jenis_kelamin_pegawai";
    public static final String KEY_TELEPON = "no_telp_pegawai";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FOTO = "url_foto_pegawai";
    public static final String KEY_AKTIF = "isAktif";

    public PegawaiPreferences(Context context){
        this.context = context;

        sharedPreferences = context.getSharedPreferences("pegawaiPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(String access_token, Long id_pegawai, Long id_role, String nama_pegawai, String alamat_pegawai,
                         String email_pegawai, String tanggal_lahir_pegawai, String jenis_kelamin_pegawai,
                         String no_telp_pegawai, String password, String url_foto_pegawai, int isAktif){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ACCESS_TOKEN, access_token);
        editor.putLong(KEY_ID, id_pegawai);
        editor.putLong(KEY_ID_ROLE, id_role);
        editor.putString(KEY_NAME, nama_pegawai);
        editor.putString(KEY_ALAMAT, alamat_pegawai);
        editor.putString(KEY_EMAIL, email_pegawai);
        editor.putString(KEY_LAHIR, tanggal_lahir_pegawai);
        editor.putString(KEY_KELAMIN, jenis_kelamin_pegawai);
        editor.putString(KEY_TELEPON, no_telp_pegawai);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_FOTO, url_foto_pegawai);
        editor.putInt(KEY_AKTIF, isAktif);

        editor.commit();
    }

    public Pegawai getPegawaiLogin(){
        String access_token;
        Long id_pegawai;
        Long id_role;
        String nama_pegawai;
        String alamat_pegawai, email_pegawai, tanggal_lahir_pegawai, jenis_kelamin_pegawai, no_telp_pegawai;
        String password, url_foto_pegawai;
        int isAktif;

        access_token = sharedPreferences.getString(ACCESS_TOKEN, null);
        id_pegawai = sharedPreferences.getLong(KEY_ID,0);
        id_role = sharedPreferences.getLong(KEY_ID_ROLE, 0);
        nama_pegawai = sharedPreferences.getString(KEY_NAME, null);
        alamat_pegawai = sharedPreferences.getString(KEY_ALAMAT, null);
        email_pegawai = sharedPreferences.getString(KEY_EMAIL, null);
        tanggal_lahir_pegawai = sharedPreferences.getString(KEY_LAHIR,null);
        jenis_kelamin_pegawai = sharedPreferences.getString(KEY_KELAMIN,null);
        no_telp_pegawai = sharedPreferences.getString(KEY_TELEPON,null);
        password = sharedPreferences.getString(KEY_PASSWORD, null);
        url_foto_pegawai = sharedPreferences.getString(KEY_FOTO,null);
        isAktif = sharedPreferences.getInt(KEY_AKTIF,2);

        return new Pegawai(access_token, id_pegawai, id_role, nama_pegawai, alamat_pegawai, email_pegawai,
                tanggal_lahir_pegawai,  jenis_kelamin_pegawai, no_telp_pegawai, password,
                url_foto_pegawai, isAktif);
    }

    public boolean checkLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
