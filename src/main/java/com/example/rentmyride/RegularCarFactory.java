package com.example.rentmyride;

public class RegularCarFactory implements CarFactory {
    @Override
    public Car createCar(int carId, String carName, String model, double baseprice, boolean available, String imagePath) {
        return new RegularCar( carId, carName, model, baseprice, available, imagePath);
    }
}

