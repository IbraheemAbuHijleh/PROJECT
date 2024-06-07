package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class Result extends AppCompatActivity {



    private RequestQueue RE;
 private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        list=findViewById(R.id.list);

        RE = Volley.newRequestQueue(this);
        String URL = "http://10.0.2.2:80/CARRENTAL/ALLCARINC.php";

        JsonArrayRequest R = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null && response.length() > 0) {
                            ArrayList<Car> list1 = new ArrayList<>();
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
                                            o.getString("chapterlocation")
                                    ));
                                }
                               // Car[] arr = list1.toArray(new Car[0]);
                                ArrayAdapter<Car> adapter = new ArrayAdapter<>(Result.this, android.R.layout.simple_list_item_1, list1);
                                list.setAdapter(adapter);
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
}





