<div align="center">

# 🚗 ATMA JOGJA RENTAL  
### *Car Rental Information System*

📚 **Tugas Mata Kuliah Pengembangan Perangkat Lunak**  
🎯 **Pendekatan Berorientasi Objek (OOP)**  

</div>

---

## 📖 Tentang Proyek

**Atma Jogja Rental (AJR)** adalah perusahaan jasa penyewaan mobil yang beroperasi di Kota Yogyakarta sejak **19 Februari 2019**.  
Perusahaan ini melayani penyewaan mobil **dengan driver maupun tanpa driver**, serta bekerja sama dengan mitra pemilik kendaraan.

Proyek ini bertujuan untuk membangun **Sistem Informasi Atma Jogja Rental** yang:
- Menggantikan proses manual
- Meningkatkan efisiensi operasional
- Mendukung pengambilan keputusan manajerial
- Siap dikembangkan untuk pembukaan cabang baru

---

## 🎯 Tujuan Pengembangan Sistem

> Sistem ini dikembangkan untuk membantu seluruh stakeholder AJR dalam menjalankan proses bisnis secara terintegrasi.

✔️ Digitalisasi proses penyewaan  
✔️ Pengelolaan data mobil, pegawai, driver, dan customer  
✔️ Monitoring transaksi & pendapatan  
✔️ Penyediaan laporan manajerial  
✔️ Peningkatan kualitas layanan customer  

---

## 👥 Aktor Sistem

| Aktor | Peran |
|------|------|
| **Owner** | Monitoring bisnis & kebijakan |
| **Manager** | Penjadwalan, promo, laporan |
| **Admin** | Pengelolaan data master |
| **Customer Service** | Transaksi & verifikasi |
| **Driver** | Layanan antar customer |
| **Customer** | Penyewaan kendaraan |

---

## 🧩 Ruang Lingkup Sistem

### 🔐 Autentikasi
- Login berbasis **email**
- Hak akses sesuai peran pengguna

### 🚘 Manajemen Kendaraan
- Mobil milik perusahaan & mitra
- Monitoring kontrak kendaraan mitra
- Pencarian & filter data mobil

### 👤 Manajemen Pegawai & Driver
- Data pegawai (Admin & CS)
- Penjadwalan shift mingguan
- Driver freelance dengan sistem rating
- Upload dokumen driver secara digital

### 📋 Manajemen Customer
- Registrasi customer
- Penyimpanan dokumen persyaratan
- Riwayat transaksi

### 💰 Transaksi Penyewaan
- 1 transaksi = 1 mobil
- Dengan / tanpa driver
- Pembayaran cash & transfer
- Promo (maksimal 1 per transaksi)
- Biaya ekstensi keterlambatan

### ⭐ Penilaian Driver
- Rating oleh customer
- Rekap performa bulanan
- Driver dapat melihat profil & histori

---

## 📊 Laporan Manajerial

> Laporan hanya dapat diakses oleh **Manager** melalui aplikasi mobile.

📈 Jenis laporan yang tersedia:
- Penyewaan per bulan & tahun
- Pendapatan detail
- Top 5 driver dengan transaksi terbanyak
- Top 5 customer dengan transaksi terbanyak
- Laporan performa driver bulanan

---

## 📱 Platform Sistem

| Platform | Pengguna |
|--------|---------|
| 🌐 **Web Application** | Admin, CS, Manager |
| 📲 **Mobile Application** | Customer, Manager, Driver |

---

## 🖼️ Screenshot Antarmuka (UI)

### 🌐 Web Application

![Login](screenshots/web-login.png)
![Dashboard](screenshots/web-dashboard.png)
![Manajemen Mobil](screenshots/web-mobil.png)

### 🌐 Web Application
![Home](screenshots/mobile-home.png)
![Riwayat Transaksi](screenshots/mobile-riwayat.png)
![Laporan](screenshots/mobile-laporan.png)
