package com.example.car_rental;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_rental.reservationform.PaymentDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignOut extends AppCompatActivity {

    EditText firstNameEditText, licenseEditText, phoneEditText, passwordEditText, confirmPasswordEditText;
    Button signUpButton;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);

        firstNameEditText = findViewById(R.id.editText_registerFirstName);
        licenseEditText = findViewById(R.id.editText_license);
        phoneEditText = findViewById(R.id.editText_editPhoneNumber);
        passwordEditText = findViewById(R.id.editText_registerPassword);
        confirmPasswordEditText = findViewById(R.id.editText_registerConfirmPassword);
        signUpButton = findViewById(R.id.button_registerSignUp);

        queue = Volley.newRequestQueue(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String license = licenseEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (!firstName.isEmpty() && !license.isEmpty() && !phone.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
                    signUp(firstName, license, phone, password);
                } else {
                    Toast.makeText(SignOut.this, "Please fill in all the fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("firstName", firstNameEditText.getText().toString());
        outState.putString("license", licenseEditText.getText().toString());
        outState.putString("phone", phoneEditText.getText().toString());
        outState.putString("password", passwordEditText.getText().toString());
        outState.putString("confirmPassword", confirmPasswordEditText.getText().toString());
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firstNameEditText.setText(savedInstanceState.getString("firstName"));
        licenseEditText.setText(savedInstanceState.getString("license"));
        phoneEditText.setText(savedInstanceState.getString("phone"));
        passwordEditText.setText(savedInstanceState.getString("password"));
        confirmPasswordEditText.setText(savedInstanceState.getString("confirmPassword"));
    }
    private void signUp(String firstName, String license, String phone, String password) {
        String url = "http://10.0.2.2:80/rest/signup.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("TAG", "Response is " + s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            Toast.makeText(SignOut.this, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(SignOut.this,SignIn.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SignOut.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", firstName);
                params.put("license", license);
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    public void goToSignIn(View view){
        Intent intent =new Intent(SignOut.this,SignIn.class);
        startActivity(intent);
    }

}
