package com.example.car_rental.ui.gallery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_rental.R;
import com.example.car_rental.adapters.ContractAdapter;
import com.example.car_rental.adapters.HomeAdapter;
import com.example.car_rental.databinding.FragmentGalleryBinding;
import com.example.car_rental.model.Contract;
import com.example.car_rental.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {


    private RecyclerView recyclerViewForReservations;
    private ContractAdapter contractAdapter;
    private List<Contract> contractList;
    private RequestQueue queue;
    private SharedPreferences prefs;
    public String username;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewForReservations = view.findViewById(R.id.recyclerViewForReservations);
        prefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        username=prefs.getString("USERNAME","user");
        queue = Volley.newRequestQueue(getContext());
        String url = "http://10.0.2.2:80/rest/fetchreservations.php?username="+username;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                contractList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        contractList.add( new Contract(obj.getString("contractid"),obj.getString("username"),obj.getString("carid"),obj.getString("startdate"),obj.getString("enddate"),obj.getString("price"),obj.getString("paymentstatus"),obj.getString("visaid")));
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                contractAdapter=new ContractAdapter(
                        contractList
                );


                recyclerViewForReservations.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerViewForReservations.setAdapter(contractAdapter);

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
   return inflater.inflate(R.layout.fragment_gallery,container,false);
    }

    public GalleryFragment() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}