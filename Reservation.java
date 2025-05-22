package com.realestate.model;

public class Reservation {
    private String reservationId;
    private String propertyId;
    private String buyerId;
    private String reservationDate;
    private String status; 

    // Constructor for creating a new reservation
    public Reservation(String reservationId, String propertyId, String buyerId, String reservationDate, String status) {
        this.reservationId = reservationId;
        this.propertyId = propertyId;
        this.buyerId = buyerId;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    // Default constructor for parsing from file
    public Reservation() {
    }

    // Getters and Setters
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }
    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }
    public String getReservationDate() { return reservationDate; }
    public void setReservationDate(String reservationDate) { this.reservationDate = reservationDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Convert reservation to pipe-delimited string for file storage
    public String toDataString() {
        return reservationId + "|" + propertyId + "|" + buyerId + "|" + reservationDate + "|" + status;
    }

    // Parse reservation from pipe-delimited string read from file
    public static Reservation fromDataString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length == 5) {
            Reservation reservation = new Reservation();
            reservation.setReservationId(parts[0]);
            reservation.setPropertyId(parts[1]);
            reservation.setBuyerId(parts[2]);
            reservation.setReservationDate(parts[3]);
            reservation.setStatus(parts[4]);
            return reservation;
        }
        return null; // Return null if data format is incorrect
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
