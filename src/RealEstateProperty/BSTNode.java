package com.realestate.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    // File paths for data storage
    private static final String USERS_FILE = "src/main/resources/data/users.txt";
    private static final String PROPERTIES_FILE = "src/main/resources/data/properties.txt";
    private static final String REVIEWS_FILE = "src/main/resources/data/reviews.txt";
    private static final String RESERVATIONS_FILE = "src/main/resources/data/reservations.txt";

    // Ensure the data directory exists
    static {
        try {
            Files.createDirectories(Paths.get("src/main/resources/data"));
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }

    // Methods for Users
    public static List<String> readUsers() throws IOException {
        return readFile(USERS_FILE);
    }

    public static void writeUser(String userData) throws IOException {
        writeToFile(USERS_FILE, userData);
    }

    public static void updateUsers(List<String> usersData) throws IOException {
        writeAllToFile(USERS_FILE, usersData);
    }

    // Methods for Properties
    public static List<String> readProperties() throws IOException {
        return readFile(PROPERTIES_FILE);
    }

    public static void writeProperty(String propertyData) throws IOException {
        writeToFile(PROPERTIES_FILE, propertyData);
    }

    public static void updateProperties(List<String> propertiesData) throws IOException {
        writeAllToFile(PROPERTIES_FILE, propertiesData);
    }

    // Methods for Reviews
    public static List<String> readReviews() throws IOException {
        return readFile(REVIEWS_FILE);
    }

    public static void writeReview(String reviewData) throws IOException {
        writeToFile(REVIEWS_FILE, reviewData);
    }

    public static void updateReviews(List<String> reviewsData) throws IOException {
        writeAllToFile(REVIEWS_FILE, reviewsData);
    }

    // Methods for Reservations
    public static List<String> readReservations() throws IOException {
        return readFile(RESERVATIONS_FILE);
    }

    public static void writeReservation(String reservationData) throws IOException {
        writeToFile(RESERVATIONS_FILE, reservationData);
    }

    public static void updateReservations(List<String> reservationsData) throws IOException {
        writeAllToFile(RESERVATIONS_FILE, reservationsData);
    }

    // Generic method to read all lines from a file
    private static List<String> readFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
        return Files.readAllLines(Paths.get(filePath));
    }

    // Generic method to append a single line to a file
    private static void writeToFile(String filePath, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.newLine(); // Add newline after the data
        }
    }

    // Generic method to overwrite a file with a list of data
    private static void writeAllToFile(String filePath, List<String> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine(); // Add newline after each line
            }
        }
    }
}
