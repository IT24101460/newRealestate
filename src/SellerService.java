package com.realestate.service;

import com.realestate.model.User;
import com.realestate.util.FileHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SellerService {

    // CREATE Operation: Adds a new seller to users.txt
    public User addSeller(String name, String email, String password) throws IOException {
        String sellerId = "S" + UUID.randomUUID().toString().substring(0, 7); // Unique ID with seller prefix
        // Check if email already exists to prevent duplicates
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User existingUser = User.fromDataString(data);
                if (existingUser != null && existingUser.getEmail().equals(email)) {
                    throw new IOException("Email already registered. Please use a different email.");
                }
            }
        }
        User seller = new User(sellerId, name, email, password, "SELLER"); // Create new Seller object
        FileHandler.writeUser(seller.toDataString()); // Write to users.txt using FileHandler
        return seller; // Return the created seller
    }

    // READ Operation: Retrieves all sellers from users.txt
    public List<User> getAllSellers() throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all lines from users.txt
        List<User> sellers = new ArrayList<>();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data); // Parse each line into a User object
                if (user != null && user.getRole().equalsIgnoreCase("SELLER")) {
                    sellers.add(user); // Add only sellers to the list
                }
            }
        }
        return sellers; // Return the list of all sellers
    }

    // READ Operation: Retrieves a single seller by their ID
    public User getSellerById(String sellerId) throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all lines from users.txt
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data); // Parse line to User object
                if (user != null && user.getUserId().equals(sellerId) && user.getRole().equalsIgnoreCase("SELLER")) {
                    return user; // Return matching seller
                }
            }
        }
        return null; // Return null if not found
    }

    // READ Operation: Retrieves a seller by email and password for login
    public User loginSeller(String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all lines from users.txt
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data); // Parse line to User object
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password) && user.getRole().equalsIgnoreCase("SELLER")) {
                    return user; // Return matching seller if credentials match
                }
            }
        }
        return null; // Return null if no match found
    }

    // UPDATE Operation: Updates an existing seller's profile in users.txt
    public User updateSeller(String sellerId, String name, String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all current users from file
        List<String> updatedData = new ArrayList<>();
        User updatedSeller = null;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(sellerId) && user.getRole().equalsIgnoreCase("SELLER")) {
                    // Update the seller with new values, retaining original role
                    updatedSeller = new User(sellerId, name, email, password, "SELLER");
                    updatedData.add(updatedSeller.toDataString()); // Add updated data to list
                } else {
                    updatedData.add(data); // Keep unchanged data
                }
            }
        }
        if (updatedSeller != null) {
            FileHandler.updateUsers(updatedData); // Write updated list back to users.txt
        }
        return updatedSeller; // Return updated seller or null if not found
    }

    // DELETE Operation: Removes a seller from users.txt
    public boolean deleteSeller(String sellerId) throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all current users from file
        List<String> updatedData = new ArrayList<>();
        boolean deleted = false;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user == null || !user.getUserId().equals(sellerId) || !user.getRole().equalsIgnoreCase("SELLER")) {
                    updatedData.add(data); // Keep non-matching users or non-sellers
                } else {
                    deleted = true; // Mark as deleted if found
                }
            }
        }
        if (deleted) {
            FileHandler.updateUsers(updatedData); // Write updated list back to users.txt
        }
        return deleted; // Return true if deletion occurred, false if not found
    }
}
