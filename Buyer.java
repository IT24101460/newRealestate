package com.example.propertymanagement.model;

public class Buyer extends User {

    // Constructors
    public Buyer() {
        super();
        this.role = "BUYER";
    }

    public Buyer(String id, String username, String email) {
        super(id, username, email);
        this.role = "BUYER";
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
