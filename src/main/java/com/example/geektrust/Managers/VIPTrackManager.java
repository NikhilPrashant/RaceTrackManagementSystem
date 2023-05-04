package com.example.geektrust.Managers;

import com.example.geektrust.Constants.Constants;
import com.example.geektrust.DTOs.ClearTrackDTO;
import com.example.geektrust.Entities.BookingEntity;
import com.example.geektrust.Enums.TrackType;
import com.example.geektrust.Functions.TrackClearer;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

public class VIPTrackManager {
    Constants constants = new Constants();

    private int currCars;
    private int currSUVs;
    private HashMap<String, BookingEntity> bookingEntityList;

    public VIPTrackManager() {
        this.currCars = 0;
        this.currSUVs = 0;
        this.bookingEntityList = new HashMap<>();
    }

    public String bookTrack(BookingEntity bookingEntity) {
        clearTrack(bookingEntity.getEntryTime());
        if (bookingEntity.getVehicleType().equals("CAR")) {
            return bookCar(bookingEntity);
        }
        else {
            return bookSUV(bookingEntity);
        }
    }

    public String bookCar(BookingEntity bookingEntity) {
        if (currCars < TrackType.VIP.getMaxCars()) {
            currCars++;
            bookingEntity.setVIP(true);
            bookingEntityList.put(bookingEntity.getVehicleNumber(), bookingEntity);
            bookingEntity.setBooked(true);
            return "SUCCESS";
        }
        else {
            return "RACETRACK_FULL";
        }
    }

    public String bookSUV(BookingEntity bookingEntity) {
        if (currSUVs < TrackType.VIP.getMaxSUVs()) {
            currSUVs++;
            bookingEntity.setVIP(true);
            bookingEntityList.put(bookingEntity.getVehicleNumber(), bookingEntity);
            bookingEntity.setBooked(true);
            return "SUCCESS";
        }
        else {
            return "RACETRACK_FULL";
        }
    }

    public void clearTrack(LocalTime currTime) {
        ClearTrackDTO clearTrackDTO = TrackClearer.clearVIPTrack(currTime, bookingEntityList);
        currCars -= clearTrackDTO.getCarsReduced();
        currSUVs -= clearTrackDTO.getSuvsReduced();
    }

    public String addTime(String vehicleNo, LocalTime additionalTime) {
        clearTrack(additionalTime);
        if (bookingEntityList.containsKey(vehicleNo)) {
            BookingEntity bookingEntity = bookingEntityList.get(vehicleNo);
            Duration duration = Duration.between(bookingEntity.getEntryTime(), additionalTime);
            double hours = duration.getSeconds() / constants.secondsInHour;
            if (Math.abs(hours) > constants.hoursAllowed) {
                return addHours(bookingEntity, additionalTime, hours);
            }
            else return "INVALID_EXIT_TIME";
        }
        else {
            return "RACETRACK_FULL";
        }
    }

    public String addHours (BookingEntity bookingEntity, LocalTime additionalTime, double hours) {
        boolean isUpdated = true;
        if (bookingEntity.getVehicleType().equals("CAR") && currCars < TrackType.REGULAR.getMaxCars()) currCars++;
        else if (bookingEntity.getVehicleType().equals("SUV") && currSUVs < TrackType.REGULAR.getMaxSUVs()) currSUVs++;
        else isUpdated = false;
        if (hours > constants.maxDelayAllowed) {
            bookingEntity.setHoursBooked((int)Math.ceil(hours)); // It can be treated as extra hour. If precise time is asked here we will enter precise time and move entire calculation in BookingEntity.java
        }
        if (isUpdated) {
            bookingEntity.setExitTime(additionalTime);
            bookingEntity.setBooked(true);
            return "SUCCESS";
        }
        else return "RACETRACK_FULL";
    }
}
