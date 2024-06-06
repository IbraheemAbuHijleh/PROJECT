package com.example.car_rental;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_rental.adapters.HomeAdapter;
import com.example.car_rental.model.Item;
import com.example.car_rental.reservationform.PaymentDetails;


public class SignIn extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    EditText emailEditText;
    EditText passwordEditText;
    AppCompatButton signInButton;
    private ProgressDialog progressDialog;
    private RequestQueue queue;
    public String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        progressDialog = new ProgressDialog(this);// progress dialog
        progressDialog.setMessage("Signing in occuring ...");
        progressDialog.setCancelable(false);

        signInButton.setOnClickListener(new View.OnClickListener() {// set on action when click login
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                   signIn();
                } else {
                    Toast.makeText(SignIn.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signIn() {
        if (emailEditText.getText().toString().trim().equals("admin")) {
             Intent intent=new Intent(SignIn.this,MainActivity.class);
             startActivity(intent);
        } else {
            queue = Volley.newRequestQueue(this);
            String url = "http://10.0.2.2:80/rest/signin.php?username=" + emailEditText.getText().toString() + "&password=" + passwordEditText.getText().toString();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        username = jsonObject.getString("username");

                            setupSharedPrefs(username);

                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(SignIn.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            queue.add(request);

        }
    }

    private void setupSharedPrefs(String email) {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putString("USERNAME",
                email);

        editor.putBoolean("TOKEN", false);
        editor.commit();
        Intent intent = new Intent(this,MainActivityForUser.class);
        startActivity(intent);
    }
    private void setupSharedPrefsForAdmin() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putString("ADMIN",
                "admin");

        editor.putBoolean("TOKEN", true);
        editor.commit();
       // Intent intent = new Intent(this,MainActivityForUser.class);
       // startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()==true) {
            progressDialog.dismiss();
        }
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this,SignOut.class);
        startActivity(intent);
    }
}

