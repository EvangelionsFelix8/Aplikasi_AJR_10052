package com.ajr.atmajayarental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.ajr.atmajayarental.screen.HomeFragment;
import com.ajr.atmajayarental.screen.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DriverMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar); // Yang membuat tidak bisa Dark mode

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
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
}