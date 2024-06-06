package com.example.car_rental.reservationform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_rental.MainActivityForUser;
import com.example.car_rental.R;
import com.example.car_rental.adapters.HomeAdapter;
import com.example.car_rental.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservationDetials extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView availableFromDate;
    private ImageView backArrow;
private ImageView reservationimage;
    private TextView totalprice;
    private TextView reservationinfo;
    String Date;
    String startDate="";
    String endDate="";
    private RequestQueue queue;
    String carPrice;
    private TextView enddate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_detials);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        totalprice=findViewById(R.id.totalprice);
        reservationinfo=findViewById(R.id.reservationinfo);
        reservationimage=findViewById(R.id.reservationimage);


        queue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:80/rest/fetchcarbyid.php?carid="+getIntent().getStringExtra("CARID");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    reservationinfo.setText(jsonObject.getString("brand")+"/"+jsonObject.getString("MODEL")+" "+jsonObject.getString("chapterlocation"));

                   carPrice=jsonObject.getString("PRICE");
                    totalprice.setText("Total price: "+carPrice);
                    reservationimage.setImageResource(getCarImageResource(jsonObject.getString("brand")));
                    Toast.makeText(ReservationDetials.this, " current",
                            Toast.LENGTH_SHORT).show();
                } catch(JSONException exception){
                    Log.d("Error", exception.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ReservationDetials.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);

        calendarView=findViewById(R.id.calendarView);
        availableFromDate=findViewById(R.id.availableFromDate);
        backArrow=findViewById(R.id.backArrow);
        enddate=findViewById(R.id.enddate);
        calendarView
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                     CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {

                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                 Date
                                        = dayOfMonth + "-"
                                        + (month + 1) + "-" + year;
                                if (startDate.isEmpty()){
                                    availableFromDate.setText("select your start date "+Date);
                                    startDate=Date;

                                }
                            else if (endDate.isEmpty()) {
                                    endDate = Date;
                                    enddate.setText("select your end date: " + endDate);
                                }

                                // set this date in TextView for Display
                                else {

                                    startDate = Date;
                                    endDate = "";
                                    availableFromDate.setText("selct your start date: " + startDate);
                                    enddate.setText("select your end date: ");
                                }


                            }
                        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReservationDetials.this, MainActivityForUser.class);

                startActivity(intent);
            }
        });
    }

    public void choosePaymentClick(View view) {
        Intent intent=new Intent(ReservationDetials.this,PaymentDetails.class);
        intent.putExtra("PRICE",carPrice);
        intent.putExtra("CARID",getIntent().getStringExtra("CARID"));
        intent.putExtra("STARTDATE",startDate);
        intent.putExtra("ENDDATE",endDate);
        startActivity(intent);
    }

    private int getCarImageResource(String carName) {
        // Convert car name to lowercase and remove spaces
        String resourceName = carName.toLowerCase().replace(" ", "");
        // Get resource ID dynamically
        int resId = this.getResources().getIdentifier(resourceName, "drawable", this.getPackageName());
        // Return the resource ID if found, otherwise return a default image resource ID
        return resId != 0 ? resId : R.drawable.mer;
    }
}