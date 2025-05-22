package com.realestate.controller;

import com.realestate.model.User;
import com.realestate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role) throws IOException {
        try {
            User user = userService.addUser(name, email, password, role);
            return ResponseEntity.ok(user); // Return created user
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 if email already exists
        }
    }

    // READ Operation
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() throws IOException {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // Returns all users
    }

    // READ Operation by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) throws IOException {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user); // Returns the user
        }
        return ResponseEntity.notFound().build(); // Returns not found
    }

    // user for login
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        User user = userService.loginUser(email, password);
        if (user != null) {
            return ResponseEntity.ok(user); // Return the user
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE Operation
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable String userId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        User user = userService.updateUser(userId, name, email, password);
        if (user != null) {
            return ResponseEntity.ok(user); // Returns updated user
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE Operation
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) throws IOException {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
