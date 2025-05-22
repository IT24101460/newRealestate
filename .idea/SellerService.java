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

    //Add a new seller to user
    public User addSeller(String name, String email, String password) throws IOException {
        String sellerId = "S" + UUID.randomUUID().toString().substring(0, 7); 
        User seller = new User(sellerId, name, email, password, "SELLER"); //new Seller object
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User existingUser = User.fromDataString(data);
                if (existingUser != null && existingUser.getEmail().equals(email)) {
                    throw new IOException("Email already registered. Please use a different email.");
                }
            }
        }
        FileHandler.writeUser(seller.toDataString()); 
        return seller; // Return to the seller
    }

    // Retrieves all sellers from user
    public List<User> getAllSellers() throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read the lines from user
        List<User> sellers = new ArrayList<>();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getRole().equalsIgnoreCase("SELLER")) {
                    sellers.add(user); 
                }
            }
        }
        return sellers; // Return all sellers
    }

    // Retrieves a seller by their ID
    public User getSellerById(String sellerId) throws IOException {
        List<String> userData = FileHandler.readUsers(); 
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data); 
                if (user != null && user.getUserId().equals(sellerId) && user.getRole().equalsIgnoreCase("SELLER")) {
                    return user; // Return to the matching seller
                }
            }
        }
        return null; 
    }

    // Retrieves a seller for login
    public User loginSeller(String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers(); 
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data); 
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password) && user.getRole().equalsIgnoreCase("SELLER")) {
                    return user; 
                }
            }
        }
        return null; // Return null if no match found
    }

    //Updates an existing seller profile in user
    public User updateSeller(String sellerId, String name, String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers(); 
        List<String> updatedData = new ArrayList<>();
        User updatedSeller = null;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(sellerId) && user.getRole().equalsIgnoreCase("SELLER")) {
                    updatedSeller = new User(sellerId, name, email, password, "SELLER");
                    updatedData.add(updatedSeller.toDataString()); 
                } else {
                    updatedData.add(data); 
                }
            }
        }
        if (updatedSeller != null) {
            FileHandler.updateUsers(updatedData); 
        }
        return updatedSeller; // Return updated seller 
    }

    // Removes a seller from user
    public boolean deleteSeller(String sellerId) throws IOException {
        List<String> userData = FileHandler.readUsers(); 
        List<String> updatedData = new ArrayList<>();
        boolean deleted = false;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (!user.getUserId().equals(sellerId) || !user.getRole().equalsIgnoreCase("SELLER")) {
                    updatedData.add(data); 
                } else {
                    deleted = true; 
                }
            }
        }
        if (deleted) {
            FileHandler.updateUsers(updatedData); 
        }
        return deleted; // Return true if deletion has happend
    }
}
