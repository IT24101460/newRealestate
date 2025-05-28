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

    public User addBuyer(String name, String email, String password) throws IOException {
        String buyerId = "B" + UUID.randomUUID().toString().substring(0, 7);
        User buyer = new User(buyerId, name, email, password, "BUYER");
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User existingUser = User.fromDataString(data);
                if (existingUser != null && existingUser.getEmail().equals(email)) {
                    throw new IOException("Email already registered. Please use a different email.");
                }
            }
        }
        FileHandler.writeUser(buyer.toDataString());
        return buyer;
    }

    public List<User> getAllBuyers() throws IOException {
        List<String> userData = FileHandler.readUsers();
        List<User> buyers = new ArrayList<>();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getRole().equalsIgnoreCase("BUYER")) {
                    buyers.add(user);
                }
            }
        }
        return buyers;
    }

    public User getBuyerById(String buyerId) throws IOException {
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(buyerId) && user.getRole().equalsIgnoreCase("BUYER")) {
                    return user;
                }
            }
        }
        return null;
    }

    public User loginBuyer(String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password) && user.getRole().equalsIgnoreCase("BUYER")) {
                    return user;
                }
            }
        }
        return null;
    }

    public User updateBuyer(String buyerId, String name, String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers();
        List<String> updatedData = new ArrayList<>();
        User updatedBuyer = null;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(buyerId) && user.getRole().equalsIgnoreCase("BUYER")) {
                    updatedBuyer = new User(buyerId, name, email, password, "BUYER");
                    updatedData.add(updatedBuyer.toDataString());
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
