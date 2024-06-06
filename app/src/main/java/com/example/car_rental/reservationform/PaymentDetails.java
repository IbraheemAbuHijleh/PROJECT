package com.example.car_rental.reservationform;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_rental.MainActivityForUser;
import com.example.car_rental.R;
import com.example.car_rental.adapters.HomeAdapter;
import com.example.car_rental.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentDetails extends AppCompatActivity {

    private SharedPreferences prefs;
    private ImageView backArrow;
public static String username;
private TextView totalcostTV;
EditText visaIDinput;
    private RequestQueue queue;
    private RequestQueue queue2;
    float currentBalance;
    float carPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_detials);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        queue = Volley.newRequestQueue(this);
        queue2 = Volley.newRequestQueue(this);
        visaIDinput=findViewById(R.id.visaIDinput);
        totalcostTV=findViewById(R.id.totalcostTV);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        username=prefs.getString("USERNAME","user");
        backArrow=findViewById(R.id.backArrow);
        carPrice=Float.parseFloat(getIntent().getStringExtra("PRICE"));
        totalcostTV.setText("Total Price: "+carPrice);
        String[] startdateformat = getIntent().getStringExtra("STARTDATE").split("-");
        String[] enddateformat = getIntent().getStringExtra("ENDDATE").split("-");
        int total= Integer.parseInt(enddateformat[0])-Integer.parseInt(startdateformat[0]);
        total+=(Integer.parseInt(enddateformat[1])-Integer.parseInt(startdateformat[1]))*30;
        total+=(Integer.parseInt(enddateformat[2])-Integer.parseInt(startdateformat[2]))*365;
         carPrice*=total;
        totalcostTV.setText("Total Price: "+carPrice);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentDetails.this, MainActivityForUser.class);
                Toast.makeText(PaymentDetails.this,"Booking Process cancelled",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    public void confirmPaymentClick() {




            String url = "http://10.0.2.2:80/rest/makereservation.php";


            // Toast.makeText(PaymentDetails.this, "  response", Toast.LENGTH_SHORT).show();


            // Create a JsonObjectRequest with POST method
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.e("TAG", "Response is " + s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        Toast.makeText(PaymentDetails.this, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(PaymentDetails.this, "Fail to ger response =" + volleyError, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return super.getBodyContentType();
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("carid", getIntent().getStringExtra("CARID"));
                    String[] startdateformat = getIntent().getStringExtra("STARTDATE").split("-");
                    String[] enddateformat = getIntent().getStringExtra("ENDDATE").split("-");
                    String startDate = startdateformat[2] + "-" + startdateformat[1] + "-" + startdateformat[0];
                    String endDate = enddateformat[2] + "-" + enddateformat[1] + "-" + enddateformat[0];

                    params.put("startdate", startDate);


                    params.put("enddate", endDate);
                    params.put("price", String.valueOf(carPrice));
                    params.put("visaid", visaIDinput.getText().toString());
                    return params;
                }
            };

            queue.add(request);


            Intent intent = new Intent(this, MainActivityForUser.class);
            startActivity(intent);

    }

        public void ValidatePayment (View view) {



            String url = "http://10.0.2.2:80/rest/fetchbalance.php?username=" + username+"&visaid="+visaIDinput.getText().toString();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        currentBalance = Float.parseFloat(jsonObject.getString("balance"));
                        if (carPrice <= currentBalance) {
                            confirmPaymentClick();
                        } else {

                            Dialog dialog=new Dialog(PaymentDetails.this);
                            dialog.setContentView(R.layout.not_enough_balance_activity);

                            dialog.show();

                        }
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(PaymentDetails.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            queue.add(request);


        }


    }
