package com.ajr.atmajayarental.models;

public class Driver {
    private String access_token;
    private String id_driver, nama_driver, alamat_driver, email_driver, status_ketersediaan_driver, status_berkas;
    private int isEnglish;
    private String tanggal_lahir_driver, jenis_kelamin, no_telp_driver, url_foto_driver, password;
    private float tarif_sewa_driver;
    private String berkas_bebas_napza, berkas_sim, berkas_sehat_jiwa, berkas_sehat_jasmani, berkas_skck;
    private float rerata_rating_driver;
    private int isAktif;

    public Driver(String access_token, String id_driver, String nama_driver, String alamat_driver, String email_driver, String status_ketersediaan_driver,
                  String status_berkas, int isEnglish, String tanggal_lahir_driver, String jenis_kelamin,
                  String no_telp_driver, String url_foto_driver, String password, float tarif_sewa_driver,
                  String berkas_bebas_napza, String berkas_sim, String berkas_sehat_jiwa, String berkas_sehat_jasmani,
                  String berkas_skck, float rerata_rating_driver, int isAktif) {
        this.access_token = access_token;
        this.id_driver = id_driver;
        this.nama_driver = nama_driver;
        this.alamat_driver = alamat_driver;
        this.email_driver = email_driver;
        this.status_ketersediaan_driver = status_ketersediaan_driver;
        this.status_berkas = status_berkas;
        this.isEnglish = isEnglish;
        this.tanggal_lahir_driver = tanggal_lahir_driver;
        this.jenis_kelamin = jenis_kelamin;
        this.no_telp_driver = no_telp_driver;
        this.url_foto_driver = url_foto_driver;
        this.password = password;
        this.tarif_sewa_driver = tarif_sewa_driver;
        this.berkas_bebas_napza = berkas_bebas_napza;
        this.berkas_sim = berkas_sim;
        this.berkas_sehat_jiwa = berkas_sehat_jiwa;
        this.berkas_sehat_jasmani = berkas_sehat_jasmani;
        this.berkas_skck = berkas_skck;
        this.rerata_rating_driver = rerata_rating_driver;
        this.isAktif = isAktif;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getAlamat_driver() {
        return alamat_driver;
    }

    public void setAlamat_driver(String alamat_driver) {
        this.alamat_driver = alamat_driver;
    }

    public String getEmail_driver() {
        return email_driver;
    }

    public void setEmail_driver(String email_driver) {
        this.email_driver = email_driver;
    }

    public String getStatus_ketersediaan_driver() {
        return status_ketersediaan_driver;
    }

    public void setStatus_ketersediaan_driver(String status_ketersediaan_driver) {
        this.status_ketersediaan_driver = status_ketersediaan_driver;
    }

    public String getStatus_berkas() {
        return status_berkas;
    }

    public void setStatus_berkas(String status_berkas) {
        this.status_berkas = status_berkas;
    }

    public int isEnglish() {
        return isEnglish;
    }

    public void setEnglish(int english) {
        isEnglish = english;
    }

    public String getTanggal_lahir_driver() {
        return tanggal_lahir_driver;
    }

    public void setTanggal_lahir_driver(String tanggal_lahir_driver) {
        this.tanggal_lahir_driver = tanggal_lahir_driver;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_telp_driver() {
        return no_telp_driver;
    }

    public void setNo_telp_driver(String no_telp_driver) {
        this.no_telp_driver = no_telp_driver;
    }

    public String getUrl_foto_driver() {
        return url_foto_driver;
    }

    public void setUrl_foto_driver(String url_foto_driver) {
        this.url_foto_driver = url_foto_driver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getTarif_sewa_driver() {
        return tarif_sewa_driver;
    }

    public void setTarif_sewa_driver(float tarif_sewa_driver) {
        this.tarif_sewa_driver = tarif_sewa_driver;
    }

    public String getBerkas_bebas_napza() {
        return berkas_bebas_napza;
    }

    public void setBerkas_bebas_napza(String berkas_bebas_napza) {
        this.berkas_bebas_napza = berkas_bebas_napza;
    }

    public String getBerkas_sim() {
        return berkas_sim;
    }

    public void setBerkas_sim(String berkas_sim) {
        this.berkas_sim = berkas_sim;
    }

    public String getBerkas_sehat_jiwa() {
        return berkas_sehat_jiwa;
    }

    public void setBerkas_sehat_jiwa(String berkas_sehat_jiwa) {
        this.berkas_sehat_jiwa = berkas_sehat_jiwa;
    }

    public String getBerkas_sehat_jasmani() {
        return berkas_sehat_jasmani;
    }

    public void setBerkas_sehat_jasmani(String berkas_sehat_jasmani) {
        this.berkas_sehat_jasmani = berkas_sehat_jasmani;
    }

    public String getBerkas_skck() {
        return berkas_skck;
    }

    public void setBerkas_skck(String berkas_skck) {
        this.berkas_skck = berkas_skck;
    }

    public float getRerata_rating_driver() {
        return rerata_rating_driver;
    }

    public void setRerata_rating_driver(float rerata_rating_driver) {
        this.rerata_rating_driver = rerata_rating_driver;
    }

    public int isAktif() {
        return isAktif;
    }

    public void setAktif(int aktif) {
        isAktif = aktif;
    }
}
