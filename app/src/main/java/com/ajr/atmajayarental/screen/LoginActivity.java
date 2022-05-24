package com.ajr.atmajayarental.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ajr.atmajayarental.DriverMainActivity;
import com.ajr.atmajayarental.MainActivity;
import com.ajr.atmajayarental.ManagerMainActivity;
import com.ajr.atmajayarental.R;
import com.ajr.atmajayarental.VolleySingleton;
import com.ajr.atmajayarental.api.LoginApi;
import com.ajr.atmajayarental.models.Customer;
import com.ajr.atmajayarental.models.CustomerResponse;
import com.ajr.atmajayarental.models.Driver;
import com.ajr.atmajayarental.models.DriverResponse;
import com.ajr.atmajayarental.models.Pegawai;
import com.ajr.atmajayarental.models.PegawaiResponse;
import com.ajr.atmajayarental.models.User;
import com.ajr.atmajayarental.preferences.CustomerPreferences;
import com.ajr.atmajayarental.preferences.DriverPreferences;
import com.ajr.atmajayarental.preferences.PegawaiPreferences;
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
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    private TextInputLayout textEmail, textPassword;
    private TextInputEditText TIETemail, TIETpassword;
    private CustomerPreferences customerPreferences;
    private PegawaiPreferences pegawaiPreferences;
    private DriverPreferences driverPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        customerPreferences = new CustomerPreferences(getApplicationContext());
        pegawaiPreferences = new PegawaiPreferences(getApplicationContext());
        driverPreferences = new DriverPreferences(getApplicationContext());

        textEmail = findViewById(R.id.inputLoginEmail);
        textPassword = findViewById(R.id.inputLoginPassword);
        TIETemail = findViewById(R.id.emailTIET);
        TIETpassword = findViewById(R.id.passwordTIET);
        btnLogin = findViewById(R.id.btnLogin);

        checkLogin();

        TIETemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) textEmail.setError(validEmail());
            }
        });

        TIETpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) textPassword.setError(validPassword());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textEmail.getEditText().getText().toString().trim();
                String password = textPassword.getEditText().getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    if(email.isEmpty()){
                        textEmail.setError("Email Cannot be Empty");
                        textEmail.requestFocus();
                    }
                    if (password.isEmpty()){
                        textPassword.setError("Password Cannot be Empty");
                        textEmail.requestFocus();
                    }
                }
                else{
                    login();
                }
            }
        });
    }

    private CharSequence validPassword() {
        String password = textPassword.getEditText().getText().toString().trim();

        if(password.isEmpty()){
            return "Password Cannot be Empty";
        }

        return null;
    }

    private CharSequence validEmail() {
        String email = textEmail.getEditText().getText().toString().trim();
        if(email.isEmpty()){
            return "Email Cannot be Empty";
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "Invalid Email";
        }

        return null;
    }

    public void login(){
        String email = textEmail.getEditText().getText().toString().trim();
        String password = textPassword.getEditText().getText().toString().trim();

//        Customer customer = new Customer(email, password);
        User user = new User(email, password);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginApi.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                CustomerResponse customerResponse = gson.fromJson(response, CustomerResponse.class);
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);
                PegawaiResponse pegawaiResponse = gson.fromJson(response, PegawaiResponse.class);

                if(customerResponse.getKode() == 1){
                    Toast.makeText(LoginActivity.this, customerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Customer tempCustomer = customerResponse.getCustomer();

                    customerPreferences.setLogin(
                            customerResponse.getToken(),
                            tempCustomer.getId_customer(),
                            tempCustomer.getNama_customer(),
                            tempCustomer.getAlamat_customer(),
                            tempCustomer.getTanggal_lahir_customer(),
                            tempCustomer.getJenis_kelamin(),
                            tempCustomer.getEmail_customer(),
                            tempCustomer.getPassword(),
                            tempCustomer.getNo_telp_customer(),
                            tempCustomer.getStatus_berkas(),
                            tempCustomer.getNo_tanda_pengenal(),
                            tempCustomer.getNo_sim()
                    );

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(driverResponse.getKode() == 2){
                    Toast.makeText(LoginActivity.this, driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Driver tempDriver = driverResponse.getDriver();

                    driverPreferences.setLogin(
                            driverResponse.getToken(),
                            tempDriver.getId_driver(),
                            tempDriver.getNama_driver(),
                            tempDriver.getAlamat_driver(),
                            tempDriver.getEmail_driver(),
                            tempDriver.getStatus_ketersediaan_driver(),
                            tempDriver.getStatus_berkas(),
                            tempDriver.isEnglish(),
                            tempDriver.getTanggal_lahir_driver(),
                            tempDriver.getJenis_kelamin(),
                            tempDriver.getNo_telp_driver(),
                            tempDriver.getUrl_foto_driver(),
                            tempDriver.getPassword(),
                            tempDriver.getTarif_sewa_driver(),
                            tempDriver.getBerkas_bebas_napza(),
                            tempDriver.getBerkas_sim(),
                            tempDriver.getBerkas_sehat_jiwa(),
                            tempDriver.getBerkas_sehat_jasmani(),
                            tempDriver.getBerkas_skck(),
                            tempDriver.getRerata_rating_driver(),
                            tempDriver.isAktif()
                    );

                    Intent intent = new Intent(LoginActivity.this, DriverMainActivity.class);
                    startActivity(intent);
                    finish();
                }

                if(pegawaiResponse.getKode() == 3 && (pegawaiResponse.getPegawai().getId_role() == 2 || pegawaiResponse.getPegawai().getId_role() == 3)){
                    Toast.makeText(LoginActivity.this, "Admin/CS tidak mendapatkan akses", Toast.LENGTH_SHORT).show();
                }
                else if(pegawaiResponse.getKode() == 3){
                    Toast.makeText(LoginActivity.this, pegawaiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Pegawai tempPegawai = pegawaiResponse.getPegawai();

                    pegawaiPreferences.setLogin(
                            pegawaiResponse.getToken(),
                            tempPegawai.getId_pegawai(),
                            tempPegawai.getId_role(),
                            tempPegawai.getNama_pegawai(),
                            tempPegawai.getAlamat_pegawai(),
                            tempPegawai.getEmail_pegawai(),
                            tempPegawai.getTanggal_lahir_pegawai(),
                            tempPegawai.getJenis_kelamin_pegawai(),
                            tempPegawai.getNo_telp_pegawai(),
                            tempPegawai.getpassword(),
                            tempPegawai.getUrl_foto_pegawai(),
                            tempPegawai.isAktif()
                    );
                    Intent intent = new Intent(LoginActivity.this, ManagerMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(LoginActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(user);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void checkLogin(){
        if(customerPreferences.checkLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        else if(pegawaiPreferences.checkLogin()){
            startActivity(new Intent(LoginActivity.this, ManagerMainActivity.class));
            finish();
        }
        else if(driverPreferences.checkLogin()){
            startActivity(new Intent(LoginActivity.this, DriverMainActivity.class));
            finish();
        }
    }
}