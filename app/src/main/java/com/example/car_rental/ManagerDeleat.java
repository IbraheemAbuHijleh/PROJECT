package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ManagerDeleat extends AppCompatActivity {
    private ListView list;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_deleat);

        list = findViewById(R.id.listView);
        queue = Volley.newRequestQueue(this);
        getLis("STATUS", "Available", "CARRENTAL/searchbybooked.php");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car selectedCar = (Car) parent.getItemAtPosition(position);
                Intent intent = new Intent(ManagerDeleat.this, CarDeleteAdapter.class);
                intent.putExtra("carID", selectedCar.getCarID());
                startActivity(intent);
            }
        });
    }

    public void getLis(String type, String searchText, String phpname) {
        String url = "http://10.0.2.2:80/" + phpname + "?" + type + "=" + searchText;
        Log.d("volley_debug", "Request URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley_debug", "Response: " + response.toString());
                        ArrayList<Car> cars = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String carID = obj.getString("carid");
                                String brand = obj.getString("brand");
                                String color = obj.getString("color");
                                String model = obj.getString("MODEL");
                                String price = obj.getString("PRICE");
                                String status = obj.getString("STATUS");

                                Car car = new Car(carID, brand, color, model, price, status, ""); // Adjust as per your constructor
                                cars.add(car);
                            }
                            CarDeleteAdapter adapter = new CarDeleteAdapter(ManagerDeleat.this, cars);
                            list.setAdapter(adapter);
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

    public void deleteCar(String carID) {
        String url = "http://10.0.2.2:80/CARRENTAL/deletecar.php?carid=" + carID;
        Log.d("volley_debug", "Delete Request URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley_debug", "Delete Response: " + response.toString());
                        showToast("Car deleted successfully");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", "Volley delete error: " + error.toString());
                showToast("Car deleted successfully");
            }
        });

        queue.add(request);
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
