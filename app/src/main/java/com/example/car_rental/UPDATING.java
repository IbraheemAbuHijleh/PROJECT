package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UPDATING extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private Spinner spinner;
    private DatePicker cal;
    private RadioGroup radio;
    private Button btn;
    private RequestQueue q;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean CHECK = false;

    private String CARIDD = "";
    private String BRANDD = "";
    private String COLORD = "";
    private String MODELD = "";
    private String PRICED = "";
    private String SEATD = "";
    private String DATED = "";
    private String LOCATIOND = "";
    private String STATUSD = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating);

        Intent i = getIntent();

        HOKS();

        CARIDD = i.getStringExtra("CARID");
        BRANDD = i.getStringExtra("BRAND");
        COLORD = i.getStringExtra("COLOR");
        MODELD = i.getStringExtra("MODEL");
        PRICED = i.getStringExtra("PRICE");
        SEATD = i.getStringExtra("SEAT");
        STATUSD = i.getStringExtra("STATUS");
        DATED = i.getStringExtra("InsuranceExpiry");
        LOCATIOND = i.getStringExtra("chapterlocation");

        // Set data to UI components
        editText1.setText(CARIDD);
        editText2.setText(BRANDD);
        editText3.setText(COLORD);
        editText4.setText(MODELD);
        editText5.setText(PRICED);
        editText6.setText(LOCATIOND);

        setSpinnerSelection(spinner, SEATD);

        if ("Available".equals(STATUSD)) {
            ((RadioButton) findViewById(R.id.RV1)).setChecked(true);
        } else if ("Not Available".equals(STATUSD)) {
            ((RadioButton) findViewById(R.id.RNV)).setChecked(true);
        }

        if (DATED != null) {
            String[] dateParts = DATED.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1; // Month is 0-based in DatePicker
            int day = Integer.parseInt(dateParts[2]);
            cal.updateDate(year, month, day);
        }
        setupS();

        CHECK = checkdata();
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sippener, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (value != null) {
            int spinnerPosition = adapter.getPosition(value);
            spinner.setSelection(spinnerPosition);
        }
    }

    private boolean checkdata() {

        String CARID = editText1.getText().toString().trim();
        String BRAND = editText2.getText().toString().trim();
        String COLOR = editText3.getText().toString().trim();
        String MODEL = editText4.getText().toString().trim();
        String PRICE = editText5.getText().toString().trim();
        String SEATS = spinner.getSelectedItem().toString();
        int id = radio.getCheckedRadioButtonId();

        // Retrieving the date from DatePicker
        int day = cal.getDayOfMonth();
        int month = cal.getMonth() + 1; // Month is 0-based in DatePicker
        int year = cal.getYear();
        String date = year + "-" + month + "-" + day;

        if (CARID.isEmpty() || BRAND.isEmpty() || COLOR.isEmpty() || MODEL.isEmpty() || PRICE.isEmpty() || SEATS.isEmpty() || id == -1) {
            Toast.makeText(UPDATING.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
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
        editText2 = findViewById(R.id.edttext2);
        editText3 = findViewById(R.id.edttext3);
        editText4 = findViewById(R.id.edttext4);
        editText5 = findViewById(R.id.edttext5);
        editText6 = findViewById(R.id.edttext6);
        spinner = findViewById(R.id.sp);
        cal = findViewById(R.id.cv); // Ensure this ID is a DatePicker in your XML layout
        btn = findViewById(R.id.btn);
        radio = findViewById(R.id.radiogroub);
        q = Volley.newRequestQueue(this);
    }

    public void clickbait(View view) {

        if (!checkdata()) return; // Prevent further execution if data is invalid

        String URL = "http://10.0.2.2:80/CARRENTAL/UPDATE.php";

        int id = radio.getCheckedRadioButtonId();

        RadioButton rdo = findViewById(id);

        String STAT = rdo.getText().toString().trim();

        StringRequest R = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                try {
                    JSONObject o = new JSONObject(response);
                    if (o.has("message")) {
                        String message = o.getString("message");
                        Toast.makeText(UPDATING.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UPDATING.this, "Key 'message' not found in response", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UPDATING.this, "Response parsing error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error: " + error.toString());
                Toast.makeText(UPDATING.this, "Request error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("carid", editText1.getText().toString().trim().toLowerCase());

                params.put("brand", editText2.getText().toString().trim());

                params.put("color", editText3.getText().toString().trim());

                params.put("MODEL", editText4.getText().toString().trim());

                params.put("PRICE", editText5.getText().toString().trim());

                params.put("SEAT", spinner.getSelectedItem().toString());

                params.put("STATUS", STAT);
                int day = cal.getDayOfMonth();
                int month = cal.getMonth() + 1; // Month is 0-based in DatePicker
                int year = cal.getYear();
                String date = year + "-" + month + "-" + day;
                Log.d("DATA", date);
                params.put("DATA", date);
                params.put("chapterlocation", editText6.getText().toString().trim());

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
            editor.putString("BRAND", editText2.getText().toString().trim());
            editor.putString("COLOR", editText3.getText().toString().trim());
            editor.putString("MODEL", editText4.getText().toString().trim());
            editor.putString("PRICE", editText5.getText().toString().trim());
            editor.putString("SEAT", spinner.getSelectedItem().toString());

            int day = cal.getDayOfMonth();
            int month = cal.getMonth() + 1; // Month is 0-based in DatePicker
            int year = cal.getYear();
            String date = year + "-" + month + "-" + day;
            editor.putString("DATE", date);

            int id = radio.getCheckedRadioButtonId();
            RadioButton rdo = findViewById(id);
            String STAT = rdo.getText().toString().trim();
            editor.putString("ID", STAT);
            editor.putString("chapterlocation", editText6.getText().toString().trim());

            editor.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CARID", editText1.getText().toString().trim().toLowerCase());
        outState.putString("BRAND", editText2.getText().toString().trim());
        outState.putString("COLOR", editText3.getText().toString().trim());
        outState.putString("MODEL", editText4.getText().toString().trim());
        outState.putString("PRICE", editText5.getText().toString().trim());
        outState.putString("SEAT", spinner.getSelectedItem().toString());

        // Retrieving the date from DatePicker
        int day = cal.getDayOfMonth();
        int month = cal.getMonth() + 1; // Month is 0-based in DatePicker
        int year = cal.getYear();
        String date = year + "-" + month + "-" + day;
        outState.putString("DATE", date);

        int id = radio.getCheckedRadioButtonId();
        RadioButton rdo = findViewById(id);
        String STAT = rdo.getText().toString().trim();
        outState.putString("ID", STAT);
        outState.putString("chapterlocation", editText6.getText().toString().trim());
    }
}
