package com.example.car_rental;

import  android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CarAdapter extends ArrayAdapter<Car> {
    public CarAdapter(Context context, List<Car> cars) {
        super(context, 0, cars);
    }

    // ViewHolder pattern to improve performance
    private static class ViewHolder {
        TextView tvCarID;
        TextView tvBrand;
        TextView tvColor;
        TextView tvModel;
        TextView tvPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Car car = getItem(position);
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_item, parent, false);

            // Lookup view for data population and store in viewHolder
            viewHolder.tvCarID = convertView.findViewById(R.id.tv_car_id);
            viewHolder.tvBrand = convertView.findViewById(R.id.tv_brand);
            viewHolder.tvColor = convertView.findViewById(R.id.tv_color);
            viewHolder.tvModel = convertView.findViewById(R.id.tv_model);
            viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // Reuse the viewHolder object from the cache
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        viewHolder.tvCarID.setText("ID: " + car.getCarID());
        viewHolder.tvBrand.setText("Brand: " + car.getBrand());
        viewHolder.tvColor.setText("Color: " + car.getColor());
        viewHolder.tvModel.setText("Model: " + car.getModel());
        viewHolder.tvPrice.setText("Price: " + car.getPrice());

        // Return the completed view to render on screen
        return convertView;
    }
}
