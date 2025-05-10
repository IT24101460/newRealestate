package RealEstateProperty;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private List<Reservation> reservations;
    private static final String RESERVATION_FILE = "reservations.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ReservationManager() {
        this.reservations = loadReservations();
    }

    // CRUD Operations
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        saveReservations();
    }

    public Reservation getReservation(int reservationId) {
        return reservations.stream()
                .filter(r -> r.getReservationId() == reservationId)
                .findFirst()
                .orElse(null);
    }

    public void updateReservation(Reservation reservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationId() == reservation.getReservationId()) {
                reservations.set(i, reservation);
                break;
            }
        }
        saveReservations();
    }

    public void deleteReservation(int reservationId) {
        reservations.removeIf(r -> r.getReservationId() == reservationId);
        saveReservations();
    }

    // File Operations
    private List<Reservation> loadReservations() {
        List<Reservation> loadedReservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Reservation reservation = new Reservation(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]),
                        LocalDateTime.parse(parts[3], formatter)
                );
                reservation.setStatus(parts[4]);
                loadedReservations.add(reservation);
            }
        } catch (IOException e) {
            System.err.println("Error loading reservations: " + e.getMessage());
        }
        return loadedReservations;
    }

    private void saveReservations() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESERVATION_FILE))) {
            for (Reservation reservation : reservations) {
                writer.write(String.format("%d,%d,%d,%s,%s%n",
                        reservation.getReservationId(),
                        reservation.getPropertyId(),
                        reservation.getBuyerId(),
                        reservation.getReservationDate().format(formatter),
                        reservation.getStatus()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving reservations: " + e.getMessage());
        }
    }

    // Utility methods
    public List<Reservation> getReservationsByBuyer(int buyerId) {
        return reservations.stream()
                .filter(r -> r.getBuyerId() == buyerId)
                .toList();
    }

    public List<Reservation> getReservationsByProperty(int propertyId) {
        return reservations.stream()
                .filter(r -> r.getPropertyId() == propertyId)
                .toList();
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
} 