package com.realestate.project_oop.service;

import com.realestate.project_oop.user.Admin;
import com.realestate.project_oop.user.Buyer;
import com.realestate.project_oop.user.Seller;
import com.realestate.project_oop.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private static List<User> users;
    private static long userId = 0;
    private final String FILE_NAME;

    public UserService(@Value("${user.data.file}") String fileName) {
        this.FILE_NAME = fileName;
        // Ensure the directory exists
        File file = new File(fileName);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    public List<User> getUsers() throws IOException {
        if (users != null) return users;
        users = Collections.synchronizedList(new ArrayList<>());
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            // Create an empty file with header if it doesn't exist
            save();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length != 5) continue;
                long id = Long.parseLong(parts[0]);
                String username = parts[1];
                String password = parts[2];
                String email = parts[3];
                String role = parts[4];
                User user;
                if ("ADMIN".equals(role)) {
                    user = new Admin(id, username, password, email);
                } else if ("SELLER".equals(role)) {
                    user = new Seller(id, username, password, email);
                } else {
                    user = new Buyer(id, username, password, email);
                }
                user.setRole(role);
                users.add(user);
                if (userId < id) userId = id;
            }
        } catch (IOException e) {
            throw new IOException("Error reading " + FILE_NAME + ": " + e.getMessage());
        }
        userId++;
        return users;
    }

    public User getUserById(Long id) throws IOException {
        if (users == null) getUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public void updateUser(User user) throws IOException {
        if (users == null) getUsers();
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                u.setUsername(user.getUsername());
                u.setPassword(user.getPassword());
                u.setEmail(user.getEmail());
                u.setRole(user.getRole());
                save();
                return;
            }
        }
    }

    public void deleteUser(Long id) throws IOException {
        if (users == null) getUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                save();
                return;
            }
        }
    }

    public void saveUser(User user) throws IOException {
        if (users == null) getUsers();
        if (user.getId() == null) {
            user.setId(userId++);
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("SELLER");
            }
            User newUser;
            if ("ADMIN".equals(user.getRole())) {
                newUser = new Admin();
            } else if ("SELLER".equals(user.getRole())) {
                newUser = new Seller();
            } else {
                newUser = new Buyer();
            }
            newUser.setId(user.getId());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setEmail(user.getEmail());
            newUser.setRole(user.getRole());
            users.add(newUser);
        } else {
            updateUser(user);
        }
        save();
    }

    public boolean authenticate(String username, String password) throws IOException {
        if (users == null) getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && "ADMIN".equals(user.getRole())) {
                return true;
            }
        }
        return false;
    }

    private synchronized void save() throws IOException {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            writer.println("id,username,password,email,role");
            for (User user : users) {
                writer.println(user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getEmail() + "," + user.getRole());
            }
        } catch (IOException e) {
            throw new IOException("Error writing " + FILE_NAME + ": " + e.getMessage());
        }
    }
}