package com.example.car_rental;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignIn extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private EditText emailEditText;
    private EditText passwordEditText;
    private AppCompatButton signInButton;
    private RequestQueue queue;
    public String username;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in...");
        progressDialog.setCancelable(false);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    signIn();
                }
                else if(!email.isEmpty() && password.isEmpty()){
                    Toast.makeText(SignIn.this, "Please enter your email!", Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty() && !password.isEmpty()){
                    Toast.makeText(SignIn.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(SignIn.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("EMAIL", emailEditText.getText().toString());
        outState.putString("PASSWORD", passwordEditText.getText().toString());
    }

    private void signIn() {
        progressDialog.show(); // Show the progress dialog
        queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/rest/signin.php?username=" + emailEditText.getText().toString() + "&password=" + passwordEditText.getText().toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
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
                progressDialog.dismiss();
                Toast.makeText(SignIn.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);
    }

    private void setupSharedPrefs(String email) {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putString("USERNAME", email);
        editor.putBoolean("LOGGED_IN", true);
        editor.apply();
        Intent intent = new Intent(this, MainActivityForUser.class);
        startActivity(intent);
    }
    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignOut.class);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss(); // Dismiss the progress dialog if it's showing
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        emailEditText.setText(savedInstanceState.getString("EMAIL", ""));
        passwordEditText.setText(savedInstanceState.getString("PASSWORD", ""));
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean loggedIn = prefs.getBoolean("LOGGED_IN", false);
        if (loggedIn) {
            Intent intent = new Intent(this, MainActivityForUser.class);
            startActivity(intent);
            finish();
        }
    }
}