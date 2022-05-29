package com.ajr.atmajayarental;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.api.PegawaiApi;
import com.ajr.atmajayarental.models.laporan.Laporan5CustomerTerbanyak;
import com.ajr.atmajayarental.models.laporan.Laporan5CustomerTerbanyakResponse;
import com.ajr.atmajayarental.models.laporan.Laporan5DriverTeratas;
import com.ajr.atmajayarental.models.laporan.Laporan5DriverTeratasResponse;
import com.ajr.atmajayarental.models.laporan.LaporanDetailPen;
import com.ajr.atmajayarental.models.laporan.LaporanDetailPenResponse;
import com.ajr.atmajayarental.models.laporan.LaporanPenyewaan;
import com.ajr.atmajayarental.models.laporan.LaporanPenyewaanResponse;
import com.ajr.atmajayarental.models.Pegawai;
import com.ajr.atmajayarental.models.laporan.LaporanPerformaDriver;
import com.ajr.atmajayarental.models.laporan.LaporanPerformaDriverResponse;
import com.ajr.atmajayarental.preferences.PegawaiPreferences;
import com.ajr.atmajayarental.screen.LoginActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ManagerMainActivity extends AppCompatActivity{

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView textTanggalMulai, textTanggalSelesai;
    TextInputLayout textInputLayoutMulai, textInputLayoutSelesai;
    private TextView textNama;
    private ImageButton btnLogout;
    private CardView laporanPenyewaanMobil, laporanDetailPen, laporan5DriverTeratas ,
                        laporanPerformaDriver, laporan5CustomerTerajin;
    private PegawaiPreferences pegawaiPreferences;
    private Pegawai pegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        pegawaiPreferences = new PegawaiPreferences(getApplicationContext());
        pegawai = pegawaiPreferences.getPegawaiLogin();

        textTanggalMulai = findViewById(R.id.autoTanggalMulai);
        textTanggalSelesai = findViewById(R.id.autoTanggalSelesai);
        textNama = findViewById(R.id.textNamaManager);
        laporanPenyewaanMobil = findViewById(R.id.laporanPendapatanMobil);
        laporanDetailPen = findViewById(R.id.laporanDetailPen);
        laporan5DriverTeratas = findViewById(R.id.laporanTop5Driver);
        laporanPerformaDriver = findViewById(R.id.laporanPerformaDriver);
        laporan5CustomerTerajin = findViewById(R.id.laporan5Customer);

        textNama.setText(pegawai.getNama_pegawai());
        btnLogout = findViewById(R.id.btnLogout);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

//        showPegawaiData();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                new AlertDialog.Builder(ManagerMainActivity.this)
                        .setTitle("WARNING")
                        .setMessage("Apakah Anda Yakin ingin Logout?")

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                pegawaiPreferences.logout();
                                Toast.makeText(ManagerMainActivity.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
                                checkLogin();
                                alertDialog.dismiss();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        textTanggalMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ManagerMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year+"-"+month+"-"+day;
                        textTanggalMulai.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        textTanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ManagerMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year+"-"+month+"-"+day;
                        textTanggalSelesai.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        laporanPenyewaanMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textTanggalMulai.getText().toString().trim().isEmpty() || textTanggalSelesai.getText().toString().trim().isEmpty()){
                    Toast.makeText(ManagerMainActivity.this, "Silakan Inputan Rentang Tanggal", Toast.LENGTH_SHORT).show();
                }
                else{
                    getResponseLaporanPenyewaanMobil(textTanggalMulai.getText().toString().trim(), textTanggalSelesai.getText().toString().trim());
                }
            }
        });

        laporanDetailPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textTanggalMulai.getText().toString().trim().isEmpty() || textTanggalSelesai.getText().toString().trim().isEmpty()){
                    Toast.makeText(ManagerMainActivity.this, "Silakan Inputan Rentang Tanggal", Toast.LENGTH_SHORT).show();
                }
                else{
                    getResponseDetailPendapatan(textTanggalMulai.getText().toString().trim(), textTanggalSelesai.getText().toString().trim());
                }
            }
        });

        laporan5DriverTeratas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textTanggalMulai.getText().toString().trim().isEmpty() || textTanggalSelesai.getText().toString().trim().isEmpty()){
                    Toast.makeText(ManagerMainActivity.this, "Silakan Inputan Rentang Tanggal", Toast.LENGTH_SHORT).show();
                }
                else{
                    getResponse5DriverTeratas(textTanggalMulai.getText().toString().trim(), textTanggalSelesai.getText().toString().trim());
                }
            }
        });

        laporanPerformaDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textTanggalMulai.getText().toString().trim().isEmpty() || textTanggalSelesai.getText().toString().trim().isEmpty()){
                    Toast.makeText(ManagerMainActivity.this, "Silakan Inputan Rentang Tanggal", Toast.LENGTH_SHORT).show();
                }
                else{
                    getResponsePerformaDriver(textTanggalMulai.getText().toString().trim(), textTanggalSelesai.getText().toString().trim());
                }
            }
        });

        laporan5CustomerTerajin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textTanggalMulai.getText().toString().trim().isEmpty() || textTanggalSelesai.getText().toString().trim().isEmpty()){
                    Toast.makeText(ManagerMainActivity.this, "Silakan Inputan Rentang Tanggal", Toast.LENGTH_SHORT).show();
                }
                else{
                    getResponse5CustomerTerajin(textTanggalMulai.getText().toString().trim(), textTanggalSelesai.getText().toString().trim());
                }
            }
        });
    }

    private void checkLogin() {
//        Fungsi ini akan mengecek jika user login,
//        akan memunculkan toast jika tidak redirect ke login activity
        if (!pegawaiPreferences.checkLogin()) {
            startActivity(new Intent(ManagerMainActivity.this, LoginActivity.class));
            finish();
        }
        else {
            Toast.makeText(ManagerMainActivity.this.getApplicationContext(), "Login !!", Toast.LENGTH_SHORT).show();
        }
    }

    // LAPORAN PENYEWAAN MOBIL ( TIPE MOBIL, NAMA MOBIL, JUMLAH PEMINJAMAN, PENDAPATAN )
    public void getResponseLaporanPenyewaanMobil(String tanggal_awal, String tanggal_akhir){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, PegawaiApi.GET_LAPORAN_SEWA_MOBIL + tanggal_awal + '/' + tanggal_akhir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                LaporanPenyewaanResponse laporanPDFResponse = gson.fromJson(response, LaporanPenyewaanResponse.class);
                List<LaporanPenyewaan> laporanPDFModelList = laporanPDFResponse.getLaporanPenyewaanList();
                try {
                    cetakPDfLaporanPenyewaan(laporanPDFModelList, tanggal_awal, tanggal_akhir);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ManagerMainActivity.this, "Berhasil Melampirkan PDF", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(ManagerMainActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ManagerMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void cetakPDfLaporanPenyewaan(List<LaporanPenyewaan> laporanPenyewaanList, String tanggal_awal, String tanggal_akhir) throws DocumentException, FileNotFoundException {

//        File folder = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "Laporan Penyewaan";
//        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (!folder.exists()) {
            folder.mkdir();
        }
        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = "LaporanPenyewaan"+ currentTime.getTime() + ".pdf";
        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new
                com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN PENYEWAAN MOBIL \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD, BaseColor.BLACK));
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});
//        PdfPTable tables = new PdfPTable(4);

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
//        tables.setWidths(new int[]{1, 1, 1, 2});
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);
        Paragraph kepada = new Paragraph(
                "Kepada Yth: \n" + "Manager Atma Jogja Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                        Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);
        Paragraph NomorTanggal = new Paragraph(
                "No : " + "123456789" + "\n\n" +
                        "Tanggal : " + new SimpleDateFormat("dd/MM/yyyy",
                        Locale.getDefault()).format(currentTime) + "\n",
                new
                        com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));
        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan laporan penyewaan ini pada bulan : ", f);
        Paragraph rentan_tanggal = new Paragraph("\nDari " + tanggal_awal+ " Sampai " + tanggal_akhir +": \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        rentan_tanggal.setIndentationLeft(20);
        document.add(rentan_tanggal);
        PdfPTable tableHeader = new PdfPTable(new float[]{3, 4, 4, 5});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.WHITE);
        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("Tipe Mobil", f));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(4);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Mobil", f));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Peminjaman", f));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        PdfPCell h4 = new PdfPCell(new Phrase("Pendapatan", f));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
//        PdfPCell h5 = new PdfPCell(new Phrase("Gaji"));
//        h5.setHorizontalAlignment(Element.ALIGN_CENTER);
//        h5.setPaddingBottom(5);
        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);
//        tableHeader.addCell(h5);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(new BaseColor(0, 57, 108));
        }
        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{3, 4, 4, 5});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        // masukan data pegawai jadi baris
        for (LaporanPenyewaan P : laporanPenyewaanList) {
            tableData.addCell(P.getTipe_mobil());
            tableData.addCell(P.getNama_mobil());
            tableData.addCell(String.valueOf(P.getJumlah_peminjaman()));
            tableData.addCell(kursIndonesia.format(P.getPendapatan()));
        }
        document.add(tableData);
        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);
        String tanggalCetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tanggalCetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    // LAPORAN DETAIL PENDAPATAN TRANSAKSI (NAMA CUSTOMER, NAMA MOBIL, JENIS TRANSAKSI, JUMLAH TRANSAKSI, PENDAPATAN)
    public void getResponseDetailPendapatan(String tanggal_awal, String tanggal_akhir){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, PegawaiApi.GET_LAPORAN_DETAIL_PENDAPATAN + tanggal_awal + '/' + tanggal_akhir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                LaporanDetailPenResponse laporanDetailPenResponse = gson.fromJson(response, LaporanDetailPenResponse.class);
                List<LaporanDetailPen> laporanDetailPenList = laporanDetailPenResponse.getLaporanDetailPenList();
                try {
                    cetakPDfDetailPendapatan(laporanDetailPenList, tanggal_awal, tanggal_akhir);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ManagerMainActivity.this, "Berhasil Melampirkan PDF", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(ManagerMainActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ManagerMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void cetakPDfDetailPendapatan(List<LaporanDetailPen> laporanDetailPenList, String tanggal_awal, String tanggal_akhir) throws DocumentException, FileNotFoundException {

        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
//        File folder = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "Laporan Detail";
        if (!folder.exists()) {
            folder.mkdir();
        }
        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = "LaporanDtlPendapatan"+ currentTime.getTime() + ".pdf";
        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new
                com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN DETAIL PENDAPATAN TRANSAKSI \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD, BaseColor.BLACK));
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});
//        PdfPTable tables = new PdfPTable(4);

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
//        tables.setWidths(new int[]{1, 1, 1, 2});
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);
        Paragraph kepada = new Paragraph(
                "Kepada Yth: \n" + "Manager Atma Jogja Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                        Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);
        Paragraph NomorTanggal = new Paragraph(
                "No : " + "123456789" + "\n\n" +
                        "Tanggal : " + new SimpleDateFormat("dd/MM/yyyy",
                        Locale.getDefault()).format(currentTime) + "\n",
                new
                        com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));
        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan laporan penyewaan ini pada bulan : ", f);
        Paragraph rentan_tanggal = new Paragraph("\nDari " + tanggal_awal+ " Sampai " + tanggal_akhir +": \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        rentan_tanggal.setIndentationLeft(20);
        document.add(rentan_tanggal);
        PdfPTable tableHeader = new PdfPTable(new float[]{3, 3, 4, 4, 5});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.WHITE);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("Nama Customer", f));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(4);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Mobil", f));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h3 = new PdfPCell(new Phrase("Jenis Transaksi" , f));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        PdfPCell h4 = new PdfPCell(new Phrase("Jumlah Transaksi", f));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        PdfPCell h5 = new PdfPCell(new Phrase("Pendapatan", f));
        h5.setHorizontalAlignment(Element.ALIGN_CENTER);
        h5.setPaddingBottom(5);
        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);
        tableHeader.addCell(h5);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(new BaseColor(0, 57, 108));
        }
        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{3, 3, 4, 4, 5});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        // masukan data pegawai jadi baris
        for (LaporanDetailPen P : laporanDetailPenList) {
            tableData.addCell(P.getNama_customer());
            tableData.addCell(P.getNama_mobil());
            tableData.addCell(P.getJenis_Transaksi());
            tableData.addCell(String.valueOf(P.getJumlah_Transaksi()));
            tableData.addCell(kursIndonesia.format(P.getPendapatan()));
        }
        document.add(tableData);
        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);
        String tanggalCetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tanggalCetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    // LAPORAN 5 DRIVER DENGAN JUMLAH TRANSAKSI TERBANYAK (ID DRIVER, NAMA DRIVER, JUMLAH TRANSAKSI
    public void getResponse5DriverTeratas(String tanggal_awal, String tanggal_akhir){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, PegawaiApi.GET_5_DRIVER_TERATAS + tanggal_awal + '/' + tanggal_akhir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Laporan5DriverTeratasResponse laporan5DriverTeratasResponse = gson.fromJson(response, Laporan5DriverTeratasResponse.class);
                List<Laporan5DriverTeratas> laporan5DriverTeratasList = laporan5DriverTeratasResponse.getLaporan5DriverTeratasList();
                try {
                    cetakPDf5DriverTeratas(laporan5DriverTeratasList, tanggal_awal, tanggal_akhir);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ManagerMainActivity.this, "Berhasil Melampirkan PDF", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(ManagerMainActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ManagerMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void cetakPDf5DriverTeratas(List<Laporan5DriverTeratas> laporan5DriverTeratasList, String tanggal_awal, String tanggal_akhir) throws DocumentException, FileNotFoundException {

        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
//        File folder = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "Laporan Detail";
        if (!folder.exists()) {
            folder.mkdir();
        }
        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = "Laporan5DriverTeratas"+ currentTime.getTime() + ".pdf";
        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new
                com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN 5 TOP DRIVER DENGAN TRANSAKSI TERBANYAK \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD, BaseColor.BLACK));
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});
//        PdfPTable tables = new PdfPTable(4);

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
//        tables.setWidths(new int[]{1, 1, 1, 2});
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);
        Paragraph kepada = new Paragraph(
                "Kepada Yth: \n" + "Manager Atma Jogja Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                        Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);
        Paragraph NomorTanggal = new Paragraph(
                "No : " + "123456789" + "\n\n" +
                        "Tanggal : " + new SimpleDateFormat("dd/MM/yyyy",
                        Locale.getDefault()).format(currentTime) + "\n",
                new
                        com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));
        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan laporan TOP 5 Driver ini pada bulan : ", f);
        Paragraph rentan_tanggal = new Paragraph("\nDari " + tanggal_awal+ " Sampai " + tanggal_akhir +": \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        rentan_tanggal.setIndentationLeft(20);
        document.add(rentan_tanggal);
        PdfPTable tableHeader = new PdfPTable(new float[]{4, 4, 4});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.WHITE);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("ID Driver", f));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(4);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Driver", f));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Transaksi" , f));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(new BaseColor(0, 57, 108));
        }
        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{4, 4, 4});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        // masukan data pegawai jadi baris
        for (Laporan5DriverTeratas P : laporan5DriverTeratasList) {
            tableData.addCell(P.getId_driver());
            tableData.addCell(P.getNama_driver());
            tableData.addCell(String.valueOf(P.getJumlah_Transaksi()));
        }
        document.add(tableData);
        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);
        String tanggalCetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tanggalCetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    // LAPORAN PERFORMA DRIVER (ID DRIVER, NAMA DRIVER, JUMLAH TRANSAKSI, RERATA RATING
    public void getResponsePerformaDriver(String tanggal_awal, String tanggal_akhir){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, PegawaiApi.GET_LAPORAN_PERFORMA_DRIVER + tanggal_awal + '/' + tanggal_akhir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                LaporanPerformaDriverResponse laporanPerformaDriverResponse = gson.fromJson(response, LaporanPerformaDriverResponse.class);
                List<LaporanPerformaDriver> laporanPerformaDriverList = laporanPerformaDriverResponse.getLaporanPerformaDriverList();
                try {
                    cetakPDfPerformaDriver(laporanPerformaDriverList, tanggal_awal, tanggal_akhir);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ManagerMainActivity.this, "Berhasil Melampirkan PDF", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(ManagerMainActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ManagerMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void cetakPDfPerformaDriver(List<LaporanPerformaDriver> laporanPerformaDriverList, String tanggal_awal, String tanggal_akhir) throws DocumentException, FileNotFoundException {

        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
//        File folder = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "Laporan Detail";
        if (!folder.exists()) {
            folder.mkdir();
        }
        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = "LaporanPerformaDriver"+ currentTime.getTime() + ".pdf";
        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new
                com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN PERFORMA DRIVER \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD, BaseColor.BLACK));
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});
//        PdfPTable tables = new PdfPTable(4);

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
//        tables.setWidths(new int[]{1, 1, 1, 2});
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);
        Paragraph kepada = new Paragraph(
                "Kepada Yth: \n" + "Manager Atma Jogja Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                        Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);
        Paragraph NomorTanggal = new Paragraph(
                "No : " + "123456789" + "\n\n" +
                        "Tanggal : " + new SimpleDateFormat("dd/MM/yyyy",
                        Locale.getDefault()).format(currentTime) + "\n",
                new
                        com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));
        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut ini merupakan laporan Performa Driver pada bulan : ", f);
        Paragraph rentan_tanggal = new Paragraph("\nDari " + tanggal_awal+ " Sampai " + tanggal_akhir +": \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        rentan_tanggal.setIndentationLeft(20);
        document.add(rentan_tanggal);
        PdfPTable tableHeader = new PdfPTable(new float[]{4, 4, 4, 4});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.WHITE);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("ID Driver", f));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(4);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Driver", f));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Transaksi" , f));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        PdfPCell h4 = new PdfPCell(new Phrase("Rerata Rating" , f));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(new BaseColor(0, 57, 108));
        }
        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{4, 4, 4, 4});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        // masukan data pegawai jadi baris
        for (LaporanPerformaDriver P : laporanPerformaDriverList) {
            tableData.addCell(P.getId_driver());
            tableData.addCell(P.getNama_driver());
            tableData.addCell(String.valueOf(P.getJumlah_Transaksi()));
            tableData.addCell(String.valueOf(P.getRerata_rating_driver()));
        }
        document.add(tableData);
        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);
        String tanggalCetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tanggalCetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    // LAPORAN PERFORMA DRIVER (ID DRIVER, NAMA DRIVER, JUMLAH TRANSAKSI, RERATA RATING
    public void getResponse5CustomerTerajin(String tanggal_awal, String tanggal_akhir){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, PegawaiApi.GET_5_CUSTOMER_TERBANYAK + tanggal_awal + '/' + tanggal_akhir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Laporan5CustomerTerbanyakResponse laporan5CustomerTerbanyakResponse = gson.fromJson(response, Laporan5CustomerTerbanyakResponse.class);
                List<Laporan5CustomerTerbanyak> laporan5CustomerTerbanyakList = laporan5CustomerTerbanyakResponse.getLaporan5CustomerTerbanyakList();
                try {
                    cetakPDf5CustomerTerajin(laporan5CustomerTerbanyakList, tanggal_awal, tanggal_akhir);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ManagerMainActivity.this, "Berhasil Melampirkan PDF", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(ManagerMainActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ManagerMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void cetakPDf5CustomerTerajin(List<Laporan5CustomerTerbanyak> laporan5CustomerTerbanyakList, String tanggal_awal, String tanggal_akhir) throws DocumentException, FileNotFoundException {

        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
//        File folder = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "Laporan Detail";
        if (!folder.exists()) {
            folder.mkdir();
        }
        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = "Laporan5CustomerTerajin"+ currentTime.getTime() + ".pdf";
        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new
                com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("LAPORAN TOP 5 CUSTOMER TERBANYAK \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD, BaseColor.BLACK));
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});
//        PdfPTable tables = new PdfPTable(4);

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
//        tables.setWidths(new int[]{1, 1, 1, 2});
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);
        Paragraph kepada = new Paragraph(
                "Kepada Yth: \n" + "Manager Atma Jogja Rental" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                        Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);
        Paragraph NomorTanggal = new Paragraph(
                "No : " + "123456789" + "\n\n" +
                        "Tanggal : " + new SimpleDateFormat("dd/MM/yyyy",
                        Locale.getDefault()).format(currentTime) + "\n",
                new
                        com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));
        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut ini merupakan TOP 5 Customer pada bulan : ", f);
        Paragraph rentan_tanggal = new Paragraph("\nDari " + tanggal_awal+ " Sampai " + tanggal_akhir +": \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        rentan_tanggal.setIndentationLeft(20);
        document.add(rentan_tanggal);
        PdfPTable tableHeader = new PdfPTable(new float[]{4, 4});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.WHITE);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("Nama Customer", f));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(4);
        PdfPCell h2 = new PdfPCell(new Phrase("Jumlah Transaksi", f));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        tableHeader.addCell(h1);
        tableHeader.addCell(h2);

        // Beri warna untuk kolumn
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(new BaseColor(0, 57, 108));
        }
        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{4, 4});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        // masukan data pegawai jadi baris
        for (Laporan5CustomerTerbanyak P : laporan5CustomerTerbanyakList) {
            tableData.addCell(P.getNama_customer());
            tableData.addCell(String.valueOf(P.getJumlah_Transaksi()));
        }
        document.add(tableData);
        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);
        String tanggalCetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tanggalCetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    private void previewPdf(File pdfFile) {
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(testIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Uri uri;
            uri = FileProvider.getUriForFile(this, getPackageName() +
                            ".provider",
                    pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(uri, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            this.grantUriPermission(getPackageName(), uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);
        }
    }
}