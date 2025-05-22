package com.realestate.model;

public class Admin extends User {
    // Default constructor
    public Admin() {
        super();
        setRole("ADMIN");
    }
    // Constructor
    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password, "ADMIN");
    }
    @Override
    public String getRole() {
        return "ADMIN"; // Polymorphic implementation
    }



}
