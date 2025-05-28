package com.realestate.service;

import com.realestate.model.Property;
import com.realestate.util.FileHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyService {
    // BST Node class for Property (used for efficient searching and dynamic management by price)
    private class BSTNode {
        Property prop; // The property data stored in this node
        BSTNode left; // Left child (properties with lower price)
        BSTNode right; // Right child (properties with higher price)

        BSTNode(Property prop) {
            this.prop = prop;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root; // Root of the BST for price-based dynamic management

    // Constructor: Initializes an empty BST and loads existing properties from file
    public PropertyService() {
        this.root = null;
        loadPropertiesIntoBST(); // Load properties from file into BST on startup
    }

    // Helper method to load properties from file into BST for dynamic management
    private void loadPropertiesIntoBST() {
        try {
            List<String> propertyData = FileHandler.readProperties(); // Read raw data from properties.txt
            for (String data : propertyData) {
                if (!data.isEmpty()) {
                    Property property = Property.fromDataString(data); // Parse string to Property object
                    if (property != null) {
                        insertIntoBST(property); // Insert into BST based on price
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading properties into BST: " + e.getMessage());
        }
    }

    // Helper method to insert a property into the BST based on price for dynamic updates
    private void insertIntoBST(Property property) {
        root = insertRec(root, property);
    }

    // Recursive helper for BST insertion
    private BSTNode insertRec(BSTNode root, Property property) {
        if (root == null) {
            return new BSTNode(property); // Create new node if position is empty
        }
        if (property.getPrice() < root.prop.getPrice()) {
            root.left = insertRec(root.left, property); // Insert into left subtree
        } else if (property.getPrice() > root.prop.getPrice()) {
            root.right = insertRec(root.right, property); // Insert into right subtree
        }
        return root;
    }

    // Helper method to perform in-order traversal of BST for sorted list by price
    private void inOrderTraversal(BSTNode root, List<Property> sortedProperties) {
        if (root != null) {
            inOrderTraversal(root.left, sortedProperties); // Visit left subtree
            sortedProperties.add(root.prop); // Add current node
            inOrderTraversal(root.right, sortedProperties); // Visit right subtree
        }
    }

    // Quick Sort implementation for sorting properties by price as an alternative to BST traversal
    private void quickSort(List<Property> properties, int low, int high) {
        if (low < high) {
            int pi = partition(properties, low, high); // Get partition index
            quickSort(properties, low, pi - 1); // Sort left part
            quickSort(properties, pi + 1, high); // Sort right part
        }
    }

    // Partition helper for Quick Sort
    private int partition(List<Property> properties, int low, int high) {
        double pivot = properties.get(high).getPrice(); // Choose last element as pivot
        int i = (low - 1); // Index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (properties.get(j).getPrice() <= pivot) {
                i++; // Increment index of smaller element
                // Swap elements
                Property temp = properties.get(i);
                properties.set(i, properties.get(j));
                properties.set(j, temp);
            }
        }
        // Place pivot in its correct position
        Property temp = properties.get(i + 1);
        properties.set(i + 1, properties.get(high));
        properties.set(high, temp);
        return i + 1;
    }

    // CREATE Operation: Adds a new property to properties.txt and dynamically to BST
    public Property addProperty(String title, String location, double price, String description, String sellerId, String imageUrl) throws IOException {
        String propertyId = "P" + UUID.randomUUID().toString().substring(0, 7); // Unique ID with property prefix
        Property property = new Property(propertyId, title, location, price, description, sellerId, imageUrl); // Create new Property object
        FileHandler.writeProperty(property.toDataString()); // Write to properties.txt using FileHandler
        insertIntoBST(property); // Dynamically insert into BST for searching and sorting
        return property; // Return the created property
    }

    // READ Operation: Retrieves all properties from properties.txt
    public List<Property> getAllProperties() throws IOException {
        List<String> propertyData = FileHandler.readProperties(); // Read all lines from properties.txt
        List<Property> properties = new ArrayList<>();
        for (String data : propertyData) {
            if (!data.isEmpty()) {
                Property property = Property.fromDataString(data); // Parse each line into a Property object
                if (property != null) {
                    properties.add(property);
                }
            }
        }
        return properties; // Return the list of all properties
    }

    // READ Operation: Retrieves a single property by its ID
    public Property getPropertyById(String propertyId) throws IOException {
        List<String> propertyData = FileHandler.readProperties(); // Read all lines from properties.txt
        for (String data : propertyData) {
            if (!data.isEmpty()) {
                Property property = Property.fromDataString(data); // Parse line to Property object
                if (property != null && property.getPropertyId().equals(propertyId)) {
                    return property; // Return matching property
                }
            }
        }
        return null; // Return null if not found
    }

    // READ Operation: Retrieves properties sorted by price using BST in-order traversal
    public List<Property> getPropertiesSortedByPriceUsingBST() {
        List<Property> sortedProperties = new ArrayList<>();
        inOrderTraversal(root, sortedProperties); // Perform in-order traversal to get sorted list by price
        return sortedProperties; // Return sorted list using BST
    }

    // READ Operation: Retrieves properties sorted by price using Quick Sort as an alternative
    public List<Property> getPropertiesSortedByPriceUsingQuickSort() throws IOException {
        List<Property> properties = getAllProperties(); // Get all properties from file
        if (properties.isEmpty()) {
            return properties; // Return empty list if no properties
        }
        quickSort(properties, 0, properties.size() - 1); // Apply Quick Sort on the list by price
        return properties; // Return sorted list using Quick Sort
    }

    // READ Operation: Retrieves properties by seller ID
    public List<Property> getPropertiesBySellerId(String sellerId) throws IOException {
        List<String> propertyData = FileHandler.readProperties(); // Read all lines from properties.txt
        List<Property> properties = new ArrayList<>();
        for (String data : propertyData) {
            if (!data.isEmpty()) {
                Property property = Property.fromDataString(data); // Parse each line into a Property object
                if (property != null && property.getSellerId().equals(sellerId)) {
                    properties.add(property); // Add only properties for the specified seller
                }
            }
        }
        return properties; // Return the list of properties for the seller
    }

    // UPDATE Operation: Updates an existing property in properties.txt and refreshes BST
    public Property updateProperty(String propertyId, String title, String location, double price, String description, String imageUrl) throws IOException {
        List<String> propertyData = FileHandler.readProperties(); // Read all current properties from file
        List<String> updatedData = new ArrayList<>();
        Property updatedProperty = null;
        for (String data : propertyData) {
            if (!data.isEmpty()) {
                Property property = Property.fromDataString(data);
                if (property != null && property.getPropertyId().equals(propertyId)) {
                    // Update the property with new values, retaining original sellerId
                    updatedProperty = new Property(propertyId, title, location, price, description, property.getSellerId(), imageUrl);
                    updatedData.add(updatedProperty.toDataString()); // Add updated data to list
                } else {
                    updatedData.add(data); // Keep unchanged data
                }
            }
        }
        if (updatedProperty != null) {
            FileHandler.updateProperties(updatedData); // Write updated list back to properties.txt
            // Reload BST to reflect changes (price might have changed)
            root = null; // Reset BST
            loadPropertiesIntoBST(); // Reload all properties into BST
        }
        return updatedProperty; // Return updated property or null if not found
    }

    // DELETE Operation: Removes a property from properties.txt and refreshes BST
    public boolean deleteProperty(String propertyId) throws IOException {
        List<String> propertyData = FileHandler.readProperties(); // Read all current properties from file
        List<String> updatedData = new ArrayList<>();
        boolean deleted = false;
        for (String data : propertyData) {
            if (!data.isEmpty()) {
                Property property = Property.fromDataString(data);
                if (property == null || !property.getPropertyId().equals(propertyId)) {
                    updatedData.add(data); // Keep non-matching properties
                } else {
                    deleted = true; // Mark as deleted if found
                }
            }
        }
        if (deleted) {
            FileHandler.updateProperties(updatedData); // Write updated list back to properties.txt
            // Reload BST to reflect deletion
            root = null; // Reset BST
            loadPropertiesIntoBST(); // Reload all properties into BST
        }
        return deleted; // Return true if deletion occurred, false if not found
    }
}
