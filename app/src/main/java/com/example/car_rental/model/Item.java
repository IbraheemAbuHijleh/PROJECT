package com.example.car_rental.model;

import java.util.List;

public class Item implements Comparable<Item> {
    public Item(String location, String carName, String price, List<Integer> images, int totalImages) {
        this.location = location;
        this.carName = carName;
        this.price = price;
        this.images = images;
        this.totalImages = totalImages;
    }

    public Item() {
    }

    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Item(String location, String carName, String price, String carid, String color, String model, String seatsNumber) {
        this.location = location;
        this.carName = carName;
        this.price = price;
        this.carid=carid;
        this.color=color;
        this.model=model;
        this.seatsNumber=seatsNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private String location;
    private String carName;
private String model;
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    private String price;
    private List<Integer> images;
    private int totalImages;
    private String carid;
    private String color;
    private String seatsNumber;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public int getTotalImages() {
        return totalImages;
    }

    public void setTotalImages(int totalImages) {
        this.totalImages = totalImages;
    }

    @Override
    public int compareTo(Item o) {
        return Float.compare(this.getPriceAsFloat(), o.getPriceAsFloat());
    }
    public float getPriceAsFloat() {
        // Convert the price string to float
        return Float.parseFloat(price);
    }
}
