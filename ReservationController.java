package com.realestate.controller;

import com.realestate.model.Reservation;
import com.realestate.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Creates a new reservation
    //  POST /reservations
    @PostMapping
    public ResponseEntity<Reservation> addReservation(
            @RequestParam String propertyId,
            @RequestParam String buyerId) throws IOException {
        Reservation reservation = reservationService.addReservation(propertyId, buyerId);
        return ResponseEntity.ok(reservation); // Returns the created reservation as JSON
    }

    // reservations by buyer ID
    //GET /reservations/buyer/{buyerId}
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Reservation>> getReservationsByBuyerId(@PathVariable String buyerId) throws IOException {
        List<Reservation> reservations = reservationService.getReservationsByBuyerId(buyerId);
        return ResponseEntity.ok(reservations); // Returns list of reservations for the buyer
    }

    //  reservations by property ID
    //  GET /reservations/property/{propertyId}
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Reservation>> getReservationsByPropertyId(@PathVariable String propertyId) throws IOException {
        List<Reservation> reservations = reservationService.getReservationsByPropertyId(propertyId);
        return ResponseEntity.ok(reservations); // Returns list of reservations for the property
    }


    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String reservationId) throws IOException {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation != null) {
            return ResponseEntity.ok(reservation); // Returns the reservation if found
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }


    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation> updateReservationStatus(
            @PathVariable String reservationId,
            @RequestParam String status) throws IOException {
        Reservation reservation = reservationService.updateReservationStatus(reservationId, status);
        if (reservation != null) {
            return ResponseEntity.ok(reservation); // Returns updated reservation
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }

    
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String reservationId) throws IOException {
        boolean deleted = reservationService.deleteReservation(reservationId);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Returns 204 on successful deletion
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }
}
