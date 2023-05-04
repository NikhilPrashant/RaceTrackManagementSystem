package com.example.geektrust.Managers;

import com.example.geektrust.Constants.Constants;
import com.example.geektrust.DTOs.ClearTrackDTO;
import com.example.geektrust.Entities.BookingEntity;
import com.example.geektrust.Enums.TrackType;
import com.example.geektrust.Functions.TrackClearer;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

public class RegularTrackManager {
    Constants constants = new Constants();

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
        clearTrack(bookingEntity.getEntryTime());
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

    public void clearTrack(LocalTime currTime) {
        ClearTrackDTO clearTrackDTO = TrackClearer.clearRegularTrack(currTime, bookingEntityList);
        currBikes -= clearTrackDTO.getBikesReduced();
        currCars -= clearTrackDTO.getCarsReduced();
        currSUVs -= clearTrackDTO.getSuvsReduced();
        bookingEntityList = clearTrackDTO.getBookingEntityList();
    }

    public String addTime(String vehicleNo, LocalTime additionalTime, VIPTrackManager vipTrackManager) {
        clearTrack(additionalTime);
        if (bookingEntityList.containsKey(vehicleNo)) {
            BookingEntity bookingEntity = bookingEntityList.get(vehicleNo);
            Duration duration = Duration.between(additionalTime, bookingEntity.getEntryTime());
            double hours = Math.abs(duration.getSeconds() / constants.secondsInHour);
            if (hours > constants.hoursAllowed) {
                return addHours(bookingEntity, additionalTime, hours);
            }
            else return "INVALID_EXIT_TIME";
        }
        else {
            return vipTrackManager.addTime(vehicleNo, additionalTime);
        }
    }

    public String addHours (BookingEntity bookingEntity, LocalTime additionalTime, double hours) {
        boolean isUpdated = true;
        if (bookingEntity.getVehicleType().equals("BIKE") && currBikes < TrackType.REGULAR.getMaxBikes()) currBikes++;
        else if (bookingEntity.getVehicleType().equals("CAR") && currCars < TrackType.REGULAR.getMaxCars()) currCars++;
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
