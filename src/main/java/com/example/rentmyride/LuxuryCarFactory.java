package com.example.rentmyride;

public class LuxuryCarFactory implements CarFactory {
    public Car createCar(int carId, String carName, String model, double baseprice, boolean available, String imagePath) {

        boolean hasLeatherSeats = true;
        boolean hasSunroof = true;
        return new LuxuryCar(carId, carName, model, baseprice, available, imagePath, hasLeatherSeats, hasSunroof);
    }
}