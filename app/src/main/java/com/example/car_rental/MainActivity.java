package com.example.car_rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
 private ImageButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        add=findViewById(R.id.addcar);
    }

    public void btncliack(View view) {
        Intent intent=new Intent(MainActivity.this, Insertcar.class);
        startActivity(intent);
    }

    public void btnupdate(View view) {

        Intent intent=new Intent(MainActivity.this, UPDATE.class);

        startActivity(intent);
    }

    public void btndisplay(View view) {

        Intent intent=new Intent(MainActivity.this, List.class);

        startActivity(intent);
    }

    public void btnQueries(View view) {

        Intent intent=new Intent(MainActivity.this, Queries.class);

        startActivity(intent);

    }

    public void btnLogout(View view) {
     finish();
    }

    public void btnserche(View view) {

        Intent intent=new Intent(MainActivity.this, ManagerSearch.class);

        startActivity(intent);


    }

    public void btndelete(View view) {

        Intent intent=new Intent(MainActivity.this, ManagerDeleat.class);

        startActivity(intent);
    }
}