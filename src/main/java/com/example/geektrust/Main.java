package com.example.geektrust;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        RegularTrack regularTrack = new RegularTrack(); // regularTrack created once
        VIPTrack vipTrack = new VIPTrack(); // vipTrack created once
        List<Booking> bookingList = new ArrayList<>(); // list of bookings with boolean isBooked to calculate payment for successfull bookings
        try {

            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            // returns true if there is another line to read

            while (sc.hasNextLine()) {
                //Add your code here to process input commands
                String input = sc.nextLine();
                String bookingRequest[] = input.split(" ");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // all string input of time are in format of HH:mm

                // Case of booking track
                if (bookingRequest[0].equals("BOOK")) {
                    LocalTime time = LocalTime.parse(bookingRequest[3], formatter);
                    LocalTime startTime = LocalTime.of(13, 0);
                    LocalTime endTime = LocalTime.of(17, 0);
                    if (time.isAfter(startTime) && time.isBefore(endTime) || time.equals(startTime) || time.equals(endTime)) {
                        Booking booking = new Booking(bookingRequest[1], bookingRequest[2], time);
                        bookingList.add(booking);
                        System.out.println(regularTrack.bookTrack(booking, vipTrack));
                    } else {
                        System.out.println("INVALID_ENTRY_TIME");
                    }
                }

                // Case of adding time
                else if (bookingRequest[0].equals("ADDITIONAL")) {
                    LocalTime time = LocalTime.parse(bookingRequest[2], formatter);
                    LocalTime startTime = LocalTime.of(16, 0);
                    LocalTime endTime = LocalTime.of(20, 0);
                    if (time.isAfter(startTime) && time.isBefore(endTime) || time.equals(startTime) || time.equals(endTime)) {
                        System.out.println(regularTrack.addTime(bookingRequest[1], time, vipTrack));
                    } else {
                        System.out.println("INVALID_EXIT_TIME");
                    }
                }
            }

            // revenue calculation by checking if booking isBooked
            int revenueRegular = 0;
            int revenueVIP = 0;
            for (Booking booking : bookingList) {
                if (booking.isBooked()) {
                    if (!booking.isVIP()) revenueRegular += booking.getPayment(); //getPayment uses setPayment according to hours on track before getting
                    else revenueVIP += booking.getPayment();
                }
            }
            System.out.println(revenueRegular + " " + revenueVIP);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
