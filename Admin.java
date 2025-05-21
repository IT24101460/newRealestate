package com.example.propertymanagement.model;



public class Admin extends User {   //extends from user(parent class)
    public Admin() {
        super();     //pass data to parent attributes
    }

    public Admin(Long id, String username, String password, String email) {
        super();
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
