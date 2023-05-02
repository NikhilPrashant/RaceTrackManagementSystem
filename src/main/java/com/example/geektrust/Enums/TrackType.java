package com.example.geektrust.Enums;

public enum TrackType {
    REGULAR(4, 2, 2),
    VIP(0, 1, 1);
    private int maxBikes;
    private int maxCars;
    private int maxSUVs;
    TrackType(int maxBikes, int maxCars, int maxSuvs) {
        this.maxBikes = maxBikes;
        this.maxCars = maxCars;
        this.maxSUVs = maxSuvs;
    }

    public int getMaxBikes() {
        return maxBikes;
    }

    public int getMaxCars() {
        return maxCars;
    }

    public int getMaxSUVs() {
        return maxSUVs;
    }
}
