package com.example.car_rental.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_rental.R;
import com.example.car_rental.model.Contract;

import java.util.List;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder> {
    private List<Contract> contractList;

    public ContractAdapter(List<Contract> contractList) {
        this.contractList = contractList;
    }

    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contract_card, parent, false);
        return new ContractViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractViewHolder holder, int position) {
        Contract contract = contractList.get(position);
        holder.tvContractId.setText("Contract ID: " + contract.getContractId());

        holder.tvCarId.setText("Car ID: " + contract.getCarId());
        holder.tvStartDate.setText("Start Date: " + contract.getStartDate());
        holder.tvEndDate.setText("End Date: " + contract.getEndDate());
        holder.tvPrice.setText("Price: $" + contract.getPrice());
        holder.tvPaymentStatus.setText("Payment Status: " + contract.getPaymentStatus());
        holder.tvVisaId.setText("Visa ID: " + contract.getVisaId());
    }

    @Override
    public int getItemCount() {
        return contractList.size();
    }

    public static class ContractViewHolder extends RecyclerView.ViewHolder {
        public TextView tvContractId, tvCarId, tvStartDate, tvEndDate, tvPrice, tvPaymentStatus, tvVisaId;

        public ContractViewHolder(View view) {
            super(view);
            tvContractId = view.findViewById(R.id.tvContractId);

            tvCarId = view.findViewById(R.id.tvCarId);
            tvStartDate = view.findViewById(R.id.tvStartDate);
            tvEndDate = view.findViewById(R.id.tvEndDate);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvPaymentStatus = view.findViewById(R.id.tvPaymentStatus);
            tvVisaId = view.findViewById(R.id.tvVisaId);
        }
    }
}
