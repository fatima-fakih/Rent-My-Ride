package com.example.rentmyride;

public interface CarFactory {
    Car createCar(int carId, String carName, String model, double baseprice, boolean available, String imagePath);
}

