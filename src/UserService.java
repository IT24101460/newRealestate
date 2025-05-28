package com.realestate.service;

import com.realestate.model.User;
import com.realestate.util.FileHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    public User addUser(String name, String email, String password, String role) throws IOException {
        String userIdPrefix = role.equalsIgnoreCase("BUYER") ? "B" : role.equalsIgnoreCase("SELLER") ? "S" : "A";
        String userId = userIdPrefix + UUID.randomUUID().toString().substring(0, 7);
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User existingUser = User.fromDataString(data);
                if (existingUser != null && existingUser.getEmail().equals(email)) {
                    throw new IOException("Email already registered. Please use a different email.");
                }
            }
        }
        User user = new User(userId, name, email, password, role.toUpperCase());
        FileHandler.writeUser(user.toDataString());
        return user;
    }

    public List<User> getAllUsers() throws IOException {
        List<String> userData = FileHandler.readUsers();
        List<User> users = new ArrayList<>();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    public User getUserById(String userId) throws IOException {
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(userId)) {
                    return user;
                }
            }
        }
        return null;
    }

    public User loginUser(String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    public User updateUser(String userId, String name, String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers();
        List<String> updatedData = new ArrayList<>();
        User updatedUser = null;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(userId)) {
                    updatedUser = new User(userId, name, email, password, user.getRole());
                    updatedData.add(updatedUser.toDataString());
                } else {
                    updatedData.add(data);
                }
            }
        }
        if (updatedUser != null) {
            FileHandler.updateUsers(updatedData);
        }
        return updatedUser;
    }

    public boolean deleteUser(String userId) throws IOException {
        List<String> userData = FileHandler.readUsers();
        List<String> updatedData = new ArrayList<>();
        boolean deleted = false;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user == null || !user.getUserId().equals(userId)) {
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
