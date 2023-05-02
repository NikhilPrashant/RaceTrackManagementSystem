package com.example.geektrust.Entities;

import com.example.geektrust.TrackManagers.RegularTrackManager;
import com.example.geektrust.TrackManagers.VIPTrackManager;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingManagerEntity {

    RegularTrackManager regularTrackManager;
    VIPTrackManager vipTrackManager;
    List<BookingEntity> bookingList = new ArrayList<>(); // list of bookings with boolean isBooked to calculate payment for successfull bookings
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // all string input of time are in format of HH:mm

    public BookingManagerEntity() {
        this.regularTrackManager = new RegularTrackManager(); // regularTrackManager created once
        this.vipTrackManager = new VIPTrackManager(); // vipTrackManager created once
    }

    public void requestBooking(String input) {
        String bookingRequest[] = input.split(" ");

        if (bookingRequest[0].equals("BOOK")) {
            book(bookingRequest);
        }
        else if (bookingRequest[0].equals("ADDITIONAL")) {
            add(bookingRequest);
        }
        else if (bookingRequest[0].equals("REVENUE")) {
            calculate(bookingRequest);
        }
        else System.out.println("INVALID");
    }

    public void book(String[] bookingRequest) {
        LocalTime time = LocalTime.parse(bookingRequest[3], formatter);
        LocalTime startTime = LocalTime.of(13, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        if (time.isAfter(startTime) && time.isBefore(endTime) || time.equals(startTime) || time.equals(endTime)) {
            BookingEntity booking = new BookingEntity(bookingRequest[1], bookingRequest[2], time);
            bookingList.add(booking);
            System.out.println(regularTrackManager.bookTrack(booking, vipTrackManager));
        } else {
            System.out.println("INVALID_ENTRY_TIME");
        }
    }

    // function to add time if track is available
    public void add(String[] bookingRequest) {
        LocalTime time = LocalTime.parse(bookingRequest[2], formatter);
        LocalTime startTime = LocalTime.of(16, 0);
        LocalTime endTime = LocalTime.of(20, 0);
        if (time.isAfter(startTime) && time.isBefore(endTime) || time.equals(startTime) || time.equals(endTime)) {
            System.out.println(regularTrackManager.addTime(bookingRequest[1], time, vipTrackManager));
        } else {
            System.out.println("INVALID_EXIT_TIME");
        }
    }

    // function to calculate revenue
    public void calculate(String[] bookingRequest) {
        // revenue calculation by checking if booking isBooked
        int revenueRegular = 0;
        int revenueVIP = 0;
        for (BookingEntity booking : bookingList) {
            if (booking.isBooked()) {
                if (!booking.isVIP()) revenueRegular += booking.getPayment(); //getPayment uses setPayment according to hours on track before getting
                else revenueVIP += booking.getPayment();
            }
        }
        System.out.println(revenueRegular + " " + revenueVIP);
    }
}
