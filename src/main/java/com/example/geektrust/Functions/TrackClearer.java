package com.example.geektrust.Functions;

import com.example.geektrust.DTOs.ClearTrackDTO;
import com.example.geektrust.Entities.BookingEntity;

import java.time.LocalTime;
import java.util.HashMap;

public class TrackClearer {

    private TrackClearer() {
    }

    public static ClearTrackDTO clearRegularTrack(LocalTime currTime, HashMap<String, BookingEntity> bookingEntityList) {
        int bikesReduced = 0;
        int carsReduced = 0;
        int suvsReduced = 0;
        for (String vehicleNo: bookingEntityList.keySet()) {
            BookingEntity bookingEntity1 = bookingEntityList.get(vehicleNo);
            if (bookingEntity1.isBooked() && (currTime.equals(bookingEntity1.getExitTime()) || currTime.isAfter(bookingEntity1.getExitTime()))) {
                bookingEntity1.setBooked(false);
                if (bookingEntity1.getVehicleType().equals("BIKE")) bikesReduced++;
                else if (bookingEntity1.getVehicleType().equals("CAR")) carsReduced++;
                else suvsReduced++;
            }
        }
        int[] reducedCounts = {bikesReduced, carsReduced, suvsReduced};
        return new ClearTrackDTO(bookingEntityList, bikesReduced, carsReduced, suvsReduced);
    }

    public static ClearTrackDTO clearVIPTrack(LocalTime currTime, HashMap<String, BookingEntity> bookingEntityList) {
        int carsReduced = 0;
        int suvsReduced = 0;
        for (String vehicleNo: bookingEntityList.keySet()) {
            BookingEntity bookingEntity1 = bookingEntityList.get(vehicleNo);
            if (bookingEntity1.isBooked() && (currTime.equals(bookingEntity1.getExitTime()) || currTime.isAfter(bookingEntity1.getExitTime()))) {
                bookingEntity1.setBooked(false);
                if (bookingEntity1.getVehicleType().equals("CAR")) carsReduced++;
                else suvsReduced++;
            }
        }
        int[] reducedCounts = {carsReduced, suvsReduced};
        return new ClearTrackDTO(bookingEntityList, 0, carsReduced, suvsReduced);
    }
}
