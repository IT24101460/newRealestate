package com.realestate.model;

public class User {
    private String userId; // Unique identifier for the user
    private String name; // User's full name
    private String email; // User's email for contact and login
    private String password; // User's password (in production, should be hashed)
    private String role; // User's role (e.g., "BUYER", "SELLER", "ADMIN")

    // Constructor for creating a new user
    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Default constructor for parsing from file
    public User() {
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Convert user to pipe-delimited string for file storage
    public String toDataString() {
        return userId + "|" + name + "|" + email + "|" + password + "|" + role;
    }

    // Parse user from pipe-delimited string read from file
    public static User fromDataString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length == 5) {
            User user = new User();
            user.setUserId(parts[0]);
            user.setName(parts[1]);
            user.setEmail(parts[2]);
            user.setPassword(parts[3]);
            user.setRole(parts[4]);
            return user;
        }
        return null; // Return null if data format is incorrect
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
