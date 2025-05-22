package com.realestate.model;

public abstract class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    protected String role;

    // Default constructor
    public User() {
    }

    // Constructor
    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }



    // Getters and Setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public abstract String getRole(); // Abstract method

    public void setRole(String role) {
        this.role = role;
    }

    // Convert user to pipe-delimited string for file storage
    public String toDataString() {
        return userId + "|" + name + "|" + email + "|" + password + "|" + role;
    }

    // Parse user from pipe-delimited string read from file
    public static User fromDataString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length == 5) {
            User user = new User() {
                @Override
                public String getRole() {
                    return this.role;
                }
            };
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
