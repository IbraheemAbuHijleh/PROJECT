package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UPDATE extends AppCompatActivity {

    private EditText edttext;
    private ListView list;
    private RequestQueue RQ;
    private boolean CHECK = false;
    private Button btncli;
    private String CARID = null;
    private String BRAND=null;
    private String COLOR =null ;
    private String MODEL=null;
    private String PRICE=null;
    private String SEAT=null;
    private String DATE=null;
    private String LOCATION=null;
    private String STATUS=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edttext = findViewById(R.id.edttextt);

        list = findViewById(R.id.list);

        RQ = Volley.newRequestQueue(this);

        btncli = findViewById(R.id.btncli);

        CHECK = CheckData();

        if (!CHECK) {
            btncli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    list.setVisibility(View.VISIBLE);

                    String CARIDD = edttext.getText().toString();

                    String URL = "http://10.0.2.2:80/CARRENTAL/GetCarId.php?carid=" + CARIDD;

                    JsonArrayRequest R = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {

                            if (response != null && response.length() > 0) {

                                ArrayList<Car> list1 = new ArrayList<>();

                                try {

                                    for (int i = 0; i < response.length(); i++) {

                                        JSONObject o = response.getJSONObject(i);

                                        list1.add(new Car(o.getString("carid"), o.getString("brand"), o.getString("color"), o.getString("MODEL"), o.getString("PRICE"), o.getString("SEAT"), o.getString("STATUS"), o.getString("DATA"), o.getString("chapterlocation")));
                                        CARID=o.getString("carid");
                                        BRAND=o.getString("brand");
                                        COLOR=o.getString("color");
                                        MODEL=o.getString("MODEL");
                                        PRICE=o.getString("PRICE");
                                        SEAT=o.getString("SEAT");
                                        STATUS=o.getString("STATUS");
                                        DATE=o.getString("DATA");
                                        LOCATION=o.getString("chapterlocation");
                                    }

                                    Car[] arr = list1.toArray(new Car[0]);

                                    ArrayAdapter<Car> adapter = new ArrayAdapter<>(UPDATE.this, android.R.layout.simple_list_item_1, arr);

                                    list.setAdapter(adapter);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                Intent intent = new Intent(UPDATE.this, UPDATING.class);
                                                intent.putExtra("CARID",CARID); ;
                                                intent.putExtra("BRAND",BRAND);
                                                intent.putExtra("COLOR",COLOR);
                                                intent.putExtra("MODEL",MODEL);
                                                intent.putExtra("PRICE", PRICE);
                                                intent.putExtra("SEAT", SEAT);
                                                intent.putExtra("STATUS", STATUS);
                                                intent.putExtra("InsuranceExpiry", DATE);
                                                intent.putExtra("chapterlocation", LOCATION);
                                                startActivity(intent);

                                        }
                                    });

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                    Toast.makeText(UPDATE.this, "Error parsing response", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(UPDATE.this, "No data found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley", "Error: " + error.toString());
                                }
                            });

                    RQ.add(R);
                }
            });
        }
    }

    private boolean CheckData() {

        String check = edttext.getText().toString().trim();

        if (check.isEmpty()) {

            Toast.makeText(UPDATE.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();

            return false;

        } else {
            return true;
        }
    }
}