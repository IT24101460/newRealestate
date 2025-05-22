package com.realestate.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class FileHandler {
    private static final String USERS_FILE = "src/main/resources/data/users.txt";
    private static final String PROPERTIES_FILE = "src/main/resources/data/properties.txt";
    private static final String REVIEWS_FILE = "src/main/resources/data/reviews.txt";
    private static final String RESERVATIONS_FILE = "src/main/resources/data/reservations.txt";

    // Generic method to create file if it doesn't exist
    private static void ensureFileExists(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }
    }

    // Users I/O
    public static List<String> readUsers() throws IOException {
        Path path = Paths.get(USERS_FILE);
        try {
            ensureFileExists(path);
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Error reading users file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void writeUser(String userData) throws IOException {
        Path path = Paths.get(USERS_FILE);
        try {
            ensureFileExists(path);
            Files.writeString(path, userData + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing user to file: " + e.getMessage());
            throw e;
        }
    }

    public static void updateUsers(List<String> usersData) throws IOException {
        Path path = Paths.get(USERS_FILE);
        try {
            ensureFileExists(path);
            Files.write(path, usersData, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error updating users file: " + e.getMessage());
            throw e;
        }
    }

    // Properties I/O
    public static List<String> readProperties() throws IOException {
        Path path = Paths.get(PROPERTIES_FILE);
        try {
            ensureFileExists(path);
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void writeProperty(String propertyData) throws IOException {
        Path path = Paths.get(PROPERTIES_FILE);
        try {
            ensureFileExists(path);
            Files.writeString(path, propertyData + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing property to file: " + e.getMessage());
            throw e;
        }
    }

    public static void updateProperties(List<String> propertiesData) throws IOException {
        Path path = Paths.get(PROPERTIES_FILE);
        try {
            ensureFileExists(path);
            Files.write(path, propertiesData, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error updating properties file: " + e.getMessage());
            throw e;
        }
    }

    // Reviews I/O
    public static List<String> readReviews() throws IOException {
        Path path = Paths.get(REVIEWS_FILE);
        try {
            ensureFileExists(path);
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Error reading reviews file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void writeReview(String reviewData) throws IOException {
        Path path = Paths.get(REVIEWS_FILE);
        try {
            ensureFileExists(path);
            Files.writeString(path, reviewData + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing review to file: " + e.getMessage());
            throw e;
        }
    }

    public static void updateReviews(List<String> reviewsData) throws IOException {
        Path path = Paths.get(REVIEWS_FILE);
        try {
            ensureFileExists(path);
            Files.write(path, reviewsData, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error updating reviews file: " + e.getMessage());
            throw e;
        }
    }

    // Reservations I/O
    public static List<String> readReservations() throws IOException {
        Path path = Paths.get(RESERVATIONS_FILE);
        try {
            ensureFileExists(path);
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Error reading reservations file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void writeReservation(String reservationData) throws IOException {
        Path path = Paths.get(RESERVATIONS_FILE);
        try {
            ensureFileExists(path);
            Files.writeString(path, reservationData + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing reservation to file: " + e.getMessage());
            throw e;
        }
    }

    public static void updateReservations(List<String> reservationsData) throws IOException {
        Path path = Paths.get(RESERVATIONS_FILE);
        try {
            ensureFileExists(path);
            Files.write(path, reservationsData, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error updating reservations file: " + e.getMessage());
            throw e;
        }
    }
}
