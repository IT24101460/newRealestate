package com.realestate.controller;

import com.realestate.model.User;
import com.realestate.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // Register a new seller
    @PostMapping
    public ResponseEntity<User> addSeller(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        try {
            User seller = sellerService.addSeller(name, email, password);
            return ResponseEntity.ok(seller); 
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null); // Return if email already exists
        }
    }

    // Retrieves all sellers
    @GetMapping
    public ResponseEntity<List<User>> getAllSellers() throws IOException {
        List<User> sellers = sellerService.getAllSellers();
        return ResponseEntity.ok(sellers); // Returns list of all sellers as JSON
    }

    //Retrieves a seller by there ID
    @GetMapping("/{sellerId}")
    public ResponseEntity<User> getSellerById(@PathVariable String sellerId) throws IOException {
        User seller = sellerService.getSellerById(sellerId);
        if (seller != null) {
            return ResponseEntity.ok(seller); 
        }
        return ResponseEntity.notFound().build(); 
    }

    // seller login
    @PostMapping("/login")
    public ResponseEntity<User> loginSeller(
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        User seller = sellerService.loginSeller(email, password);
        if (seller != null) {
            return ResponseEntity.ok(seller); // Returns to the seller if credentials can not be matched
        }
        return ResponseEntity.notFound().build(); 
    }

    // Updates seller's profile
    @PutMapping("/{sellerId}")
    public ResponseEntity<User> updateSeller(
            @PathVariable String sellerId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) throws IOException {
        User seller = sellerService.updateSeller(sellerId, name, email, password);
        if (seller != null) {
            return ResponseEntity.ok(seller); // Return to the updated seller
        }
        return ResponseEntity.notFound().build(); 
    }

    //Deletes a seller
    @DeleteMapping("/{sellerId}")
    public ResponseEntity<Void> deleteSeller(@PathVariable String sellerId) throws IOException {
        boolean deleted = sellerService.deleteSeller(sellerId);
        if (deleted) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build(); 
    }
}
