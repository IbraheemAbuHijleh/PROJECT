package com.example.car_rental;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_rental.R;
import com.example.car_rental.WishListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CarDetailsActiv extends AppCompatActivity {

    private TextView carNameTextView;
    private LinearLayout carDetailsLayout;
    private ImageButton imgFavView;
    private RequestQueue queue;
    private boolean isFavorite = false;
    private ArrayList<String> wishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_special_offer);

        carNameTextView = findViewById(R.id.textView_name);
        carDetailsLayout = findViewById(R.id.carDetailsLayout);
        imgFavView = findViewById(R.id.imgFavView);
        Button reserveButton = findViewById(R.id.button_reserve);

        String carId = getIntent().getStringExtra("carID");

        queue = Volley.newRequestQueue(this);

        wishList = new ArrayList<>();

        getCarDetails(carId);

        imgFavView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMessage();
            }
        });
    }

    private void toggleFavorite() {
        isFavorite = !isFavorite;
        if (isFavorite) {
            imgFavView.setImageResource(R.drawable.ic_favorite_red_24dp);
            addToWishList(carNameTextView.getText().toString());
            showToast("Added to favorites");
        } else {
            imgFavView.setImageResource(R.drawable.ic_favorite_shadow_24dp);
            removeFromWishList(carNameTextView.getText().toString());
            showToast("Removed from favorites");
        }
    }

    private void removeFromWishList(String string) {
        String carName = carNameTextView.getText().toString();
        if (wishList.contains(carName)) {
            wishList.remove(carName);
            Toast.makeText(this, "Removed from Wish List", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewWishList(View view) {
        Intent intent = new Intent(this, WishListActivity.class);
        startActivity(intent);
    }

    private void displayMessage() {
        Toast.makeText(this, "Car reserved!", Toast.LENGTH_SHORT).show();
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
                                String brand = obj.getString("brand");
                                String color = obj.getString("color");
                                String model = obj.getString("MODEL");
                                String price = obj.getString("PRICE");
                                String status = obj.getString("STATUS");

                                carNameTextView.setText(brand);

                                addCarDetail("Brand", brand);
                                addCarDetail("Color", color);
                                addCarDetail("Model", model);
                                addCarDetail("Price", price);
                                addCarDetail("Status", status);
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

        queue.add(request);
    }

    private void addCarDetail(String label, String value) {
        TextView detailTextView = new TextView(this);
        detailTextView.setText(label + ": " + value);
        detailTextView.setTextSize(16);
        detailTextView.setTextColor(getResources().getColor(R.color.black));
        carDetailsLayout.addView(detailTextView);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void addToWishList(String carName) {
        if (!wishList.contains(carName)) {
            wishList.add(carName);
        }
    }


}