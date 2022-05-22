package com.ajr.atmajayarental.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.ajr.atmajayarental.preferences.DriverPreferences;

public class DriverHomeFragment extends Fragment {

    private TextView textNamaHome;
    private DriverPreferences driverPreferences;
    private Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textNamaHome = view.findViewById(R.id.textNamaHomeD);

        driverPreferences = new DriverPreferences(getContext());
        driver = driverPreferences.getDriverLogin();

        textNamaHome.setText("Selamat Datang, " + driver.getNama_driver());
    }
}