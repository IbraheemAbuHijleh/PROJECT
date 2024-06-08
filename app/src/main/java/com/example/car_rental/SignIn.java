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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in...");
        progressDialog.setCancelable(false);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("isSigningIn")) {
                progressDialog.show();
            }
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(SignIn.this, "User name is required!", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SignIn.this, "Password is required!", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(SignIn.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    signIn();
                }
            }
        });

    }

    private void signIn() {
        queue = Volley.newRequestQueue(this);
        progressDialog.show();
        String url = "http://10.0.2.2:80/rest/signin.php?username=" + emailEditText.getText().toString().toLowerCase() + "&password=" + passwordEditText.getText().toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                try {
                    username = jsonObject.getString("username");
                    if (username.equals("admin")) {
                        Log.d("Ibraheem_Admin", "Admin");
                        setupSharedPrefsForAdmin();
                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        setupSharedPrefs(username);
                    }

                } catch (JSONException exception) {
                    Log.d("Error", exception.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SignIn.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);
    }

    private void setupSharedPrefs(String email) {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putString("USERNAME", email);

        editor.putBoolean("TOKEN", false);
        editor.commit();
        Intent intent = new Intent(this, MainActivityForUser.class);
        startActivity(intent);
    }

    private void setupSharedPrefsForAdmin() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putString("ADMIN", "admin");

        editor.putBoolean("TOKEN", true);
        editor.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", emailEditText.getText().toString());
        outState.putString("password", passwordEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String savedEmail = savedInstanceState.getString("email");
        String savedPassword = savedInstanceState.getString("password");
        if (savedEmail != null) {
            emailEditText.setText(savedEmail);
        }
        if (savedPassword != null) {
            passwordEditText.setText(savedPassword);
        }
    }
    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignOut.class);
        startActivity(intent);
    }
}
