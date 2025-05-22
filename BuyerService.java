package com.realestate.service;

import com.realestate.model.User;
import com.realestate.util.FileHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BuyerService {

    // CREATE Operation: Adds a new buyer to users.txt
    public User addBuyer(String name, String email, String password) throws IOException {
        String buyerId = "B" + UUID.randomUUID().toString().substring(0, 7); // Unique ID with buyer prefix
        User buyer = new User(buyerId, name, email, password, "BUYER"); // Create new Buyer object
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
        FileHandler.writeUser(buyer.toDataString()); // Write to users.txt using FileHandler
        return buyer; // Return the created buyer
    }

    // READ Operation: Retrieves all buyers from users.txt
    public List<User> getAllBuyers() throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all lines from users.txt
        List<User> buyers = new ArrayList<>();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getRole().equalsIgnoreCase("BUYER")) {
                    buyers.add(user); // Add only buyers to the list
                }
            }
        }
        return buyers; // Return the list of all buyers
    }


    public User getBuyerById(String buyerId) throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all lines from users.txt
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data); // Parse line to User object
                if (user != null && user.getUserId().equals(buyerId) && user.getRole().equalsIgnoreCase("BUYER")) {
                    return user; // Return matching buyer
                }
            }
        }
        return null; // Return null if not found or not a buyer
    }

    // READ Operation: Retrieves a buyer by email and password for login
    public User loginBuyer(String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all lines from users.txt
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data); // Parse line to User object
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password) && user.getRole().equalsIgnoreCase("BUYER")) {
                    return user; // Return matching buyer if credentials match
                }
            }
        }
        return null; // Return null if no match found or not a buyer
    }


    public User updateBuyer(String buyerId, String name, String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all current users from file
        List<String> updatedData = new ArrayList<>();
        User updatedBuyer = null;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(buyerId) && user.getRole().equalsIgnoreCase("BUYER")) {

                    updatedBuyer = new User(buyerId, name, email, password, "BUYER");
                    updatedData.add(updatedBuyer.toDataString()); // Add updated data to list
                } else {
                    updatedData.add(data);
                }
            }
        }
        if (updatedBuyer != null) {
            FileHandler.updateUsers(updatedData);
        }
        return updatedBuyer;
    }


    public boolean deleteBuyer(String buyerId) throws IOException {
        List<String> userData = FileHandler.readUsers();
        List<String> updatedData = new ArrayList<>();
        boolean deleted = false;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (!user.getUserId().equals(buyerId) || !user.getRole().equalsIgnoreCase("BUYER")) {
                    updatedData.add(data);
                } else {
                    deleted = true;
                }
            }
        }
        if (deleted) {
            FileHandler.updateUsers(updatedData);
        }
        return deleted;
    }
}
