package com.realestate.model;

public class Seller extends User {
    // Constructor for new seller
    public Seller(String userId, String name, String email, String password) {
        super(userId, name, email, password, "SELLER");
    }
    
    public Seller() {
        super();
        setRole("SELLER");
    }
}
