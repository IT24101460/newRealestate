package com.realestate.model;

public class Property {
    private String propertyId; // Unique identifier for the property
    private String title; // Property title or name
    private String location; // Property location
    private double price; // Property price
    private String description; // Property description
    private String sellerId; // ID of the seller
    private String imageUrl; // URL for property image

    // Create new property using constructor
    public Property(String propertyId, String title, String location, double price, String description, String sellerId, String imageUrl) {
        this.propertyId = propertyId;
        this.title = title;
        this.location = location;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;
        this.imageUrl = imageUrl;
    }

    // Default constructor
    public Property() {
    }

    // Getters and Setters
    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // Convert property to String with space
    public String toDataString() {
        return propertyId + "|" + title + "|" + location + "|" + price + "|" + description + "|" + sellerId + "|" + imageUrl;
    }

    // Parse property from pipe-delimited string read from file
    public static Property fromDataString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length == 7) {
            Property property = new Property();
            property.setPropertyId(parts[0]);
            property.setTitle(parts[1]);
            property.setLocation(parts[2]);
            try {
                property.setPrice(Double.parseDouble(parts[3]));
            } catch (NumberFormatException e) {
                property.setPrice(0.0);
            }
            property.setDescription(parts[4]);
            property.setSellerId(parts[5]);
            property.setImageUrl(parts[6]);
            return property;
        }
        return null; // Return null if data format is incorrect
    }

    @Override
    public String toString() {
        return "Property{" +
                "propertyId='" + propertyId + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                '}';
    }
}
