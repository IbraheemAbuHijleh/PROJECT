package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;
import android.widget.DatePicker;
public class Insertcar extends AppCompatActivity {
    private EditText editText1;
    private EditText getEditText2;
    private EditText getEditText3;
    private EditText getEditText4;
    private EditText getEditText5;
    private EditText getEditText6;
    private Spinner spinner;
    private DatePicker cal;
    private RadioGroup radio;
    private Button btn;
    private RequestQueue q;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean CHECK = false;

    private Button ntn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_insertcar);


        Intent intent = getIntent();

        HOKS();

        setupS();

        CHECK = checkdata();

    }

    private boolean checkdata() {

        String CARID = editText1.getText().toString().trim();

        String BRAND = getEditText2.getText().toString().trim();

        String COLOR = getEditText3.getText().toString().trim();

        String MODEL = getEditText4.getText().toString().trim();

        String PRICE = getEditText5.getText().toString().trim();

        String SEATS = spinner.getSelectedItem().toString();

        int id = radio.getCheckedRadioButtonId();

        // Retrieving the date from DatePicker

        int day = cal.getDayOfMonth();

        int month = cal.getMonth() + 1; // Month is 0-based in DatePicker

        int year = cal.getYear();

        String date = day + "/" + month + "/" + year;


        if (CARID.isEmpty() || BRAND.isEmpty() || COLOR.isEmpty() || MODEL.isEmpty() || PRICE.isEmpty() || SEATS.isEmpty() || id == -1) {

            Toast.makeText(Insertcar.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();

            return false;

        }

        return true;

    }

    private void setupS() {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        editor = preferences.edit();

    }

    private void HOKS() {

        editText1 = findViewById(R.id.edtt1);

        getEditText2 = findViewById(R.id.edttext2);

        getEditText3 = findViewById(R.id.edttext3);

        getEditText4 = findViewById(R.id.edttext4);

        getEditText5 = findViewById(R.id.edttext5);

        getEditText6 = findViewById(R.id.edttext6);

        spinner = findViewById(R.id.sp);

        cal = findViewById(R.id.cv); // Ensure this ID is a DatePicker in your XML layout

        btn = findViewById(R.id.btn);

        radio = findViewById(R.id.radiogroub);

        q = Volley.newRequestQueue(this);


    }

    public void clickbait(View view) {


        if (!checkdata()) return; // Prevent further execution if data is invalid

        String URL = "http://10.0.2.2:80/CARRENTAL/INSERT.php";

        int id = radio.getCheckedRadioButtonId();

        RadioButton rdo = findViewById(id);

        String STAT = rdo.getText().toString().trim();

        Log.d("TTTT",STAT);

        StringRequest R = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {

                Log.d("Response", response);

                try {

                    JSONObject o = new JSONObject(response);

                    if (o.has("message")) {

                        String message = o.getString("message");

                        Toast.makeText(Insertcar.this, message, Toast.LENGTH_SHORT).show();

                    }
                    else {

                        Toast.makeText(Insertcar.this, "Key 'message' not found in response", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                    Toast.makeText(Insertcar.this, "Response parsing error", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                Log.e("Volley", "Error: " + error.toString());

                Toast.makeText(Insertcar.this, "Request error", Toast.LENGTH_SHORT).show();

            }

        })
        {
            @Override

            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("carid", editText1.getText().toString().trim().toLowerCase());

                params.put("brand", getEditText2.getText().toString().trim());

                params.put("color", getEditText3.getText().toString().trim());

                params.put("MODEL", getEditText4.getText().toString().trim());

                params.put("PRICE", getEditText5.getText().toString().trim());

                params.put("SEAT", spinner.getSelectedItem().toString());

                params.put("STATUS", STAT);


                // Retrieving the date from DatePicker
                int day = cal.getDayOfMonth();

                int month = cal.getMonth() + 1;

                // Month is 0-based in DatePicker

                int year = cal.getYear();

                String date = year + "-" + month + "-" + day;

                Log.d("DATA",date);

                params.put("DATA", date);

                params.put("chapterlocation",getEditText6.getText().toString().trim());


                return params;
            }
        };

        q.add(R);
    }



    @Override
    protected void onStop() {

        super.onStop();

        if (CHECK) {

            editor.putString("CARID", editText1.getText().toString().trim().toLowerCase());

            editor.putString("BRAND", getEditText2.getText().toString().trim());

            editor.putString("COLOR", getEditText3.getText().toString().trim());

            editor.putString("MODEL", getEditText4.getText().toString().trim());

            editor.putString("PRICE", getEditText5.getText().toString().trim());

            editor.putString("SEAT", spinner.getSelectedItem().toString());


            int day = cal.getDayOfMonth();

            int month = cal.getMonth() + 1;

            int year = cal.getYear();

            String date = year + "-" + month + "-" + day;

            editor.putString("DATE", date);

            int id = radio.getCheckedRadioButtonId();

            RadioButton rdo = findViewById(id);

            String STAT = rdo.getText().toString().trim();

            editor.putString("ID", STAT);

            editor.putString("chapterlocation",getEditText6.getText().toString().trim());

            editor.commit();

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putString("CARID", editText1.getText().toString().trim().toLowerCase());

        outState.putString("BRAND", getEditText2.getText().toString().trim());

        outState.putString("COLOR", getEditText3.getText().toString().trim());

        outState.putString("MODEL", getEditText4.getText().toString().trim());

        outState.putString("PRICE", getEditText5.getText().toString().trim());

        outState.putString("SEAT", spinner.getSelectedItem().toString());


        // Retrieving the date from DatePicker
        int day = cal.getDayOfMonth();

        int month = cal.getMonth() + 1;

        int year = cal.getYear();

        String date = year + "-" + month + "-" + day;

        outState.putString("DATE", date);

        int id = radio.getCheckedRadioButtonId();

        RadioButton rdo = findViewById(id);

        String STAT = rdo.getText().toString().trim();

        outState.putString("ID", STAT);

        outState.putString("chapterlocation",getEditText6.getText().toString().trim());

    }
}
