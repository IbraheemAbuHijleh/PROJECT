package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertcar);

        HOKS();
        setupS();

        CHECK = checkdata();

        setupVisibilityLogic();
    }

    private boolean checkdata() {
        String CARID = editText1.getText().toString().trim().toLowerCase();
        String BRAND = getEditText2.getText().toString().trim().toLowerCase();
        String COLOR = getEditText3.getText().toString().trim().toLowerCase();
        String MODEL = getEditText4.getText().toString().trim().toLowerCase();
        String PRICE = getEditText5.getText().toString().trim().toLowerCase();
        String SEATS = spinner.getSelectedItem().toString().toLowerCase();
        int id = radio.getCheckedRadioButtonId();

        int day = cal.getDayOfMonth();
        int month = cal.getMonth() + 1;
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
        cal = findViewById(R.id.cv);
        btn = findViewById(R.id.btn);
        radio = findViewById(R.id.radiogroub);
        q = Volley.newRequestQueue(this);

        getEditText2.setVisibility(View.GONE);
        getEditText3.setVisibility(View.GONE);
        getEditText4.setVisibility(View.GONE);
        getEditText5.setVisibility(View.GONE);
        getEditText6.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        cal.setVisibility(View.GONE);
        radio.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
    }

    private void setupVisibilityLogic() {
        setupEditTextWatcher(editText1, getEditText2);
        setupEditTextWatcher(getEditText2, getEditText3);
        setupEditTextWatcher(getEditText3, getEditText4);
        setupEditTextWatcher(getEditText4, getEditText5);
        setupEditTextWatcher(getEditText5, getEditText6);
        setupEditTextWatcher(getEditText6, spinner);
        setupSpinnerWatcher(spinner, cal);
        setupDatePickerWatcher(cal, radio);
        setupRadioGroupWatcher(radio, btn);
    }

    private void setupEditTextWatcher(EditText currentEditText, final View nextView) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    nextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupSpinnerWatcher(Spinner currentSpinner, final View nextView) {
        currentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    nextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupDatePickerWatcher(DatePicker currentDatePicker, final View nextView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    nextView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setupRadioGroupWatcher(RadioGroup currentRadioGroup, final View nextView) {
        currentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nextView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void clickbait(View view) {
        if (!checkdata()) return;

        String URL = "http://10.0.2.2:80/CARRENTAL/INSERT.php";
        int id = radio.getCheckedRadioButtonId();
        RadioButton rdo = findViewById(id);
        String STAT = rdo.getText().toString().trim().toLowerCase();

        StringRequest R = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                try {
                    JSONObject o = new JSONObject(response);
                    if (o.has("message")) {
                        String message = o.getString("message");
                        Toast.makeText(Insertcar.this, message, Toast.LENGTH_SHORT).show();
                    } else {
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
                Toast.makeText(Insertcar.this, "Request error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("carid", editText1.getText().toString().trim().toLowerCase());
                params.put("brand", getEditText2.getText().toString().trim().toLowerCase());
                params.put("color", getEditText3.getText().toString().trim().toLowerCase());
                params.put("MODEL", getEditText4.getText().toString().trim().toLowerCase());
                params.put("PRICE", getEditText5.getText().toString().trim().toLowerCase());
                params.put("SEAT", spinner.getSelectedItem().toString().toLowerCase());
                params.put("STATUS", STAT.toLowerCase());

                int day = cal.getDayOfMonth();
                int month = cal.getMonth() + 1;
                int year = cal.getYear();
                String date = year + "-" + month + "-" + day;
                params.put("DATA", date.toLowerCase());

                params.put("chapterlocation", getEditText6.getText().toString().trim().toLowerCase());

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
            editor.putString("BRAND", getEditText2.getText().toString().trim().toLowerCase());
            editor.putString("COLOR", getEditText3.getText().toString().trim().toLowerCase());
            editor.putString("MODEL", getEditText4.getText().toString().trim().toLowerCase());
            editor.putString("PRICE", getEditText5.getText().toString().trim().toLowerCase());
            editor.putString("SEAT", spinner.getSelectedItem().toString().toLowerCase());

            int day = cal.getDayOfMonth();
            int month = cal.getMonth() + 1;
            int year = cal.getYear();
            String date = year + "-" + month + "-" + day;
            editor.putString("DATE", date.toLowerCase());

            int id = radio.getCheckedRadioButtonId();
            RadioButton rdo = findViewById(id);
            String STAT = rdo.getText().toString().trim().toLowerCase();
            editor.putString("ID", STAT.toLowerCase());

            editor.putString("chapterlocation", getEditText6.getText().toString().trim().toLowerCase());
            editor.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CARID", editText1.getText().toString().trim().toLowerCase());
        outState.putString("BRAND", getEditText2.getText().toString().trim().toLowerCase());
        outState.putString("COLOR", getEditText3.getText().toString().trim().toLowerCase());
        outState.putString("MODEL", getEditText4.getText().toString().trim().toLowerCase());
        outState.putString("PRICE", getEditText5.getText().toString().trim().toLowerCase());
        outState.putString("SEAT", spinner.getSelectedItem().toString().toLowerCase());

        int day = cal.getDayOfMonth();
        int month = cal.getMonth() + 1;
        int year = cal.getYear();
        String date = year + "-" + month + "-" + day;
        outState.putString("DATE", date.toLowerCase());

        int id = radio.getCheckedRadioButtonId();
        RadioButton rdo = findViewById(id);
        String STAT = rdo.getText().toString().trim().toLowerCase();
        outState.putString("ID", STAT.toLowerCase());

        outState.putString("chapterlocation", getEditText6.getText().toString().trim().toLowerCase());
    }
}
