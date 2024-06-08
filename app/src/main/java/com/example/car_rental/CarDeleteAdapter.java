package com.example.car_rental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class CarDeleteAdapter extends ArrayAdapter<Car> {
    private Context context;
    private ArrayList<Car> cars;

    public CarDeleteAdapter(Context context, ArrayList<Car> cars) {
        super(context, 0, cars);
        this.context = context;
        this.cars = cars;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_car_delete_adapter, parent, false);
        }

        Car car = getItem(position);

        TextView carDetails = convertView.findViewById(R.id.carDetails);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        carDetails.setText("car id : " + car.getCarID() + " \n" +
                "car brand: " + car.getBrand() + " \n" +
                "car model: " + car.getModel() + " \n" +
                "color: " + car.getColor() + "\n" +
                "price per day: " + car.getPrice() + "\n" +
                "car status: " + car.getStatus() + "\n" +
                "car location: " + car.getChapterlocation() + "\n" +
                "expire date: " + car.getInsurance_expires()
        );


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof ManagerDeleat) {
                    ((ManagerDeleat) context).deleteCar(car.getCarID());
                } else {
                    throw new IllegalStateException("Context must be an instance of ManagerDeleat");
                }
            }
        });

        return convertView;
    }
}