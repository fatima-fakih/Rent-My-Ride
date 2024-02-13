package com.example.rentmyride;

public class PerMonthPriceStrategy implements PriceStrategy {
    private static final double PER_MONTH_RATE = 0.5; // 50% of base price per month

    @Override
    public double calculatePrice(double basePrice, int duration) {
        double price = basePrice * 24 * 30 * duration;
        return price - price * PER_MONTH_RATE;
    }
}


