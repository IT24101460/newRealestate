package com.realestate.project_oop.user;

public abstract class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role; // Field to support setRole

    public User() {
    }

    public User(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getRole(); // Abstract method

    public void setRole(String role) {
        this.role = role; // Allows mutable roles
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', role='" + getRole() + "'}";
    }
}