package com.example.propertymanagement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Seller class - extends User to manage sellers in the property management system
 * Responsible for listing properties and managing seller profiles
 */
public class Seller extends User {
    private String contactPhone;
    private String address;
    private List<Property> listedProperties;
    private String companyName;
    private String description;

    /**
     * Default constructor
     */
    public Seller() {
        super();
        this.role = "SELLER";
        this.listedProperties = new ArrayList<>();
    }

    /**
     * Constructor with essential fields
     */
    public Seller(String id, String username, String email) {
        super(id, username, email);
        this.role = "SELLER";
        this.listedProperties = new ArrayList<>();
    }

    /**
     * Full constructor with all fields
     */
    public Seller(String id, String username, String email, String contactPhone,
                  String address, String companyName, String description) {
        super(id, username, email);
        this.role = "SELLER";
        this.contactPhone = contactPhone;
        this.address = address;
        this.companyName = companyName;
        this.description = description;
        this.listedProperties = new ArrayList<>();
    }

    // Getters and Setters

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Property> getListedProperties() {
        return listedProperties;
    }

    public void setListedProperties(List<Property> listedProperties) {
        this.listedProperties = listedProperties;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Business Methods

    /**
     * Add a property to the seller's listings
     * @param property The property to list
     * @return true if successful
     */
    public boolean listProperty(Property property) {
        return listedProperties.add(property);
    }

    /**
     * Remove a property from the seller's listings
     * @param propertyId The ID of the property to remove
     * @return true if property was found and removed
     */
    public boolean removeProperty(int propertyId) {
        return listedProperties.removeIf(p -> p.getPropertyId() == propertyId);
    }

    /**
     * Update an existing property in the seller's listings
     * @param property The updated property object
     * @return true if property was found and updated
     */
    public boolean updateProperty(Property property) {
        for (int i = 0; i < listedProperties.size(); i++) {
            if (listedProperties.get(i).getPropertyId() == property.getPropertyId()) {
                listedProperties.set(i, property);
                return true;
            }
        }
        return false;
    }

    /**
     * Find a property by ID in the seller's listings
     * @param propertyId The ID of the property to find
     * @return The property if found, null otherwise
     */
    public Property findPropertyById(int propertyId) {
        for (Property property : listedProperties) {
            if (property.getPropertyId() == propertyId) {
                return property;
            }
        }
        return null;
    }

    /**
     * Get the count of properties listed by this seller
     * @return The number of properties listed
     */
    public int getPropertyCount() {
        return listedProperties.size();
    }

    /**
     * Creates a complete profile of the seller with all details
     * @return String representation of the seller's profile
     */
    public String getFullProfile() {
        StringBuilder profile = new StringBuilder();
        profile.append("Seller Profile:\n");
        profile.append("ID: ").append(id).append("\n");
        profile.append("Username: ").append(username).append("\n");
        profile.append("Email: ").append(email).append("\n");
        profile.append("Contact Phone: ").append(contactPhone).append("\n");
        profile.append("Address: ").append(address).append("\n");
        profile.append("Company: ").append(companyName).append("\n");
        profile.append("Description: ").append(description).append("\n");
        profile.append("Properties Listed: ").append(listedProperties.size()).append("\n");

        return profile.toString();
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", address='" + address + '\'' +
                ", companyName='" + companyName + '\'' +
                ", propertiesCount=" + listedProperties.size() +
                '}';
    }
}