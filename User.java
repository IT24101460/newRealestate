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

    //method to store the user details in a tube manner for easy access,user object to string
    public String toDataString() {
        return userId + "|" + name + "|" + email + "|" + password + "|" + role;
    }

   //the saved string is used to make a user object
    public static User fromDataString(String data) {
        String[] parts = data.split("\\|");   //like an array type with index
        if (parts.length == 5) {
            User user = new User() {
                @Override
                public String getRole() {  //temporary class created and return role
                    return this.role;
                }
            };
            user.setUserId(parts[0]);        //set attributes to index value
            user.setName(parts[1]);
            user.setEmail(parts[2]);
            user.setPassword(parts[3]);
            user.setRole(parts[4]);
            return user;
        }
        return null; // Return null
    }
//to get the user data in a readable way
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
