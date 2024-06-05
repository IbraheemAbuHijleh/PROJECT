package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import android.view.View;

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

public class ManagerSearch extends AppCompatActivity {
    private Button search_button;
    private Spinner spinner_option;
    private RequestQueue queue;
    private EditText search_query;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_search);
        search_button = findViewById(R.id.searchButton);
        spinner_option = findViewById(R.id.spinner_search);
        search_query = findViewById(R.id.search);
        list = findViewById(R.id.listView);
        queue = Volley.newRequestQueue(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getSearchOptions());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_option.setAdapter(adapter);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = spinner_option.getSelectedItem().toString();
                String searchText = search_query.getText().toString();

                if (selectedItem.equals("Search by car ID")) {
                    getLis("carid", searchText, "CARRENTAL/searchbycarid.php");
                }

                if (selectedItem.equals("Search by car type")) {

                    getLis("MODEL", searchText,"CARRENTAL/search.php");

                }

                else if (selectedItem.equals("Search by booked car")) {

                    getLis("STATUS", "not available","CARRENTAL/searchbybooked.php");

                }

                else if (selectedItem.equals("Search by available car")) {

                    getLis("STATUS", "available","CARRENTAL/searchbybooked.php");

                }

                else if (selectedItem.equals("Search for specific customer by name")) {
                    getLis("username", searchText,"CARRENTAL/usernamesearch.php");


                } else if (selectedItem.equals("Search for car in a specific branch")) {

                    getLis("chapterlocation", searchText,"CARRENTAL/locationsearch.php");
                }
            }
        });
    }

    private String[] getSearchOptions() {
        return new String[]{
                "select one of the following to search",
                "Search by car ID",
                "Search by car type",
                "Search by booked car",
                "Search by available car",
                "Search for car in a specific branch"
        };
    }

    private void showToast(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void getLis(String type, String searchText , String phpname) {
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
                            CarAdapter adapter = new CarAdapter(ManagerSearch.this, cars);
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
}