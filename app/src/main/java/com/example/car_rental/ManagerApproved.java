package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManagerApproved extends AppCompatActivity {
    private RequestQueue queue;
    private ListView list;

    private ArrayList<String> contractIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Inraheem 1","Ibraheem hello");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_approved);
        queue = Volley.newRequestQueue(this);
        Intent I=getIntent() ;
        Log.d("Inraheem 1","Ibraheem hello");
        list = findViewById(R.id.listView);
        Log.d("Inraheem 1","Ibraheem Hello New");
        contractIds = new ArrayList<>();
        list();
    }

    public void list() {
        Log.d("Inraheem 1","Ibraheem");

        String url = "http://10.0.2.2:80/CARRENTAL/approved.php";

        Log.d("volley_debug", "Request URL: " + url);

        Log.d("Ihraheem 2","Ibraheem");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley_debug", "Response: " + response.toString());
                        ArrayList<String> contractDetails = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String contractId = obj.getString("contractid");
                                String username = obj.getString("username");
                                int contractCount = i + 1;
                                contractIds.add(contractId);
                                contractDetails.add(contractCount + "- " + username + " wants to reserve a car: \n" + "Contract ID: " + contractId + "\n"); // Adjust this to your actual JSON key
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ManagerApproved.this,
                                    android.R.layout.simple_list_item_1, contractDetails);
                            list.setAdapter(adapter);
                        } catch (JSONException exception) {
                            Log.d("volley_error", "JSON parsing error: " + exception.toString());
                        }

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedContractId = contractIds.get(position);
                                Intent intent = new Intent(ManagerApproved.this, ManagerDetailsApprover.class);
                                intent.putExtra("contractId", selectedContractId);
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", "Volley error: " + error.toString());
            }
        });

        queue.add(request);
    }
}
