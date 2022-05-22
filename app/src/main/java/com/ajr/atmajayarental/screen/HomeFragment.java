package com.ajr.atmajayarental.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.api.CustomerApi;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.CustomerResponse;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private TextView textNamaHome;
    private CustomerPreferences customerPreferences;
    private Customer customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add( new SlideModel(R.drawable.carou1, ScaleTypes.CENTER_CROP));
        slideModels.add( new SlideModel(R.drawable.carou2, ScaleTypes.CENTER_CROP));
        slideModels.add( new SlideModel(R.drawable.carou3, ScaleTypes.CENTER_CROP));
        slideModels.add( new SlideModel(R.drawable.carou4, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textNamaHome = view.findViewById(R.id.textNamaHome);

        customerPreferences = new CustomerPreferences(getContext());
        customer = customerPreferences.getCustomerLogin();

        textNamaHome.setText("Selamat Datang, " + customer.getNama_customer());

    }
}