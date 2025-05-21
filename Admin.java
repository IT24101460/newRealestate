package com.realestate.project_oop.user;

public class Admin extends User {
    public Admin() {
        super();
        setRole("ADMIN");
    }

    public Admin(Long id, String username, String password, String email) {
        super(id, username, password, email);
        setRole("ADMIN");
    }

    @Override
    public String getRole() {
        return "ADMIN"; // Polymorphic implementation
    }
}