package com.example.rentmyride;

public class LuxuryCar extends Car {
    private boolean leatherSeats;
    private boolean sunroof;

    public LuxuryCar(int carId, String carName, String model, double baseprice, boolean available, String imagePath, boolean leatherSeats, boolean sunroof) {
        super(carId, carName, model, baseprice, available, imagePath);
        this.leatherSeats = leatherSeats;
        this.sunroof = sunroof;
    }

    public boolean hasLeatherSeats() {
        return leatherSeats;
    }

    public void setLeatherSeats(boolean leatherSeats) {
        this.leatherSeats = leatherSeats;
    }

    public boolean hasSunroof() {
        return sunroof;
    }

    public void setSunroof(boolean sunroof) {
        this.sunroof = sunroof;
    }
}