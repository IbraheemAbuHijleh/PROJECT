package com.example.car_rental.ui.callus;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_rental.R;
import android.app.ProgressDialog;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class CallUsFragment extends Fragment {

    private ImageView phoneImageView;
    private ImageView emailImageView;
    private ImageView instaImageView;
    private ImageView teleImageView;
    private ImageView fbookImageView;
    private ImageView whatsImageView;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private static final String PREFERENCES_FILE = "com.example.myapp.PREFERENCES_FILE";
    private ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_us, container, false);

        sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE, getActivity().MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(getActivity());
        phoneImageView = view.findViewById(R.id.phoneImageView);
        emailImageView = view.findViewById(R.id.emailImageView);
        instaImageView = view.findViewById(R.id.instaImageView);
        teleImageView = view.findViewById(R.id.teleImageView);
        fbookImageView = view.findViewById(R.id.fbookImageView);
        whatsImageView = view.findViewById(R.id.whatsImageView);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching contacts details...");
        progressDialog.setCancelable(false);

        phoneImageView.setOnClickListener(new View.OnClickListener() {// get the campany phone number
            @Override
            public void onClick(View v) {

                makeCall();
            }
        });

        emailImageView.setOnClickListener(new View.OnClickListener() {// get the campany email
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        instaImageView.setOnClickListener(new View.OnClickListener() {// get the campany instegram account
            @Override
            public void onClick(View v) {
                openInsta();
            }
        });

        teleImageView.setOnClickListener(new View.OnClickListener() {// get the campany telegram account
            @Override
            public void onClick(View v) {
                openTele();
            }
        });

        fbookImageView.setOnClickListener(new View.OnClickListener() {// get the campany facebook account
            @Override
            public void onClick(View v) {
                openFb();
            }
        });

        whatsImageView.setOnClickListener(new View.OnClickListener() {// get the campany whats account
            @Override
            public void onClick(View v) {
                openWhapp();
            }
        });

        ftchdetails();

        return view;
    }

    private void makeCall() {// method to make call
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+970597908705"));
        startActivity(intent);
    }

    private void sendEmail() {// method to send wmail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:yunanawahdah2003@gmail.com"));
        startActivity(intent);
    }

    private void openInsta() {// method to oper the insta
        Uri uri = Uri.parse("http://instagram.com/_u/myapp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");
        try {
            startActivity(intent);
        } catch (Exception e) {
           Intent intnt = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/myapp"));

            startActivity(intnt);
        }
    }

    private void openTele() {// method to open telegram
        Uri uri = Uri.parse("https://telegram.org/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void openFb() {// method to open face book
        Uri uri = Uri.parse("http://facebook.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/")));
        }
    }

    private void openWhapp() {// method to open whatsup
        Uri uri = Uri.parse("https://wa.me/0597908705");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void ftchdetails() {
        String url = "https://api.myapp.com/contact-details";

        JsonObjectRequest obj = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String phone = response.getString("phone");
                            String email = response.getString("email");
                            String instagram = response.getString("instagram");
                            String telegram = response.getString("telegram");
                            String facebook = response.getString("facebook");
                            String whatsapp = response.getString("whatsapp");

                            SharedPreferences.Editor editor = sharedPreferences.edit();// save it using shared prefrences
                            editor.putString("phone", phone);
                            editor.putString("email", email);
                            editor.putString("instagram", instagram);
                            editor.putString("telegram", telegram);
                            editor.putString("facebook", facebook);
                            editor.putString("whatsapp", whatsapp);
                            editor.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                  //      Toast.makeText(getActivity(), "Error while fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(obj);
    }
    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()==true) {
            progressDialog.dismiss();
        }
    }
}
