package com.example.rentmyride;

public class PerDayPriceStrategy implements PriceStrategy {
    private static final double PER_DAY_RATE = 0.24; // 24% of base price per day

    @Override
    public double calculatePrice(double basePrice, int duration) {
        double price = basePrice * 24 * duration;
        return price - price * PER_DAY_RATE;
    }
}

