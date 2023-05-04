package com.example.geektrust.DTOs;

import com.example.geektrust.Entities.BookingEntity;

import java.util.HashMap;

public class ClearTrackDTO {
    private HashMap<String, BookingEntity> bookingEntityList;
    private int bikesReduced;
    private int carsReduced;
    private int suvsReduced;

    public ClearTrackDTO(HashMap<String, BookingEntity> bookingEntityList, int bikesReduced, int carsReduced, int suvsReduced) {
        this.bookingEntityList = bookingEntityList;
        this.bikesReduced = bikesReduced;
        this.carsReduced = carsReduced;
        this.suvsReduced = suvsReduced;
    }

    public HashMap<String, BookingEntity> getBookingEntityList() {
        return bookingEntityList;
    }

    public int getBikesReduced() {
        return bikesReduced;
    }

    public int getCarsReduced() {
        return carsReduced;
    }

    public int getSuvsReduced() {
        return suvsReduced;
    }
}
