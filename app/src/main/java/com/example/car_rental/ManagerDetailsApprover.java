package com.example.car_rental;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;public class ManagerDetailsApprover extends AppCompatActivity {
    private TextView contractIdView, paymentStatusView, visaIdView;
    private TextView usernameView, daynumber, carIdView, startDateView, endDateView, priceView;
    private RequestQueue queue;
    private String start, end;
    private Button approvedButton ,disapprovedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_details_approver);
        Intent I=getIntent();
        contractIdView = findViewById(R.id.contractIdView);
        usernameView = findViewById(R.id.usernameView);
        carIdView = findViewById(R.id.carIdView);
        startDateView = findViewById(R.id.startDateView);
        endDateView = findViewById(R.id.endDateView);
        priceView = findViewById(R.id.priceView);
        paymentStatusView = findViewById(R.id.paymentStatusView);
        visaIdView = findViewById(R.id.visaIdView);
        daynumber = findViewById(R.id.daynumber); // Initialize daynumber TextView
        disapprovedButton=findViewById(R.id.disapprovedButton);
        queue = Volley.newRequestQueue(this);

        String contractId = getIntent().getStringExtra("contractId");
        contractIdView.setText("contractId \n" + contractId);
        getData(contractId);
        approvedButton = findViewById(R.id.approvedButton);

        approvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(contractId);
                Toast.makeText(ManagerDetailsApprover.this, "you approved to rent a car ", Toast.LENGTH_SHORT).show();
            }
        });

        disapprovedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleatcontract(contractId);
                Toast.makeText(ManagerDetailsApprover.this, "deleted contract ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getData(String id) {
        String url = "http://10.0.2.2:80/CARRENTAL/approved.php?id=" + id;
        Log.d("volley_debug", "Request URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("volley_debug", "Response: " + response.toString());
                        try {
                            if (response.length() > 0) {
                                JSONObject obj = response.getJSONObject(0);
                                String username = obj.getString("username");
                                String carid = obj.getString("carid");
                                String startdate = obj.getString("startdate");
                                String enddate = obj.getString("enddate");
                                String price = obj.getString("price");
                                String status = obj.getString("paymentstatus");
                                String visa = obj.getString("visaid");
                                updatcarstatus(carid);
                                usernameView.setText("Username \n"+username);
                                carIdView.setText("Car Id \n"+carid);
                                startDateView.setText("Start date \n"+startdate);
                                endDateView.setText("end date \n"+enddate);
                                priceView.setText("Price \n"+price);
                                paymentStatusView.setText("Status \n"+status);
                                visaIdView.setText("Visa \n"+visa);

                                start = startdate;
                                end = enddate;
                                int res = customDateCalculation(start, end);

                                daynumber.setText("number of day to reserve it \n"+String.valueOf(res)); // Convert int to String

                            }
                        } catch (JSONException exception) {
                            Log.d("volley_error", "JSON parsing error: " + exception.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", "Volley error: " + error.toString());
            }
        });

        queue.add(request);
    }

    public static int customDateCalculation(String startDateStr, String endDateStr) {
        // Custom calculation to find days between two dates
        String[] startDateParts = startDateStr.split("-");
        String[] endDateParts = endDateStr.split("-");

        int startYear = Integer.parseInt(startDateParts[0]);
        int startMonth = Integer.parseInt(startDateParts[1]);
        int startDay = Integer.parseInt(startDateParts[2]);

        int endYear = Integer.parseInt(endDateParts[0]);
        int endMonth = Integer.parseInt(endDateParts[1]);
        int endDay = Integer.parseInt(endDateParts[2]);

        int days = 0;
        while (startYear < endYear || startMonth < endMonth || startDay < endDay) {
            startDay++;
            days++;
            if (startDay > daysInMonth(startYear, startMonth)) {
                startDay = 1;
                startMonth++;
                if (startMonth > 12) {
                    startMonth = 1;
                    startYear++;
                }
            }
        }
        return days;
    }

    public static int daysInMonth(int year, int month) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 0;
        }
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    private void changeStatus(String id) {
        String url = "http://10.0.2.2:80/CARRENTAL/changeapprovedstatus.php?contractid=" + id;
        Log.d("volley_debug", "Request URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject obj = response.getJSONObject(0);
                            String success = obj.getString("success");
                            Toast.makeText(ManagerDetailsApprover.this, success, Toast.LENGTH_SHORT).show();
                        } catch (JSONException exception) {
                            Log.d("volley_error", "JSON parsing error: " + exception.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", "Volley error: " + error.toString());
            }
        });

        queue.add(request);
    }
    private void deleatcontract(String id) {
        String url = "http://10.0.2.2:80/CARRENTAL/deleatcontract.php?contractid=" + id;
        Log.d("volley_debug", "Request URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject obj = response.getJSONObject(0);
                            String success = obj.getString("deleted contract");
                            Toast.makeText(ManagerDetailsApprover.this, success, Toast.LENGTH_SHORT).show();
                        } catch (JSONException exception) {
                            Log.d("volley_error", "JSON parsing error: " + exception.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", "Volley error: " + error.toString());
            }
        });

        queue.add(request);
    }
    private void updatcarstatus(String id) {
        String url = "http://10.0.2.2:80/CARRENTAL/carstatusapproved.php?carid=" + id;
        Log.d("volley_debug", "Request URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject obj = response.getJSONObject(0);

                        } catch (JSONException exception) {
                            Log.d("volley_error", "JSON parsing error: " + exception.toString());
                        }
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
