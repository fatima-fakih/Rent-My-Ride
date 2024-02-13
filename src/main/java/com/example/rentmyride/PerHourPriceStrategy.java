package com.example.rentmyride;

public class PerHourPriceStrategy implements PriceStrategy {
    @Override
    public double calculatePrice(double basePrice, int duration) {
        return basePrice * duration;
    }
}

