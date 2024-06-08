package com.example.car_rental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Intent I=getIntent();


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

        Intent intent=new Intent(MainActivity.this, SignIn.class);
        startActivity(intent);
    }

    public void btnserche(View view) {

        Intent intent=new Intent(MainActivity.this, ManagerSearch.class);

        startActivity(intent);


    }

    public void btndelete(View view) {

        Intent intent=new Intent(MainActivity.this, ManagerDeleat.class);

        startActivity(intent);
    }

    public void btnrented(View view){
        Intent intent=new Intent(MainActivity.this,ManagerApproved.class);

        startActivity(intent);
    }
}