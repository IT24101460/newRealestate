package com.realestate.controller;

import com.realestate.model.User;
import com.realestate.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/buyers")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;


    @PostMapping
    public ResponseEntity<User> addBuyer(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        try {
            User buyer = buyerService.addBuyer(name, email, password);
            return ResponseEntity.ok(buyer);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllBuyers() throws IOException {
        List<User> buyers = buyerService.getAllBuyers();
        return ResponseEntity.ok(buyers);
    }


    @GetMapping("/{buyerId}")
    public ResponseEntity<User> getBuyerById(@PathVariable String buyerId) throws IOException {
        User buyer = buyerService.getBuyerById(buyerId);
        if (buyer != null) {
            return ResponseEntity.ok(buyer);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/login")
    public ResponseEntity<User> loginBuyer(
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        User buyer = buyerService.loginBuyer(email, password);
        if (buyer != null) {
            return ResponseEntity.ok(buyer);
        }
        return ResponseEntity.notFound().build();
        
    @PutMapping("/{buyerId}")
    public ResponseEntity<User> updateBuyer(
            @PathVariable String buyerId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        User buyer = buyerService.updateBuyer(buyerId, name, email, password);
        if (buyer != null) {
            return ResponseEntity.ok(buyer);
        }
        return ResponseEntity.notFound().build();
    }

    
    @DeleteMapping("/{buyerId}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable String buyerId) throws IOException {
        boolean deleted = buyerService.deleteBuyer(buyerId);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Returns 204 on successful deletion
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }
}
