package com.ajr.atmajayarental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.ajr.atmajayarental.preferences.DriverPreferences;
import com.ajr.atmajayarental.preferences.PegawaiPreferences;
import com.ajr.atmajayarental.screen.HomeFragment;
import com.ajr.atmajayarental.screen.LoginActivity;
import com.ajr.atmajayarental.screen.MobilFragment;
import com.ajr.atmajayarental.screen.ProfileFragment;
import com.ajr.atmajayarental.screen.PromoFragment;
import com.ajr.atmajayarental.screen.RiwayatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String COMMON_TAG = "";
//public class MainActivity extends AppCompatActivity  {

    private Button btnPromo;
    private DrawerLayout drawer;
    Toolbar toolbar;
    TextView toolbarTitle;
    private CustomerPreferences customerPreferences;
    private Customer customer;
    private TextView textNamaHeader, textEmailHeader;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerPreferences = new CustomerPreferences(getApplicationContext());
        customer = customerPreferences.getCustomerLogin();

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar); // Yang membuat tidak bisa Dark mode

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get findView ID yang di Header nya Navigation View
        View header = navigationView.getHeaderView(0);
        textNamaHeader = header.findViewById(R.id.namaCustHeader);
        textEmailHeader = header.findViewById(R.id.emailCustHeader);

        textNamaHeader.setText(customer.getNama_customer());
        textEmailHeader.setText(customer.getEmail_customer());

        // BOTTOM NAVIGATION
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        changeFragment(new HomeFragment());
        changeToolbarTitle();
        toolbarTitle.setText("Home");
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_promo:
                changeFragment(new PromoFragment());
                changeToolbarTitle();
                toolbarTitle.setText("Promo Yang Tersedia");
                break;
            case R.id.nav_mobil:
                changeFragment(new MobilFragment());
                changeToolbarTitle();
                toolbarTitle.setText("Mobil Yang Tersedia");
                break;
            case R.id.nav_riwayat_trans:
                changeFragment(new RiwayatFragment());
                changeToolbarTitle();
                toolbarTitle.setText("Riwayat Transaksi Anda");
                break;
            case R.id.nav_logout:
                customerPreferences.logout();
                Toast.makeText(MainActivity.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
                checkLogin();
//                Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(i);
//                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                changeFragment(new HomeFragment());
                changeToolbarTitle();
                toolbarTitle.setText("Home");
            } else if (item.getItemId() == R.id.menuProfile) {
                changeFragment(new ProfileFragment());
                changeToolbarTitle();
                toolbarTitle.setText("Profile Saya");
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i(COMMON_TAG, "Landscape");
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.i(COMMON_TAG, "Potrait");
        }
    }

    private void checkLogin() {
//        Fungsi ini akan mengecek jika user login,
//        akan memunculkan toast jika tidak redirect ke login activity
        if (!customerPreferences.checkLogin()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else {
            Toast.makeText(MainActivity.this.getApplicationContext(), "Login !!", Toast.LENGTH_SHORT).show();
        }
    }
}