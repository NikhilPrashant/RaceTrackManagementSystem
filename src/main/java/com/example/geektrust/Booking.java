package com.example.geektrust;

import java.time.LocalTime;

public class Booking {
    private String vehicleType;
    private String vehicleNumber;
    private LocalTime entryTime;
    private LocalTime exitTime;
    private int hoursBooked;
    private int payment;
    private boolean isVIP;
    private boolean isBooked;

    public Booking(String vehicleType, String vehicleNumber, LocalTime entryTime) {
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = this.entryTime.plusHours(3);
        this.hoursBooked = 3;
        this.payment = 0;
        this.isVIP = false;
        isBooked = false;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalTime exitTime) {
        this.exitTime = exitTime;
    }

    public int getHoursBooked() {
        return hoursBooked;
    }

    public void setHoursBooked(int hoursBooked) {
        this.hoursBooked = hoursBooked;
    }

    public int getPayment() {
        if (!isVIP) {
            return setPayment((VehicleType.valueOf(vehicleType).getRate() * 3) + ((hoursBooked - 3) * 50));
        }
        else return setPayment(VehicleType.valueOf(vehicleType + "_VIP").getRate() * hoursBooked);
    }

    public int setPayment(int payment) {
        this.payment = payment;
        return payment;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
