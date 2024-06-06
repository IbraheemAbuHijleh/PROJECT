package com.example.car_rental.ui.notifications;


public class SpecialOffer {
    private int ID;
    private String name;
    private int CarID;
    private String StartDate;
    private String EndDate;
    private String Discount;
    private String price ;

    public SpecialOffer() {
    }

    public SpecialOffer(int ID,String name, int CarID, String StartDate, String EndDate, String Discount,String price) {
        this.ID = ID;
        this.name = name;
        this.CarID = CarID;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Discount = Discount;
        this.price = price;

    }
    //
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCarID() {
        return CarID;
    }

    public void setCarID(int carID) {
        CarID = carID;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getDiscount() {
        return Discount;
    }


    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
