package RealEstateProperty;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private int propertyId;
    private int buyerId;
    private LocalDateTime reservationDate;
    private String status; // "PENDING", "CONFIRMED", "CANCELLED"

    public Reservation(int reservationId, int propertyId, int buyerId, LocalDateTime reservationDate) {
        this.reservationId = reservationId;
        this.propertyId = propertyId;
        this.buyerId = buyerId;
        this.reservationDate = reservationDate;
        this.status = "PENDING";
    }

    // Getters and Setters
    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public int getPropertyId() { return propertyId; }
    public void setPropertyId(int propertyId) { this.propertyId = propertyId; }

    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }

    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "RealEstateProperty.Reservation{" +
                "reservationId=" + reservationId +
                ", propertyId=" + propertyId +
                ", buyerId=" + buyerId +
                ", reservationDate=" + reservationDate +
                ", status='" + status + '\'' +
                '}';
    }
} 