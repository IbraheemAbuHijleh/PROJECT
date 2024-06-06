package com.example.car_rental.ui.notifications;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.car_rental.R;

import java.util.List;

public class SpecialOffersAdapter extends RecyclerView.Adapter<SpecialOffersAdapter.SpecialOffersViewHolder> {

    private List<SpecialOffer> specialOffers;

    public SpecialOffersAdapter(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    @NonNull
    @Override
    public SpecialOffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_special_offer, parent, false);
        return new SpecialOffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialOffersViewHolder holder, int position) {
        SpecialOffer offer = specialOffers.get(position);
        holder.textViewName.setText(offer.getName());
        holder.textViewDiscount.setText(offer.getDiscount());
        holder.textViewNewPrice.setText(offer.getPrice());
        holder.textViewEndDate.setText(offer.getEndDate());
    }

    @Override
    public int getItemCount() {
        return specialOffers.size();
    }

    public static class SpecialOffersViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewDiscount;
        TextView textViewNewPrice;
        TextView textViewEndDate;

        public SpecialOffersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView_name);
            textViewDiscount = itemView.findViewById(R.id.discount);
            textViewNewPrice = itemView.findViewById(R.id.text_newPrice);
            textViewEndDate = itemView.findViewById(R.id.textView_endDate);
        }
    }
}