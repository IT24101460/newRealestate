package com.realestate.model;

public class Buyer extends User {

    public Buyer(String userId, String name, String email, String password) {
        super(userId, name, email, password, "BUYER");
    }

    
    public Buyer() {
        super();
        setRole("BUYER");
    }
}
