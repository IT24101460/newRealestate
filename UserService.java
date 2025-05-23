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

    //  Adds a new user to users.txt
    public User addUser(String name, String email, String password, String role) throws IOException {
        String userIdPrefix = role.equalsIgnoreCase("BUYER") ? "B" : role.equalsIgnoreCase("SELLER") ? "S" : "A";
        String userId = userIdPrefix + UUID.randomUUID().toString().substring(0, 7);
        // Check if email already exists
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User existingUser = User.fromDataString(data);
                if (existingUser != null && existingUser.getEmail().equals(email)) {
                    throw new IOException("Email already registered. Please use a different email.");
                }
            }
        }
        User user = new User(userId, name, email, password, role.toUpperCase()) {
            @Override
            public String getRole() {
                return this.role;
            }
        }; // Create new User object
        FileHandler.writeUser(user.toDataString()); // Write to users.txt using FileHandler
        return user;
    }

    // READ all users from users.txt
    public List<User> getAllUsers() throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all lines from users.txt
        List<User> users = new ArrayList<>();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null) {
                    users.add(user);
                }
            }
        }
        return users; // Return all users
    }

    // READ user by their ID
    public User getUserById(String userId) throws IOException {
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(userId)) {
                    return user; // Return user
                }
            }
        }
        return null; // Return null if not found
    }

    // READ a user by email and password for login
    public User loginUser(String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers();
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return user; // Return matching user if credentials match
                }
            }
        }
        return null; // Return null if no match found
    }

    // UPDATE user's profile in users.txt
    public User updateUser(String userId, String name, String email, String password) throws IOException {
        List<String> userData = FileHandler.readUsers();
        List<String> updatedData = new ArrayList<>();
        User updatedUser = null;
        for (String data : userData) {
            if (!data.isEmpty()) {
                User user = User.fromDataString(data);
                if (user != null && user.getUserId().equals(userId)) {
                    updatedUser = new User(userId, name, email, password, user.getRole()) {
                        @Override
                        public String getRole() {
                            return this.role;
                        }
                    };
                    updatedData.add(updatedUser.toDataString()); // Add updated data
                } else {
                    updatedData.add(data); // Keep unchanged data
                }
            }
        }
        if (updatedUser != null) {
            FileHandler.updateUsers(updatedData); // Write updated list back to users.txt
        }
        return updatedUser; // Return updated user
    }

    // DELETE a user from users.txt
    public boolean deleteUser(String userId) throws IOException {
        List<String> userData = FileHandler.readUsers(); // Read all current users
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
