package com.ajr.atmajayarental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.Pegawai;
import com.ajr.atmajayarental.preferences.DriverPreferences;
import com.ajr.atmajayarental.preferences.PegawaiPreferences;
import com.ajr.atmajayarental.screen.DriverHomeFragment;
import com.ajr.atmajayarental.screen.DriverProfileFragment;
import com.ajr.atmajayarental.screen.DriverRiwayatFragment;
import com.ajr.atmajayarental.screen.HomeFragment;
import com.ajr.atmajayarental.screen.LoginActivity;
import com.ajr.atmajayarental.screen.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DriverMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    private ImageButton btnLogout;
    private DriverPreferences driverPreferences;
    private Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        btnLogout = findViewById(R.id.btnLogout);

        driverPreferences = new DriverPreferences(getApplicationContext());
        driver = driverPreferences.getDriverLogin();

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar); // Yang membuat tidak bisa Dark mode

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottomNavigationView.getMenu();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                new AlertDialog.Builder(DriverMainActivity.this)
                        .setTitle("WARNING")
                        .setMessage("Apakah Anda Yakin ingin Logout?")

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                driverPreferences.logout();
                                Toast.makeText(DriverMainActivity.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
                                checkLogin();
                                alertDialog.dismiss();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        changeFragment(new DriverHomeFragment());
        changeToolbarTitle();
        toolbarTitle.setText("Home");
    }

    private void checkLogin() {
//        Fungsi ini akan mengecek jika user login,
//        akan memunculkan toast jika tidak redirect ke login activity
        if (!driverPreferences.checkLogin()) {
            startActivity(new Intent(DriverMainActivity.this, LoginActivity.class));
            finish();
        }
        else {
            Toast.makeText(DriverMainActivity.this.getApplicationContext(), "Login !!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method untuk ganti nama Title di toolbar
    private void changeToolbarTitle(){
        getSupportActionBar().setTitle("");
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.menuHome) {
                changeFragment(new DriverHomeFragment());
                changeToolbarTitle();
                toolbarTitle.setText("Home");
                btnLogout.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.menuProfile) {
                changeFragment(new DriverProfileFragment());
                changeToolbarTitle();
                toolbarTitle.setText("Profile Saya");
                btnLogout.setVisibility(View.VISIBLE);
            }
            else if(item.getItemId() == R.id.menuRiwayat){
                changeFragment(new DriverRiwayatFragment());
                changeToolbarTitle();
                toolbarTitle.setText("Riwayat Saya");
                btnLogout.setVisibility(View.GONE);
            }
            return true;
        }
    };

    // Method untuk Berganti Fragment
    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.layout_fragment, fragment)
                .commit();
    }
}