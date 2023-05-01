package com.example.geektrust;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

public class RegularTrack {
    private int maxBikes;
    private int maxCars;
    private int maxSUVs;
    private int currBikes;
    private int currCars;
    private int currSUVs;
    HashMap<String, Booking> bookingList;

    public RegularTrack() {
        this.maxBikes = 4;
        this.maxCars = 2;
        this.maxSUVs = 2;
        this.currBikes = 0;
        this.currCars = 0;
        this.currSUVs = 0;
        this.bookingList = new HashMap<>();
    }

    public int getMaxBikes() {
        return maxBikes;
    }

    public void setMaxBikes(int maxBikes) {
        this.maxBikes = maxBikes;
    }

    public int getMaxCars() {
        return maxCars;
    }

    public void setMaxCars(int maxCars) {
        this.maxCars = maxCars;
    }

    public int getMaxSUVs() {
        return maxSUVs;
    }

    public void setMaxSUVs(int maxSUVs) {
        this.maxSUVs = maxSUVs;
    }

    public int getCurrBikes() {
        return currBikes;
    }

    public void setCurrBikes(int currBikes) {
        this.currBikes = currBikes;
    }

    public int getCurrCars() {
        return currCars;
    }

    public void setCurrCars(int currCars) {
        this.currCars = currCars;
    }

    public int getCurrSUVs() {
        return currSUVs;
    }

    public void setCurrSUVs(int currSUVs) {
        this.currSUVs = currSUVs;
    }

    public HashMap<String, Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(HashMap<String, Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public String bookTrack(Booking booking, VIPTrack vipTrack) {
        LocalTime currentTime = booking.getEntryTime();
        for (String vehicleNo: bookingList.keySet()) {
            Booking booking1 = bookingList.get(vehicleNo);
            Duration duration = Duration.between(booking.getEntryTime(), booking1.getEntryTime());
            double hours = duration.getSeconds() / 3600.0;
            if (Math.abs(hours) >= 3) {
                if (booking1.getVehicleType().equals("BIKE")) currBikes--;
                else if (booking1.getVehicleType().equals("CAR")) currCars--;
                else currSUVs--;
            }
        }
        if (booking.getVehicleType().equals("BIKE")) {
            if (currBikes < maxBikes) {
                currBikes++;
                booking.setBooked(true);
                bookingList.put(booking.getVehicleNumber(), booking);
                return "SUCCESS";
            }
            else {
                return "RACETRACK_FULL";
            }
        }
        else if (booking.getVehicleType().equals("CAR")) {
            if (currCars < maxCars) {
                currCars++;
                bookingList.put(booking.getVehicleNumber(), booking);
                booking.setBooked(true);
                return "SUCCESS";
            }
            else {
                return vipTrack.bookTrack(booking); // passes to vipTrack if cars are full
            }
        }
        else {
            if (currSUVs < maxSUVs) {
                currSUVs++;
                bookingList.put(booking.getVehicleNumber(), booking);
                booking.setBooked(true);
                return "SUCCESS";
            }
            else {
                return vipTrack.bookTrack(booking);  // passes to vipTrack if suvs are is full
            }
        }
    }
    public String addTime(String vehicleNo, LocalTime additionalTime, VIPTrack vipTrack) {
        if (bookingList.containsKey(vehicleNo)) {
            Booking booking = bookingList.get(vehicleNo);
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
                    if (booking.getVehicleType().equals("BIKE") && currBikes < maxBikes) booking.setExitTime(additionalTime);
                    else if (booking.getVehicleType().equals("CAR") && currCars < maxCars) booking.setExitTime(additionalTime);
                    else if (booking.getVehicleType().equals("SUV") && currSUVs < maxSUVs) booking.setExitTime(additionalTime);
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
