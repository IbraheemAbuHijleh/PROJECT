package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Queries extends AppCompatActivity {
private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_queries);

        Intent I=getIntent();

        list=findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                  Intent I=new Intent(Queries.this,Result.class);
                  startActivity(I);
                }
                if(position == 1){
                    Intent I=new Intent(Queries.this,Result1.class);
                    startActivity(I);
                }
            }
        });
    }
}