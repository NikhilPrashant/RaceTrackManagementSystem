package com.example.geektrust.Entities;

import com.example.geektrust.Constants.Constants;
import com.example.geektrust.Enums.VehicleType;

import java.time.LocalTime;

public class BookingEntity {

    Constants constants = new Constants();

    private final String vehicleType;
    private final String vehicleNumber;
    private final LocalTime entryTime;
    private LocalTime exitTime;
    private int hoursBooked;
    private int payment;
    private boolean isVIP;
    private boolean isBooked;

    public BookingEntity(String vehicleType, String vehicleNumber, LocalTime entryTime) {
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = this.entryTime.plusHours(constants.hoursAllowed);
        this.hoursBooked = constants.hoursAllowed;
        this.payment = 0;
        this.isVIP = false;
        isBooked = false;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public LocalTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalTime exitTime) {
        this.exitTime = exitTime;
    }

    public void setHoursBooked(int hoursBooked) {
        this.hoursBooked = hoursBooked;
    }

    public int getPayment() {
        if (!isVIP) {
            return setPayment((VehicleType.valueOf(vehicleType).getRate() * 3) + ((hoursBooked - 3) * 50));
        } else return setPayment(VehicleType.valueOf(vehicleType + "_VIP").getRate() * hoursBooked);
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

    @Override
    public String toString() {
        return "BookingEntity{" + "vehicleType='" + vehicleType + '\'' + ", vehicleNumber='" + vehicleNumber + '\'' + ", entryTime=" + entryTime + ", exitTime=" + exitTime + ", hoursBooked=" + hoursBooked + ", payment=" + payment + ", isVIP=" + isVIP + ", isBooked=" + isBooked + '}';
    }
}
