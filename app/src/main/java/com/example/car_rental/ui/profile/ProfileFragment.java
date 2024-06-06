package com.example.car_rental.ui.profile;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_rental.R;
import com.example.car_rental.SignIn;
import com.example.car_rental.adapters.HomeAdapter;
import com.example.car_rental.model.Item;
import com.example.car_rental.reservationform.PaymentDetails;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    TextView full_name;
    TextView payment_lbl;
    TextView booking_lbl;
    TextView license;
    TextInputEditText editText_phone;
    private SharedPreferences prefs;
    public String username;
public  float currentBalance;
    private RequestQueue queue;
    TextView editText_password;
    Button btn;
    TextView signout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        super.onCreate(savedInstanceState);
        btn=getView().findViewById(R.id.btn);
      
       
        editText_password=getView().findViewById(R.id.editText_password);
        full_name=getView().findViewById(R.id.full_name);
        payment_lbl=getView().findViewById(R.id.payment_lbl);
        booking_lbl=getView().findViewById(R.id.booking_lbl);
        editText_phone=getView().findViewById(R.id.editText_phone);
        signout=getView().findViewById(R.id.signout);
        license=getView().findViewById(R.id.license);
        prefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        username=prefs.getString("USERNAME","user");
        Toast.makeText(getContext(),"Hello "+username,Toast.LENGTH_SHORT).show();
        fetchProfile();
        fetchBalance();
        fetchReservations();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePhone();
                update();
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignIn.class);
                startActivity(intent);
            }
        });


    }

    private void updatePhone() {
        String url = "http://10.0.2.2:80/rest/updatephone.php?username="+username+"&newphone="+editText_phone.getText().toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("message")) {
                                String message = response.getString("message");
                                Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                            } else if (response.has("error")) {
                                String error = response.getString("error");
                                booking_lbl.setText(error);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        // Add the request to the RequestQueue

        queue.add(request);
    }

    private void fetchProfile() {
        queue = Volley.newRequestQueue(getContext());
        String url = "http://10.0.2.2:80/rest/fetchprofile.php?username=" + username;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    full_name.setText(jsonObject.getString("username"));
                    editText_phone.setText(jsonObject.getString("phone"));
                    license.setText(jsonObject.getString("license"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);
    }
    private void fetchBalance() {
        String url = "http://10.0.2.2:80/rest/fetchbalanceprofile.php?username=" + username;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    currentBalance = Float.parseFloat(jsonObject.getString("balance"));
                    payment_lbl.setText(jsonObject.getString("balance"));
                } catch (JSONException exception) {
                    Log.d("Error", exception.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);
    }
    public void fetchReservations(){
        String url = "http://10.0.2.2:80/rest/fetchreservations.php?username="+username;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                booking_lbl.setText(String.valueOf(response.length()));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void update( ) {

        String url = "http://10.0.2.2:80/rest/updateuserpassword.php?username="+username+"&newpassword="+editText_password.getText().toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("message")) {
                                String message = response.getString("message");
                                Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                            } else if (response.has("error")) {
                                String error = response.getString("error");
                                booking_lbl.setText(error);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        // Add the request to the RequestQueue

        queue.add(request);

    }



}