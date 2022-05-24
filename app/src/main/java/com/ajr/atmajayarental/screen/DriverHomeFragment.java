package com.ajr.atmajayarental.screen;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.api.DriverApi;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.ajr.atmajayarental.preferences.DriverPreferences;
import com.bumptech.glide.Glide;

public class DriverHomeFragment extends Fragment {

    private TextView textNamaHome;
    private ImageView fotoDriver;
    private DriverPreferences driverPreferences;
    private Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textNamaHome = view.findViewById(R.id.textNamaHomeD);
        fotoDriver = view.findViewById(R.id.fotoDriverHome);

        driverPreferences = new DriverPreferences(getContext());
        driver = driverPreferences.getDriverLogin();

        textNamaHome.setText(driver.getNama_driver());
        Glide.with(getContext())
                .load(DriverApi.BASE_URL_FOTO + driver.getUrl_foto_driver())
                .placeholder(R.drawable.no_image)
                .into(fotoDriver);
    }
}