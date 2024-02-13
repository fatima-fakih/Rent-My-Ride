package com.example.rentmyride;

public class CarToRent {
        private int carId;
        private String carName;
        private double basePrice;

        public CarToRent(int carId, String carName,double basePrice) {
            this.carId = carId;
            this.carName=carName;
            this.basePrice = basePrice;
        }

        public int getCarId() {
            return carId;
        }

        public String getCarName() {
            return carName;
        }

        public double getBasePrice() {
            return basePrice;
        }

    }
