package com.example.rentmyride;

import java.time.LocalDate;

public class Rental {
    private CarToRent car;
    private LocalDate startDate;
    private int duration;
    private PriceStrategy priceStrategy;
    public Rental(){

    }

    public Rental(CarToRent car, LocalDate startDate, int duration, PriceStrategy priceStrategy) {
        this.car = car;
        this.startDate = startDate;
        this.duration = duration;
        this.priceStrategy = priceStrategy;
    }

    public double calculateRentalPrice() {
        double basePrice = car.getBasePrice();
        return priceStrategy.calculatePrice(basePrice, duration);
    }

    // getters and setters

    public CarToRent getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getDuration() {
        return duration;
    }

    public PriceStrategy getPriceStrategy() {
        return priceStrategy;
    }

    public void setCar(CarToRent car) {
        this.car = car;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPriceStrategy(PriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }
}

