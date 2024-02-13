package com.example.rentmyride;

public class Car {

    private int carid;
    private String carName;
    private String model;
    private double baseprice;
    private boolean available;

    private String imagePath;

    public Car(int carId, String carName, String model,  double baseprice, boolean available,  String imagePath) {

        this.carid = carId;
        this.carName = carName;
        this.model = model;
        this.baseprice = baseprice;
        this.available = available;
        this.imagePath = imagePath;
    }



    public int getCarid() {
        return carid;
    }

    public void setCarId(int carId) {
        this.carid = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }



    public double getBaseprice() {
        return baseprice;
    }

    public void setPricePerDay(double pricePerDay) {
        this.baseprice = pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }



    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String toString() {
        return "Car ID: " + carid + "\n" +
                "Car Name: " + carName + "\n" +
                "Model: " + model + "\n" +
                "Price per day: " + baseprice + "\n" +
                "Available: " + available + "\n" +
                "Image path: " + imagePath + "\n";
    }

}
