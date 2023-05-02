package com.example.geektrust.TrackManagers;

import com.example.geektrust.Entities.BookingEntity;
import com.example.geektrust.Enums.TrackType;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

public class RegularTrackManager {
    private int currBikes;
    private int currCars;
    private int currSUVs;
    HashMap<String, BookingEntity> bookingEntityList;

    public RegularTrackManager() {
        this.currBikes = 0;
        this.currCars = 0;
        this.currSUVs = 0;
        this.bookingEntityList = new HashMap<>();
    }

    public String bookTrack(BookingEntity bookingEntity, VIPTrackManager vipTrackManager) {
        LocalTime currentTime = bookingEntity.getEntryTime();
        for (String vehicleNo: bookingEntityList.keySet()) {
            BookingEntity bookingEntity1 = bookingEntityList.get(vehicleNo);
            Duration duration = Duration.between(bookingEntity.getEntryTime(), bookingEntity1.getEntryTime());
            double hours = duration.getSeconds() / 3600.0;
            if (Math.abs(hours) >= 3) {
                if (bookingEntity1.getVehicleType().equals("BIKE")) currBikes--;
                else if (bookingEntity1.getVehicleType().equals("CAR")) currCars--;
                else currSUVs--;
            }
        }
        if (bookingEntity.getVehicleType().equals("BIKE")) {
            return bookBike(bookingEntity);
        }
        else if (bookingEntity.getVehicleType().equals("CAR")) {
            return bookCar(bookingEntity, vipTrackManager);
        }
        else {
            return bookSUV(bookingEntity, vipTrackManager);
        }
    }

    public String bookBike(BookingEntity bookingEntity) {
        if (currBikes < TrackType.REGULAR.getMaxBikes()) {
            currBikes++;
            bookingEntity.setBooked(true);
            bookingEntityList.put(bookingEntity.getVehicleNumber(), bookingEntity);
            return "SUCCESS";
        }
        else {
            return "RACETRACK_FULL";
        }
    }

    public String bookCar(BookingEntity bookingEntity, VIPTrackManager vipTrackManager) {
        if (currCars < TrackType.REGULAR.getMaxCars()) {
            currCars++;
            bookingEntityList.put(bookingEntity.getVehicleNumber(), bookingEntity);
            bookingEntity.setBooked(true);
            return "SUCCESS";
        }
        else {
            return vipTrackManager.bookTrack(bookingEntity); // passes to vipTrackManager if cars are full
        }
    }

    public String bookSUV(BookingEntity bookingEntity, VIPTrackManager vipTrackManager) {
        if (currSUVs < TrackType.REGULAR.getMaxSUVs()) {
            currSUVs++;
            bookingEntityList.put(bookingEntity.getVehicleNumber(), bookingEntity);
            bookingEntity.setBooked(true);
            return "SUCCESS";
        }
        else {
            return vipTrackManager.bookTrack(bookingEntity);  // passes to vipTrackManager if SUVs are is full
        }
    }

    public String addTime(String vehicleNo, LocalTime additionalTime, VIPTrackManager vipTrackManager) {
        if (bookingEntityList.containsKey(vehicleNo)) {
            BookingEntity bookingEntity = bookingEntityList.get(vehicleNo);
            Duration duration = Duration.between(bookingEntity.getEntryTime(), additionalTime);
            double hours = duration.getSeconds() / 3600.0;
            if (Math.abs(hours) > 3) {
                bookingEntity.setExitTime(additionalTime);
                if (hours > 3.25) {
                    bookingEntity.setHoursBooked((int)Math.ceil(hours)); // It can be treated as extra hour
                    // If precise time is asked here we will enter precise time and move entire calculation in BookingEntity.java
                }
                else {
                    // It cn be treated as 3 hours and no need to set hours
                    if (bookingEntity.getVehicleType().equals("BIKE") && currBikes < TrackType.REGULAR.getMaxBikes()) bookingEntity.setExitTime(additionalTime);
                    else if (bookingEntity.getVehicleType().equals("CAR") && currCars < TrackType.REGULAR.getMaxCars()) bookingEntity.setExitTime(additionalTime);
                    else if (bookingEntity.getVehicleType().equals("SUV") && currSUVs < TrackType.REGULAR.getMaxSUVs()) bookingEntity.setExitTime(additionalTime);
                    else return "RACETRACK_FULL";
                }
            }
            else return "INVALID_EXIT_TIME";
        }
        else {
            return vipTrackManager.addTime(vehicleNo, additionalTime);
        }
        return "SUCCESS";
    }
}
