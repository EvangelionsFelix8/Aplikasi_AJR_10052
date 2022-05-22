package com.ajr.atmajayarental.models;

public class RiwayatTrans {
    private String id_transaksi, id_driver, id_customer;
    private Long id_mobil, id_pegawai, id_promo;
    private String tanggal_transaksi, tanggal_pengembalian, tanggal_mulai, tanggal_selesai, status_transaksi,
            metode_pembayaran, bukti_bayar;
    private double total_harga, total_sewa_mobil, total_sewa_driver, total_denda, potongan_promo;
    private int rating_driver, rating_ajr;
    private String nama_pegawai, nama_mobil, nama_driver;
    private String jenis_promo;

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getJenis_promo() {
        return jenis_promo;
    }

    public void setJenis_promo(String jenis_promo) {
        this.jenis_promo = jenis_promo;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public RiwayatTrans(String id_transaksi, String id_driver, String id_customer, Long id_mobil, Long id_pegawai, Long id_promo,
                        String tanggal_transaksi, String tanggal_pengembalian, String tanggal_mulai, String tanggal_selesai,
                        String status_transaksi, String metode_pembayaran, String bukti_bayar, double total_harga,
                        double total_sewa_mobil, double total_sewa_driver, double total_denda, double potongan_promo,
                        int rating_driver, int rating_ajr) {
        this.id_transaksi = id_transaksi;
        this.id_driver = id_driver;
        this.id_customer = id_customer;
        this.id_mobil = id_mobil;
        this.id_pegawai = id_pegawai;
        this.id_promo = id_promo;
        this.tanggal_transaksi = tanggal_transaksi;
        this.tanggal_pengembalian = tanggal_pengembalian;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_selesai = tanggal_selesai;
        this.status_transaksi = status_transaksi;
        this.metode_pembayaran = metode_pembayaran;
        this.bukti_bayar = bukti_bayar;
        this.total_harga = total_harga;
        this.total_sewa_mobil = total_sewa_mobil;
        this.total_sewa_driver = total_sewa_driver;
        this.total_denda = total_denda;
        this.potongan_promo = potongan_promo;
        this.rating_driver = rating_driver;
        this.rating_ajr = rating_ajr;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public Long getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(Long id_mobil) {
        this.id_mobil = id_mobil;
    }

    public Long getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(Long id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public Long getId_promo() {
        return id_promo;
    }

    public void setId_promo(Long id_promo) {
        this.id_promo = id_promo;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getTanggal_pengembalian() {
        return tanggal_pengembalian;
    }

    public void setTanggal_pengembalian(String tanggal_pengembalian) {
        this.tanggal_pengembalian = tanggal_pengembalian;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setMetode_pembayaran(String metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }

    public String getBukti_bayar() {
        return bukti_bayar;
    }

    public void setBukti_bayar(String bukti_bayar) {
        this.bukti_bayar = bukti_bayar;
    }

    public double getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(double total_harga) {
        this.total_harga = total_harga;
    }

    public double getTotal_sewa_mobil() {
        return total_sewa_mobil;
    }

    public void setTotal_sewa_mobil(double total_sewa_mobil) {
        this.total_sewa_mobil = total_sewa_mobil;
    }

    public double getTotal_sewa_driver() {
        return total_sewa_driver;
    }

    public void setTotal_sewa_driver(double total_sewa_driver) {
        this.total_sewa_driver = total_sewa_driver;
    }

    public double getTotal_denda() {
        return total_denda;
    }

    public void setTotal_denda(double total_denda) {
        this.total_denda = total_denda;
    }

    public double getPotongan_promo() {
        return potongan_promo;
    }

    public void setPotongan_promo(double potongan_promo) {
        this.potongan_promo = potongan_promo;
    }

    public int getRating_driver() {
        return rating_driver;
    }

    public void setRating_driver(int rating_driver) {
        this.rating_driver = rating_driver;
    }

    public int getRating_ajr() {
        return rating_ajr;
    }

    public void setRating_ajr(int rating_ajr) {
        this.rating_ajr = rating_ajr;
    }
}
