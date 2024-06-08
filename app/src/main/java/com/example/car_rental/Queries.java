package com.example.car_rental;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class Queries extends AppCompatActivity {

    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
         Intent intent=getIntent();
        txt1 = findViewById(R.id.avcar);
        txt2 = findViewById(R.id.nvc);
        txt3 = findViewById(R.id.Profit);
        txt4 = findViewById(R.id.NIC);

        // Initialize the RequestQueue
        queue = Volley.newRequestQueue(this);

        // Define URLs
        String URL_AVAILABLE_CARS = "http://10.0.2.2:80/CARRENTAL/NumberCarAvailable.php";
        String URL_NOT_AVAILABLE_CARS = "http://10.0.2.2:80/CARRENTAL/NotAvailbale.php";
        String URL_PROFIT = "http://10.0.2.2:80/CARRENTAL/Profet.php";
        String URL_PROFIT_I="http://10.0.2.2:80/CARRENTAL/NumberOfCarsEndDateIns.php";

        // Request for available cars
       makeRequest(URL_AVAILABLE_CARS,1);

        // Request for not available cars
       makeRequest(URL_NOT_AVAILABLE_CARS,2);

        // Request for profit
        makeRequestpROFIT(URL_PROFIT);

        makeRequest(URL_PROFIT_I,4);

    }

    private void makeRequestpROFIT(String urlProfit) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlProfit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            JSONObject o = new JSONObject(response);

                            // Check if the response contains "total_profit"
                            if (o.has("total_profit")) {
                                float totalProfit = (float) o.getDouble("total_profit");
                                Log.d("Float", String.valueOf(totalProfit));
                                txt3.setText(String.valueOf(totalProfit));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSONError", "Response parsing error");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            Log.e("Volley", "Error: " + new String(error.networkResponse.data));
                        } else {
                            Log.e("Volley", "Error: " + error.toString());
                        }
                        Toast.makeText(getApplicationContext(), "Request error", Toast.LENGTH_SHORT).show();
                    }
                });


        queue.add(stringRequest);
    }

    private void makeRequest(String url,int i) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            JSONObject o = new JSONObject(response);

                            if (o.has("message")) {
                                String message = o.getString("message");
                                Log.d("String",message);


                                if(i==1){
                                    txt1.setText(message);
                                } else if (i==2) {

                                    txt2.setText(message);

                                }
                                if(i==4){

                                    txt4.setText(message);
                                }

                            } else {
                                Toast.makeText(Queries.this, "Key 'message' not found in response", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Queries.this, "Response parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + error.toString());
                        Toast.makeText(Queries.this, "Request error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        queue.add(stringRequest);


    }
    public void calculateProfit(View v){
        Intent I=new Intent(Queries.this,Result.class);
        startActivity(I);

    }
}
