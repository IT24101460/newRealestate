package RealEstateProperty;

import java.util.ArrayList;
import java.util.List;

public class Buyer extends User {
    private List<Integer> reservationIds; // List of reservation IDs made by this buyer
    private List<String> preferences; // RealEstateProperty.Property preferences (type, location, price range, etc.)

    public Buyer(int userId, String username, String password, String name, String email, String phone) {
        super(userId, username, password, "BUYER", name, email, phone);
        this.reservationIds = new ArrayList<>();
        this.preferences = new ArrayList<>();
    }

    // RealEstateProperty.Reservation management
    public void addReservation(int reservationId) {
        reservationIds.add(reservationId);
    }

    public void removeReservation(int reservationId) {
        reservationIds.remove(Integer.valueOf(reservationId));
    }

    public List<Integer> getReservationIds() {
        return new ArrayList<>(reservationIds);
    }

    public void setReservationIds(List<Integer> reservationIds) {
        this.reservationIds = new ArrayList<>(reservationIds);
    }

    // Preferences management
    public void addPreference(String preference) {
        preferences.add(preference);
    }

    public void removePreference(String preference) {
        preferences.remove(preference);
    }

    public List<String> getPreferences() {
        return new ArrayList<>(preferences);
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = new ArrayList<>(preferences);
    }

    @Override
    public String toString() {
        return "RealEstateProperty.Buyer{" +
                "userId=" + getUserId() +
                ", username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", reservationIds=" + reservationIds +
                ", preferences=" + preferences +
                '}';
    }
} 