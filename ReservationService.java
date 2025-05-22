package com.realestate.service;

import com.realestate.model.Reservation;
import com.realestate.util.FileHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {



    public Reservation addReservation(String propertyId, String buyerId) throws IOException {
        String reservationId = "RES" + UUID.randomUUID().toString().substring(0, 7); // Unique ID with reservation prefix
        String reservationDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); // Current date
        String status = "PENDING"; // Initial status
        Reservation reservation = new Reservation(reservationId, propertyId, buyerId, reservationDate, status); // Create new Reservation object
        FileHandler.writeReservation(reservation.toDataString()); // Write to reservations.txt using FileHandler
        return reservation; // Return the created reservation
    }

    //  reservations for buyer from reservations

    public List<Reservation> getReservationsByBuyerId(String buyerId) throws IOException {
        List<String> reservationData = FileHandler.readReservations(); // Read all lines from reservations
        List<Reservation> reservations = new ArrayList<>();
        for (String data : reservationData) {
            if (!data.isEmpty()) {
                Reservation reservation = Reservation.fromDataString(data);
                if (reservation != null && reservation.getBuyerId().equals(buyerId)) {
                    reservations.add(reservation); // Add only reservations for the  buyer
                }
            }
        }
        return reservations; // Return the list of reservations for the buyer
    }


    public List<Reservation> getReservationsByPropertyId(String propertyId) throws IOException {
        List<String> reservationData = FileHandler.readReservations(); // Read all lines from reservations.txt
        List<Reservation> reservations = new ArrayList<>();
        for (String data : reservationData) {
            if (!data.isEmpty()) {
                Reservation reservation = Reservation.fromDataString(data);
                if (reservation != null && reservation.getPropertyId().equals(propertyId)) {
                    reservations.add(reservation); // Add only reservations for the specified property
                }
            }
        }
        return reservations; // Return the list of reservations for the property
    }

    // Retrieves a single reservation by its ID
    public Reservation getReservationById(String reservationId) throws IOException {
        List<String> reservationData = FileHandler.readReservations(); // Read all lines from reservations.txt
        for (String data : reservationData) {
            if (!data.isEmpty()) {
                Reservation reservation = Reservation.fromDataString(data);
                if (reservation != null && reservation.getReservationId().equals(reservationId)) {
                    return reservation; // Return matching reservation
                }
            }
        }
        return null; // Return null if not found
    }


    public Reservation updateReservationStatus(String reservationId, String status) throws IOException {
        List<String> reservationData = FileHandler.readReservations(); // Read all current reservations from file
        List<String> updatedData = new ArrayList<>();
        Reservation updatedReservation = null;
        for (String data : reservationData) {
            if (!data.isEmpty()) {
                Reservation reservation = Reservation.fromDataString(data);
                if (reservation != null && reservation.getReservationId().equals(reservationId)) {

                    updatedReservation = new Reservation(reservationId, reservation.getPropertyId(), reservation.getBuyerId(), reservation.getReservationDate(), status);
                    updatedData.add(updatedReservation.toDataString()); // Add updated data to list
                } else {
                    updatedData.add(data); // Keep unchanged data
                }
            }
        }
        if (updatedReservation != null) {
            FileHandler.updateReservations(updatedData); // Write updated list back to reservations.txt
        }
        return updatedReservation; // Return updated reservation or null if not found
    }

    //  Removes a reservation from reservations
    public boolean deleteReservation(String reservationId) throws IOException {
        List<String> reservationData = FileHandler.readReservations(); // Read all current reservations from file
        List<String> updatedData = new ArrayList<>();
        boolean deleted = false;
        for (String data : reservationData) {
            if (!data.isEmpty()) {
                Reservation reservation = Reservation.fromDataString(data);
                if (reservation == null || !reservation.getReservationId().equals(reservationId)) {
                    updatedData.add(data); // Keep non-matching reservations
                } else {
                    deleted = true; // Mark as deleted if found
                }
            }
        }
        if (deleted) {
            FileHandler.updateReservations(updatedData); // Write updated list back to reservations.txt
        }
        return deleted; // Return true if deletion , false if not found
    }
}
