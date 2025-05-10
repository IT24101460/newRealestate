package RealEstateProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private static final String USER_FILE = "users.txt";

    public UserManager() {
        this.users = loadUsers();
    }

    // CRUD Operations
    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public User getUser(int userId) {
        return users.stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == user.getUserId()) {
                users.set(i, user);
                break;
            }
        }
        saveUsers();
    }

    public void deleteUser(int userId) {
        users.removeIf(u -> u.getUserId() == userId);
        saveUsers();
    }

    // Authentication
    public User authenticate(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // File Operations
    private List<User> loadUsers() {
        List<User> loadedUsers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                User user = new User(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5],
                        parts[6]
                );
                loadedUsers.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return loadedUsers;
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s%n",
                        user.getUserId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Utility methods
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean usernameExists(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }
}