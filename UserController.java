package com.example.propertymanagement.controller;

import com.example.propertymanagement.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String FILE_PATH;

    public UserController() {
        // Get the absolute path to the data directory
        String projectRoot = System.getProperty("user.dir");
        FILE_PATH = Paths.get(projectRoot, "data", "users.txt").toString();
        createDataDirectory();
    }

    private void createDataDirectory() {
        try {
            // Create data directory
            Path dataDir = Paths.get(System.getProperty("user.dir"), "data");
            Files.createDirectories(dataDir);
            
            // Create users.txt if it doesn't exist
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return new ArrayList<>();
            }
            return Files.lines(Paths.get(FILE_PATH))
                .filter(line -> !line.trim().isEmpty())
                .map(line -> {
                    try {
                        return mapper.readValue(line, User.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return ResponseEntity.notFound().build();
            }
            return Files.lines(Paths.get(FILE_PATH))
                .filter(line -> !line.trim().isEmpty())
                .map(line -> {
                    try {
                        return mapper.readValue(line, User.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(u -> u != null && id.equals(u.getId()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            // Generate a unique ID if not set
            if (user.getId() == null || user.getId().isEmpty()) {
                user.setId(UUID.randomUUID().toString());
            }

            // Check if user with same email already exists
            List<User> existingUsers = getAllUsers();
            if (existingUsers.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
                return ResponseEntity.badRequest().build();
            }

            // Append the new user to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                String userJson = mapper.writeValueAsString(user);
                writer.write(userJson);
                writer.newLine();
                writer.flush(); // Ensure data is written immediately
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return ResponseEntity.notFound().build();
            }

            List<User> users = getAllUsers();
            boolean updated = false;
            for (int i = 0; i < users.size(); i++) {
                if (id.equals(users.get(i).getId())) {
                    user.setId(id); // Ensure ID stays the same
                    users.set(i, user);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                return ResponseEntity.notFound().build();
            }

            // Write all users back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
                for (User u : users) {
                    String userJson = mapper.writeValueAsString(u);
                    writer.write(userJson);
                    writer.newLine();
                }
                writer.flush(); // Ensure data is written immediately
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return ResponseEntity.notFound().build();
            }

            List<User> users = getAllUsers();
            boolean removed = users.removeIf(u -> id.equals(u.getId()));

            if (!removed) {
                return ResponseEntity.notFound().build();
            }

            // Write remaining users back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
                for (User u : users) {
                    String userJson = mapper.writeValueAsString(u);
                    writer.write(userJson);
                    writer.newLine();
                }
                writer.flush(); // Ensure data is written immediately
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
} 