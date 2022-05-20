package com.ajr.atmajayarental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ManagerMainActivity extends AppCompatActivity{

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView textTanggalMulai, textTanggalSelesai;
    TextInputLayout textInputLayoutMulai, textInputLayoutSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        textTanggalMulai = findViewById(R.id.autoTanggalMulai);
        textTanggalSelesai = findViewById(R.id.autoTanggalSelesai);
        textInputLayoutMulai = findViewById(R.id.tanggalMulai);
        textInputLayoutSelesai = findViewById(R.id.tanggalSelesai);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        textTanggalMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ManagerMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
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
                        String date = day+"/"+month+"/"+year;
                        textTanggalSelesai.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}