package com.realestate.project_oop.user;

public class Buyer extends User {
    public Buyer() {
        super();
        setRole("BUYER");
    }

    public Buyer(Long id, String username, String password, String email) {
        super(id, username, password, email);
        setRole("BUYER");
    }

    @Override
    public String getRole() {
        return "BUYER"; // Polymorphic implementation
    }
}