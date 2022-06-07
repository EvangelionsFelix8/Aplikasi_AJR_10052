package com.ajr.atmajayarental.screen;

import static com.android.volley.Request.Method.PUT;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.api.CustomerApi;
import com.ajr.atmajayarental.api.DriverApi;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.CustomerResponse;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.DriverResponse;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ProfileFragment extends Fragment {

    private TextView textNama, textEmail, textNomorTelepon, textTanggalLahir, textJenisKelamin, textAlamat;
    private ImageButton btnEdit, btnPassword;
    private Button btnSavePassword;
    private TextInputLayout textPasswordLama, textPassword_1, textPassword_2;
    private TextInputEditText textPassLama, textConfirm;
    private CustomerPreferences customerPreferences;
    private Customer customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textNama = view.findViewById(R.id.textNama);
        textEmail = view.findViewById(R.id.textEmail);
        textNomorTelepon = view.findViewById(R.id.textNomorTelepon);
        textTanggalLahir = view.findViewById(R.id.textTanggalLahir);
        textJenisKelamin = view.findViewById(R.id.textJenisKelamin);
        textAlamat = view.findViewById(R.id.textAlamat);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnPassword = view.findViewById(R.id.btnPassword);

        customerPreferences = new CustomerPreferences(getContext());
        customer = customerPreferences.getCustomerLogin();

        showCustomerData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "HELLO", Toast.LENGTH_SHORT).show();
            }
        });

        btnPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View viewPassword = layoutInflater.inflate(R.layout.layout_ubah_password, null);

                final AlertDialog alertDialog = new AlertDialog.Builder(viewPassword.getContext()).create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btnSavePassword = viewPassword.findViewById(R.id.btnGanti);
                textConfirm = viewPassword.findViewById(R.id.passwordTIETKedua);
//                textPasswordLama = viewPassword.findViewById(R.id.passwordLama);
//                textPassLama = viewPassword.findViewById(R.id.passwordTIETLama);
                textPassword_1 = viewPassword.findViewById(R.id.inputanPasswordBaru);
                textPassword_2 = viewPassword.findViewById(R.id.passwordKedua);

                textConfirm.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        textPassword_2.setError(validPassword());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                btnSavePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pass1, pass2;
                        pass1 = textPassword_1.getEditText().getText().toString().trim();
                        pass2 = textPassword_2.getEditText().getText().toString().trim();
                        if(!pass1.equalsIgnoreCase(pass2)){
                            Toast.makeText(getContext(), "Inputan Password Berbeda", Toast.LENGTH_SHORT).show();
                        }
                        else if(pass1.isEmpty() || pass2.isEmpty()){
                            Toast.makeText(getContext(), "Silakan Menginputkan Password Baru", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            showAlertDialog("Apakah Yakin Ingin Mengganti Password?", alertDialog, pass1);
                        }
                    }
                });
                alertDialog.setView(viewPassword);
                alertDialog.show();
                return false;
            }
        });
    }

    private CharSequence validPassword() {
        String password1 = textPassword_1.getEditText().getText().toString().trim();
        String password2 = textPassword_2.getEditText().getText().toString().trim();

        if(!password1.equalsIgnoreCase(password2)){
            return "Password doesn't matched";
        }

        return null;
    }

    public void showAlertDialog(String message, AlertDialog alertDialog, String inputanPassword){
        new AlertDialog.Builder(getContext())
                .setTitle("WARNING")
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        updatePassword(inputanPassword);
                        alertDialog.dismiss();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void showCustomerData(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                CustomerApi.GET_CUSTOMER_DATA + customer.getId_customer(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                CustomerResponse customerResponse = gson.fromJson(response, CustomerResponse.class);
                Customer foundedCustomerData = customerResponse.getCustomer();
                textNama.setText(foundedCustomerData.getNama_customer());
                textEmail.setText(foundedCustomerData.getEmail_customer());
                textNomorTelepon.setText(foundedCustomerData.getNo_telp_customer());
                textTanggalLahir.setText(foundedCustomerData.getTanggal_lahir_customer());
                textJenisKelamin.setText(foundedCustomerData.getJenis_kelamin());
                textAlamat.setText(foundedCustomerData.getAlamat_customer());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+ customerPreferences.getCustomerLogin().getAccess_token());
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void updatePassword(String password){

        Customer updatedCustomer = new Customer(
                password
        );

        StringRequest stringRequest = new StringRequest(PUT,
                CustomerApi.UPDATE_PASSWORD + customer.getId_customer(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                CustomerResponse customerResponse = gson.fromJson(response, CustomerResponse.class);
                Toast.makeText(getContext(), customerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                customerPreferences.setPasswordLogin(
                        password
                );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+ customerPreferences.getCustomerLogin().getAccess_token());
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(updatedCustomer);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}