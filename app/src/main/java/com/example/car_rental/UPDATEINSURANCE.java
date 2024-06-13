package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UPDATEINSURANCE extends AppCompatActivity{
        private EditText editText1, editText2, editText3, editText4, editText5, editText6;
        private Spinner spinner;
        private DatePicker cal;
        private RadioGroup radio;
        private Button btn;
        private RequestQueue q;

        private SharedPreferences preferences;
        private SharedPreferences.Editor editor;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_updating);

            Intent i = getIntent();
            HOKS();

            // Set data from intent to UI components
            editText1.setText(i.getStringExtra("CARID"));
            editText2.setText(i.getStringExtra("BRAND"));
            editText3.setText(i.getStringExtra("COLOR"));
            editText4.setText(i.getStringExtra("MODEL"));
            editText5.setText(i.getStringExtra("PRICE"));
            editText6.setText(i.getStringExtra("chapterlocation"));
            setSpinnerSelection(spinner, i.getStringExtra("SEAT"));

            String status = i.getStringExtra("STATUS");
            if ("available".equals(status)) {
                ((RadioButton) findViewById(R.id.RV1)).setChecked(true);
            } else if ("not available".equals(status)) {
                ((RadioButton) findViewById(R.id.RNV)).setChecked(true);
            }

            String date = i.getStringExtra("InsuranceExpiry");
            if (date != null) {
                String[] dateParts = date.split("-");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]) - 1;
                int day = Integer.parseInt(dateParts[2]);
                cal.updateDate(year, month, day);
            }

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkdata()) {
                        updateCarData();
                    }
                }
            });
        }

        private void HOKS() {
            editText1 = findViewById(R.id.edtt1);
            editText2 = findViewById(R.id.edttext2);
            editText3 = findViewById(R.id.edttext3);
            editText4 = findViewById(R.id.edttext4);
            editText5 = findViewById(R.id.edttext5);
            editText6 = findViewById(R.id.edttext6);
            spinner = findViewById(R.id.sp);
            cal = findViewById(R.id.cv);
            btn = findViewById(R.id.btn);
            radio = findViewById(R.id.radiogroub);
            q = Volley.newRequestQueue(this);
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
            String CARID = editText1.getText().toString().trim().toLowerCase();
            String BRAND = editText2.getText().toString().trim().toLowerCase();
            String COLOR = editText3.getText().toString().trim().toLowerCase();
            String MODEL = editText4.getText().toString().trim().toLowerCase();
            String PRICE = editText5.getText().toString().trim().toLowerCase();
            String SEATS = spinner.getSelectedItem().toString().toLowerCase();
            int id = radio.getCheckedRadioButtonId();

            // Retrieving the date from DatePicker
            int day = cal.getDayOfMonth();
            int month = cal.getMonth() + 1; // Month is 0-based in DatePicker
            int year = cal.getYear();
            String date = year + "-" + month + "-" + day;

            if (CARID.isEmpty() || BRAND.isEmpty() || COLOR.isEmpty() || MODEL.isEmpty() || PRICE.isEmpty() || SEATS.isEmpty() || id == -1) {
                Toast.makeText(UPDATEINSURANCE.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }

        private void updateCarData() {
            String URL = "http://10.0.2.2:80/CARRENTAL/UPDATE.php";

            StringRequest R = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response", response);
                    try {
                        JSONObject o = new JSONObject(response);
                        if (o.has("message")) {
                            String message = o.getString("message");
                            Toast.makeText(UPDATEINSURANCE.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UPDATEINSURANCE.this, "Key 'message' not found in response", Toast.LENGTH_SHORT).show();
                        }
                       saveData();
                        Intent intent =new Intent(UPDATEINSURANCE.this, Queries.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(UPDATEINSURANCE.this, "Response parsing error", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null) {
                        Log.e("Volley", "Error: " + new String(error.networkResponse.data));
                    } else {
                        Log.e("Volley", "Error: " + error.toString());
                    }
                    Toast.makeText(UPDATEINSURANCE.this, "Request error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("carid", editText1.getText().toString().trim().toLowerCase());
                    params.put("brand", editText2.getText().toString().trim().toLowerCase());
                    params.put("color", editText3.getText().toString().trim().toLowerCase());
                    params.put("MODEL", editText4.getText().toString().trim().toLowerCase());
                    params.put("PRICE", editText5.getText().toString().trim().toLowerCase());
                    params.put("SEAT", spinner.getSelectedItem().toString().toLowerCase());

                    int day = cal.getDayOfMonth();
                    int month = cal.getMonth() + 1; // Month is 0-based in DatePicker
                    int year = cal.getYear();
                    String date = year + "-" + month + "-" + day;
                    params.put("DATA", date.toLowerCase());

                    int id = radio.getCheckedRadioButtonId();
                    RadioButton rdo = findViewById(id);
                    String STAT = rdo.getText().toString().trim();
                    params.put("STATUS", STAT.toLowerCase());
                    params.put("chapterlocation", editText6.getText().toString().trim().toLowerCase());

                    return params;
                }
            };

            q.add(R);


        }

        private void saveData() {
            SharedPreferences sharedPreferences = getSharedPreferences("CarData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("CARID", editText1.getText().toString().trim().toLowerCase());
            editor.putString("BRAND", editText2.getText().toString().trim().toLowerCase());
            editor.putString("COLOR", editText3.getText().toString().trim().toLowerCase());
            editor.putString("MODEL", editText4.getText().toString().trim().toLowerCase());
            editor.putString("PRICE", editText5.getText().toString().trim().toLowerCase());
            editor.putString("SEAT", spinner.getSelectedItem().toString().toLowerCase());

            int day = cal.getDayOfMonth();
            int month = cal.getMonth() + 1; // Month is 0-based in DatePicker
            int year = cal.getYear();
            String date = year + "-" + month + "-" + day;
            editor.putString("DATE", date.toLowerCase());

            int id = radio.getCheckedRadioButtonId();
            RadioButton rdo = findViewById(id);
            editor.putString("STATUS", rdo.getText().toString().trim().toLowerCase());
            editor.putString("LOCATION", editText6.getText().toString().trim().toLowerCase());

            editor.commit();
        }

        public void onStop(){
            super.onStop();
            finish();
        }
}


