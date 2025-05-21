package com.example.propertymanagement.controller;
import com.example.propertymanagement.model.Admin;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private List<Admin> admins = new ArrayList<>();

    @PostMapping
    public Admin addAdmin(@RequestBody Admin admin) {
        admins.add(admin);
        return admin;
    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        return admins;
    }

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable String id) {
        return admins.stream()
                .filter(admin -> admin.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable String id) {
        admins.removeIf(admin -> admin.getId().equals(id));
    }
}
