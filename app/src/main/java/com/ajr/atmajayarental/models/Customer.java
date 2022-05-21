package com.ajr.atmajayarental.models;

import java.util.Date;

public class Customer {
    private String access_token;
    private String id_customer, nama_customer, alamat_customer, jenis_kelamin, email_customer, password;
    private String no_telp_customer, status_berkas, no_tanda_pengenal, no_sim;
    private String tanggal_lahir_customer;
    private String email;

    public Customer(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Customer(String access_token, String id_customer, String nama_customer, String alamat_customer, String tanggal_lahir_customer,
                    String jenis_kelamin, String email_customer, String password,
                    String no_telp_customer, String status_berkas, String no_tanda_pengenal, String no_sim) {
        this.access_token = access_token;
        this.id_customer = id_customer;
        this.nama_customer = nama_customer;
        this.alamat_customer = alamat_customer;
        this.tanggal_lahir_customer = tanggal_lahir_customer;
        this.jenis_kelamin = jenis_kelamin;
        this.email_customer = email_customer;
        this.password = password;
        this.no_telp_customer = no_telp_customer;
        this.status_berkas = status_berkas;
        this.no_tanda_pengenal = no_tanda_pengenal;
        this.no_sim = no_sim;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail_customer() {
        return email_customer;
    }

    public void setEmail_customer(String email_customer) {
        this.email_customer = email_customer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_telp_customer() {
        return no_telp_customer;
    }

    public void setNo_telp_customer(String no_telp_customer) {
        this.no_telp_customer = no_telp_customer;
    }

    public String getStatus_berkas() {
        return status_berkas;
    }

    public void setStatus_berkas(String status_berkas) {
        this.status_berkas = status_berkas;
    }

    public String getNo_tanda_pengenal() {
        return no_tanda_pengenal;
    }

    public void setNo_tanda_pengenal(String no_tanda_pengenal) {
        this.no_tanda_pengenal = no_tanda_pengenal;
    }

    public String getNo_sim() {
        return no_sim;
    }

    public void setNo_sim(String no_sim) {
        this.no_sim = no_sim;
    }

    public String getTanggal_lahir_customer() {
        return tanggal_lahir_customer;
    }

    public void setTanggal_lahir_customer(String tanggal_lahir_customer) {
        this.tanggal_lahir_customer = tanggal_lahir_customer;
    }
}
