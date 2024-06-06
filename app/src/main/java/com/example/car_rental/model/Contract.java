package com.example.car_rental.model;

public class Contract {
    private String contractId;
    private String username;
    private String carId;
    private String startDate;
    private String endDate;
    private String price;
    private String paymentStatus;
    private String visaId;

    // Constructor, getters, and setters

    public Contract(String contractId, String username, String carId, String startDate, String endDate, String price, String paymentStatus, String visaId) {
        this.contractId = contractId;
        this.username = username;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.visaId = visaId;
    }

    public String getContractId() { return contractId; }
    public String getUsername() { return username; }
    public String getCarId() { return carId; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getPrice() { return price; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getVisaId() { return visaId; }
}
