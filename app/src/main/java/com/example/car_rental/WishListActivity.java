package com.example.car_rental;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.car_rental.R;

import java.util.ArrayList;

public class WishListActivity extends AppCompatActivity {

    private ListView wishListView;
    private ArrayList<String> wishList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        wishListView = findViewById(R.id.wish_list_view);
        wishList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wishList);
        wishListView.setAdapter(adapter);
    }
}