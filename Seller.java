package com.realestate.project_oop.user;

public class Seller extends User {
    public Seller() {
        super();
        setRole("SELLER");
    }

    public Seller(Long id, String username, String password, String email) {
        super(id, username, password, email);
        setRole("SELLER");
    }

    @Override
    public String getRole() {
        return "SELLER"; // Polymorphic implementation
    }
}