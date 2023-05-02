package com.example.geektrust.TrackManagers;

import com.example.geektrust.Entities.BookingEntity;
import com.example.geektrust.Enums.TrackType;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

public class VIPTrackManager {
    private int currCars;
    private int currSUVs;
    private HashMap<String, BookingEntity> bookingList;

    public VIPTrackManager() {
        this.currCars = 0;
        this.currSUVs = 0;
        this.bookingList = new HashMap<>();
    }

    public String bookTrack(BookingEntity booking) {
        LocalTime currentTime = booking.getEntryTime();
        for (String vehicleNo: bookingList.keySet()) {
            BookingEntity booking1 = bookingList.get(vehicleNo);
            Duration duration = Duration.between(booking.getEntryTime(), booking1.getEntryTime());
            double hours = duration.getSeconds() / 3600.0;
            if (hours >= 3) {
                if (booking1.getVehicleType().equals("CAR")) currCars--;
                else currSUVs--;
            }
        }
        if (booking.getVehicleType().equals("CAR")) {
            return bookCar(booking);
        }
        else {
            return bookSUV(booking);
        }
    }

    public String bookCar(BookingEntity bookingEntity) {
        if (currCars < TrackType.VIP.getMaxCars()) {
            currCars++;
            bookingEntity.setVIP(true);
            bookingList.put(bookingEntity.getVehicleNumber(), bookingEntity);
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
            bookingList.put(bookingEntity.getVehicleNumber(), bookingEntity);
            bookingEntity.setBooked(true);
            return "SUCCESS";
        }
        else {
            return "RACETRACK_FULL";
        }
    }

    public String addTime(String vehicleNo, LocalTime additionalTime) {
        if (bookingList.containsKey(vehicleNo)) {
            BookingEntity booking = bookingList.get(vehicleNo);
            Duration duration = Duration.between(booking.getEntryTime(), additionalTime);
            double hours = duration.getSeconds() / 3600.0;
            if (Math.abs(hours) > 3) {
                booking.setExitTime(additionalTime);
                if (hours > 3.25) {
                    booking.setHoursBooked((int)Math.ceil(hours)); // It can be treated as extra hour
                    // If precise time is asked here we will enter precise time and move entire calculation in Booking.java
                }
                else {
                    // It cn be treated as 3 hours and no need to set hours
                    if (booking.getVehicleType().equals("CAR") && currCars < TrackType.VIP.getMaxCars()) booking.setExitTime(additionalTime);
                    else if (booking.getVehicleType().equals("SUV") && currSUVs < TrackType.VIP.getMaxSUVs()) booking.setExitTime(additionalTime);
                    else return "RACETRACK_FULL";
                }
            }
            else return "INVALID_EXIT_TIME";
        }
        else {
            return "RACETRACK_FULL";
        }
        return "SUCCESS";
    }
}
