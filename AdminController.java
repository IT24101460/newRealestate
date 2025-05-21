package com.realestate.project_oop.controller;

import com.realestate.project_oop.user.Admin;
import com.realestate.project_oop.user.Buyer;
import com.realestate.project_oop.user.Seller;
import com.realestate.project_oop.user.User;
import com.realestate.project_oop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("error", null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            if (userService.authenticate(username, password)) {
                return "redirect:/dashboard";
            } else {
                model.addAttribute("error", "Invalid credentials");
            }
        } catch (IOException e) {
            System.err.println("Error during login: " + e.getMessage());
            model.addAttribute("error", "Error during login: " + e.getMessage());
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        try {
            model.addAttribute("users", userService.getUsers());
        } catch (IOException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            model.addAttribute("error", "Error retrieving users: " + e.getMessage());
        }
        return "dashboard";
    }

    @GetMapping("/user")
    public String getAllUsers(Model model, @RequestParam(required = false) Long id) {
        try {
            if (id != null) {
                User user = userService.getUserById(id);
                if (user == null) {
                    model.addAttribute("error", "User not found");
                    return "redirect:/user";
                }
                model.addAttribute("user", user);
                return "user/view-user";
            }
            model.addAttribute("users", userService.getUsers());
            return "user/all-users";
        } catch (IOException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            model.addAttribute("error", "Error retrieving users: " + e.getMessage());
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/user/delete")
    public String deleteUserById(@RequestParam Long id, Model model) {
        try {
            userService.deleteUser(id);
        } catch (IOException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            model.addAttribute("error", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/dashboard"; // Changed from /user to /dashboard
    }

    @GetMapping("/user/edit")
    public String editUser(Model model, @RequestParam(required = false) Long id) {
        try {
            if (id != null) {
                User user = userService.getUserById(id);
                if (user == null) {
                    model.addAttribute("error", "User not found");
                    return "redirect:/user";
                }
                model.addAttribute("user", user);
            }
            return "user/edit-user";
        } catch (IOException e) {
            System.err.println("Error retrieving user for edit: " + e.getMessage());
            model.addAttribute("error", "Error retrieving user for edit: " + e.getMessage());
            return "redirect:/user";
        }
    }

    @PostMapping("/user/update")
    public String updateUser(
            @RequestParam(required = false) Long id,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String role,
            Model model) {
        try {
            User user;
            if ("ADMIN".equals(role)) {
                user = new Admin();
            } else if ("SELLER".equals(role)) {
                user = new Seller();
            } else {
                user = new Buyer();
            }
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role); // Set role dynamically
            if (id == null) {
                userService.saveUser(user);
            } else {
                user.setId(id);
                userService.updateUser(user);
            }
        } catch (IOException e) {
            System.err.println("Error saving/updating user: " + e.getMessage());
            model.addAttribute("error", "Error saving/updating user: " + e.getMessage());
        }
        return "redirect:/dashboard"; // Redirect to dashboard after success or error
    }
}