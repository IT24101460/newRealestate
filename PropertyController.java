package com.realestate.controller;

import com.realestate.model.Property;
import com.realestate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // CREATE Operation: Adds a new property
    // Endpoint: POST /properties
    @PostMapping
    public ResponseEntity<Property> addProperty(
            @RequestParam String title,
            @RequestParam String location,
            @RequestParam double price,
            @RequestParam String description,
            @RequestParam String sellerId,
            @RequestParam String imageUrl) throws IOException {
        Property property = propertyService.addProperty(title, location, price, description, sellerId, imageUrl);
        return ResponseEntity.ok(property); // Returns the created property as JSON
    }

    // READ Operation: Retrieves all properties
    // Endpoint: GET /properties
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() throws IOException {
        List<Property> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties); // Returns list of all properties as JSON
    }

    // READ Operation: Retrieves a property by ID
    // Endpoint: GET /properties/{propertyId}
    @GetMapping("/{propertyId}")
    public ResponseEntity<Property> getPropertyById(@PathVariable String propertyId) throws IOException {
        Property property = propertyService.getPropertyById(propertyId);
        if (property != null) {
            return ResponseEntity.ok(property); // Returns the property if found
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }

    // READ Operation: Retrieves properties sorted by price
    // Endpoint: GET /properties/sorted
    @GetMapping("/sorted")
    public ResponseEntity<List<Property>> getPropertiesSortedByPrice() {
        List<Property> properties = propertyService.getPropertiesSortedByPrice();
        return ResponseEntity.ok(properties); // Returns list of properties sorted by price
    }

    // READ Operation: Retrieves properties by seller ID
    // Endpoint: GET /properties/seller/{sellerId}
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Property>> getPropertiesBySellerId(@PathVariable String sellerId) throws IOException {
        List<Property> properties = propertyService.getPropertiesBySellerId(sellerId);
        return ResponseEntity.ok(properties); // Returns list of properties for the specified seller
    }

    // UPDATE Operation: Updates an existing property
    // Endpoint: PUT /properties/{propertyId}
    @PutMapping("/{propertyId}")
    public ResponseEntity<Property> updateProperty(
            @PathVariable String propertyId,
            @RequestParam String title,
            @RequestParam String location,
            @RequestParam double price,
            @RequestParam String description,
            @RequestParam String imageUrl) throws IOException {
        Property property = propertyService.updateProperty(propertyId, title, location, price, description, imageUrl);
        if (property != null) {
            return ResponseEntity.ok(property); // Returns updated property
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }

    // DELETE Operation: Deletes a property
    // Endpoint: DELETE /properties/{propertyId}
    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String propertyId) throws IOException {
        boolean deleted = propertyService.deleteProperty(propertyId);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Returns 204 on successful deletion
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }
}
