package com.example.car_rental;

public class Car {
    private String carID;
    private String brand;
    private String color;
    private String model;
    private String price;
    private String statusNumber;
    private String status;

    private String insurance_expires;

     private String chapterlocation ;

    public Car() {
        this.carID = carID;
        this.brand = brand;
        this.color = color;
        this.model = model;
        this.price = price;
        this.statusNumber = statusNumber;
        this.status = status;
        this.insurance_expires=insurance_expires;
        this.chapterlocation=chapterlocation;
    }
    public Car(String carID, String brand, String color, String model, String price, String statusNumber, String status,String Insurance_Expires,String ChapterLocation) {
        this.carID = carID;
        this.brand = brand;
        this.color = color;
        this.model = model;
        this.price = price;
        this.statusNumber = statusNumber;
        this.status = status;
        this.insurance_expires=Insurance_Expires;
        this.chapterlocation=ChapterLocation;
    }
    public Car(String carID, String brand, String color, String model, String price, String statusNumber, String status) {
        this.carID = carID;
        this.brand = brand;
        this.color = color;
        this.model = model;
        this.price = price;
        this.statusNumber = statusNumber;
        this.status = status;
    }
    public Car(String carID, String brand, String color, String model, String price, String statusNumber, String date , String location ) {
        this.carID = carID;
        this.brand = brand;
        this.color = color;
        this.model = model;
        this.price = price;
        this.statusNumber = statusNumber;
        this.status = status;
        this.statusNumber=statusNumber;
        this.insurance_expires=date;
        this.chapterlocation=location;
    }
    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatusNumber() {
        return statusNumber;
    }

    public void setStatusNumber(String statusNumber) {
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void updateStatus(String status) {
        this.status = status;
    }

    public String getInsurance_expires() {
        return insurance_expires;
    }

    public void setInsurance_expires(String insurance_expires) {
        this.insurance_expires = insurance_expires;
    }

    public String getChapterlocation() {
        return chapterlocation;
    }

    public void setChapterlocation(String chapterlocation) {
        this.chapterlocation = chapterlocation;
    }

    @Override
    public String toString() {
        return "Car: " + carID + "\n" +
                "Model: " + model + " " + brand + "\n" +
                "Color: " + color + "\n" +
                "Price: " + price + "\n" +
                "Number of seats: " + statusNumber + "\n" +
                "Status: " + status + "\n" +
                "Insurance Status: " + insurance_expires + "\n" +
                "Location: " + chapterlocation + "\n";
    }

}
