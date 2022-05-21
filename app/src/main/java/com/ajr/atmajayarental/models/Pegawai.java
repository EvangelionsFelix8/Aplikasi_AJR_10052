package com.ajr.atmajayarental.models;

public class Pegawai {
    private String access_token;
    private Long id_pegawai;
    private Long id_role;
    private String nama_pegawai;
    private String alamat_pegawai, email_pegawai, tanggal_lahir_pegawai, jenis_kelamin_pegawai, no_telp_pegawai;
    private String password, url_foto_pegawai;
    private int isAktif;

    public Pegawai(String access_token, Long id_pegawai, Long id_role, String nama_pegawai, String alamat_pegawai, String email_pegawai,
                   String tanggal_lahir_pegawai, String jenis_kelamin_pegawai, String no_telp_pegawai, String password,
                   String url_foto_pegawai, int isAktif) {
        this.access_token = access_token;
        this.id_pegawai = id_pegawai;
        this.id_role = id_role;
        this.nama_pegawai = nama_pegawai;
        this.alamat_pegawai = alamat_pegawai;
        this.email_pegawai = email_pegawai;
        this.tanggal_lahir_pegawai = tanggal_lahir_pegawai;
        this.jenis_kelamin_pegawai = jenis_kelamin_pegawai;
        this.no_telp_pegawai = no_telp_pegawai;
        this.password = password;
        this.url_foto_pegawai = url_foto_pegawai;
        this.isAktif = isAktif;
    }



    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(Long id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public Long getId_role() {
        return id_role;
    }

    public void setId_role(Long id_role) {
        this.id_role = id_role;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getAlamat_pegawai() {
        return alamat_pegawai;
    }

    public void setAlamat_pegawai(String alamat_pegawai) {
        this.alamat_pegawai = alamat_pegawai;
    }

    public String getEmail_pegawai() {
        return email_pegawai;
    }

    public void setEmail_pegawai(String email_pegawai) {
        this.email_pegawai = email_pegawai;
    }

    public String getTanggal_lahir_pegawai() {
        return tanggal_lahir_pegawai;
    }

    public void setTanggal_lahir_pegawai(String tanggal_lahir_pegawai) {
        this.tanggal_lahir_pegawai = tanggal_lahir_pegawai;
    }

    public String getJenis_kelamin_pegawai() {
        return jenis_kelamin_pegawai;
    }

    public void setJenis_kelamin_pegawai(String jenis_kelamin_pegawai) {
        this.jenis_kelamin_pegawai = jenis_kelamin_pegawai;
    }

    public String getNo_telp_pegawai() {
        return no_telp_pegawai;
    }

    public void setNo_telp_pegawai(String no_telp_pegawai) {
        this.no_telp_pegawai = no_telp_pegawai;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getUrl_foto_pegawai() {
        return url_foto_pegawai;
    }

    public void setUrl_foto_pegawai(String url_foto_pegawai) {
        this.url_foto_pegawai = url_foto_pegawai;
    }

    public int isAktif() {
        return isAktif;
    }

    public void setAktif(int aktif) {
        isAktif = aktif;
    }
}
