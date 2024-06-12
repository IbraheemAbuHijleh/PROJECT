package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// Result Activity
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Result extends AppCompatActivity {

    private RequestQueue RE;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        list = findViewById(R.id.list);
        RE = Volley.newRequestQueue(this);

        GETDATA();
    }

    private void GETDATA() {
        String URL = "http://10.0.2.2:80/CARRENTAL/ALLCARINC.php";

        JsonArrayRequest R = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null && response.length() > 0) {
                            ArrayList<Car> list1 = new ArrayList<>();
                            list1.clear();
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject o = response.getJSONObject(i);
                                    list1.add(new Car(
                                            o.getString("carid"),
                                            o.getString("brand"),
                                            o.getString("color"),
                                            o.getString("MODEL"),
                                            o.getString("PRICE"),
                                            o.getString("SEAT"),
                                            o.getString("STATUS"),
                                            o.getString("DATA"),
                                            o.getString("chapterlocation")));
                                }
                                ArrayAdapter<Car> adapter = new ArrayAdapter<>(Result.this, android.R.layout.simple_list_item_1, list1);
                                list.setAdapter(adapter);
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Car car = (Car) parent.getItemAtPosition(position);
                                        Intent intent = new Intent(Result.this, UPDATING.class);
                                        intent.putExtra("CARID", car.getCarID().toLowerCase());
                                        intent.putExtra("BRAND", car.getBrand().toLowerCase());
                                        intent.putExtra("COLOR", car.getColor().toLowerCase());
                                        intent.putExtra("MODEL", car.getModel().toLowerCase());
                                        intent.putExtra("PRICE", car.getPrice().toLowerCase());
                                        intent.putExtra("SEAT", car.getStatusNumber().toLowerCase());
                                        intent.putExtra("STATUS", car.getStatus().toLowerCase());
                                        intent.putExtra("InsuranceExpiry", car.getInsurance_expires().toLowerCase());
                                        intent.putExtra("chapterlocation", car.getChapterlocation().toLowerCase());
                                        startActivity(intent);
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(Result.this, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + error.toString());
                    }
                }
        );

        RE.add(R);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // تحديث البيانات من SharedPreferences وعرضها في ListView
        updateListView();
    }

    private void updateListView() {
        SharedPreferences sharedPreferences = getSharedPreferences("CarData", MODE_PRIVATE);
        String carId = sharedPreferences.getString("CARID", "");
        String brand = sharedPreferences.getString("BRAND", "");
        String color = sharedPreferences.getString("COLOR", "");
        String model = sharedPreferences.getString("MODEL", "");
        String price = sharedPreferences.getString("PRICE", "");
        String seat = sharedPreferences.getString("SEAT", "");
        String date = sharedPreferences.getString("DATE", "");
        String location = sharedPreferences.getString("LOCATION", "");
        String status = sharedPreferences.getString("STATUS", "");

        if (!carId.isEmpty() && !brand.isEmpty() && !color.isEmpty() && !model.isEmpty() &&
                !price.isEmpty() && !seat.isEmpty() && !date.isEmpty() && !location.isEmpty() && !status.isEmpty()) {
            ArrayList<Car> list1 = new ArrayList<>();
            list1.add(new Car(carId, brand, color, model, price, seat, status, date, location));

            ArrayAdapter<Car> adapter = new ArrayAdapter<>(Result.this, android.R.layout.simple_list_item_1, list1);
            list.setAdapter(adapter);
        } else {
            GETDATA(); // If no updated data in SharedPreferences, fetch data from the server
        }
    }
}
