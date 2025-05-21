package com.example.propertymanagement.controller;

import com.example.propertymanagement.model.Buyer;
import com.example.propertymanagement.model.Property;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {

    private List<Buyer> buyers = new ArrayList<>();

    @PostMapping
    public Buyer addBuyer(@RequestBody Buyer buyer) {
        buyers.add(buyer);
        return buyer;
    }

    @GetMapping
    public List<Buyer> getAllBuyers() {
        return buyers;
    }

    @GetMapping("/{id}")
    public Buyer getBuyerById(@PathVariable String id) {
        return buyers.stream()
                .filter(buyer -> buyer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    @DeleteMapping("/{id}")
    public void deleteBuyer(@PathVariable String id) {
        buyers.removeIf(buyer -> buyer.getId().equals(id));
    }
}
