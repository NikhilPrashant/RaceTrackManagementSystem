package com.example.geektrust;

import com.example.geektrust.Entities.BookingManagerEntity;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        BookingManagerEntity bookingManagerEntity = new BookingManagerEntity(); // BookingManagerEntity Created
        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            // returns true if there is another line to read

            while (sc.hasNextLine()) {
                //Add your code here to process input commands
                String input = sc.nextLine();
                bookingManagerEntity.requestBooking(input);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
