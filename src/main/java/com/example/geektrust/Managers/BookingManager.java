package com.example.geektrust.Managers;

import com.example.geektrust.Constants.Constants;
import com.example.geektrust.Entities.BookingEntity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {

    Constants constants = new Constants();

    RegularTrackManager regularTrackManager;
    VIPTrackManager vipTrackManager;
    List<BookingEntity> bookingList = new ArrayList<>(); // list of bookings with boolean isBooked to calculate payment for successfull bookings
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // all string input of time are in format of HH:mm

    public BookingManager() {
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
        LocalTime startTime = LocalTime.of(constants.minStartTime, 0);
        LocalTime endTime = LocalTime.of(constants.maxStartTime, 0);
        if (time.isAfter(startTime) && time.isBefore(endTime) || time.equals(startTime) || time.equals(endTime)) {
            BookingEntity booking = new BookingEntity(bookingRequest[1], bookingRequest[2], time);
            String response = regularTrackManager.bookTrack(booking, vipTrackManager);
            if (response.equals("SUCCESS")) {
                bookingList.add(booking);
            }
            System.out.println(response);
        } else {
            System.out.println("INVALID_ENTRY_TIME");
        }
    }

    // function to add time if track is available
    public void add(String[] bookingRequest) {
        LocalTime time = LocalTime.parse(bookingRequest[2], formatter);
        LocalTime startTime = LocalTime.of(constants.minExitTime, 0);
        LocalTime endTime = LocalTime.of(constants.maxExitTime, 0);
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
            if (!booking.isVIP()) revenueRegular += booking.getPayment(); //getPayment uses setPayment according to hours on track before getting
            else revenueVIP += booking.getPayment();
        }
        System.out.println(revenueRegular + " " + revenueVIP);
    }
}
