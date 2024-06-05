package com.example.car_rental;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CarDetailsActivity extends AppCompatActivity {
    private TextView carIDTextView;
    private RequestQueue queue;
    private Button deleat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        carIDTextView = findViewById(R.id.carIDTextView);
        Button displayMessageButton = findViewById(R.id.delete);

        String id = getIntent().getStringExtra("carID");

        queue = Volley.newRequestQueue(this);

        getCarDetails(id);

        // Set onClick listener for the display message button
        displayMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMessage();
            }
        });
    }

    private void displayMessage() {
        Toast.makeText(this, "Button clicked! Displaying a message.", Toast.LENGTH_SHORT).show();
    }


    private void getCarDetails(String id) {
        String url = "http://10.0.2.2:80/CARRENTAL/searchbycarid.php?carid=" + id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley_debug", "Response: " + response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String carID = obj.getString("carid");
                                String brand = obj.getString("brand");
                                String color = obj.getString("color");
                                String model = obj.getString("MODEL");
                                String price = obj.getString("PRICE");
                                String status = obj.getString("STATUS");

                                // Construct the result string
                                String res = "Brand: " + brand + "\n \n" +
                                        "Color: " + color + "\n \n" +
                                        "Model: " + model + "\n \n" +
                                        "Price: " + price + "\n \n" +
                                        "Status: " + status;

                                // Update the TextView with car details
                                carIDTextView.setText(res);
                            }

                        } catch (JSONException exception) {
                            Log.d("volley_error", "JSON parsing error: " + exception.toString());
                            showToast("JSON parsing error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", "Volley error: " + error.toString());
                showToast("Error fetching data");
            }
        });

        // Add the request to the RequestQueue
        queue.add(request);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
