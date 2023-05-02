package com.example.geektrust.Enums;

public enum VehicleType {
    BIKE(60),
    CAR(120),
    CAR_VIP(250),
    SUV(200),
    SUV_VIP(300);
    private int rate;

    VehicleType(int rate) {
        this.rate = rate;
    }
    public int getRate() {
        return rate;
    }
}
