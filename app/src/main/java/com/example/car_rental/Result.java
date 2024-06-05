package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Result extends AppCompatActivity {

    private TextView txtRes;

    private RequestQueue RE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        txtRes=findViewById(R.id.txtres);

        RE= Volley.newRequestQueue(this);


        String URL = "http://10.0.2.2:80/CARRENTAL/NumberCarAvailable.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            JSONObject o = new JSONObject(response);

                            if (o.has("message")) {
                                String message = o.getString("message");
                                Toast.makeText(Result.this, message, Toast.LENGTH_SHORT).show();
                                txtRes.setText(message);
                            } else {
                                Toast.makeText(Result.this, "Key 'message' not found in response", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Result.this, "Response parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + error.toString());
                        Toast.makeText(Result.this, "Request error", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RE.add(stringRequest);
    }
}



