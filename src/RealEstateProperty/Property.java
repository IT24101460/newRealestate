package com.realestate.model;

public class Property {
    private String propertyId;
    private String title;
    private String location;
    private double price;
    private String description;
    private String sellerId;
    private String imageUrl;

    public Property(String propertyId, String title, String location, double price, String description, String sellerId, String imageUrl) {
        this.propertyId = propertyId;
        this.title = title;
        this.location = location;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;
        this.imageUrl = imageUrl;
    }

    public Property() {
    }

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

    public String toDataString() {
        return propertyId + "|" + title + "|" + location + "|" + price + "|" + description + "|" + sellerId + "|" + imageUrl;
    }

    public static Property fromDataString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length == 7) {
            Property property = new Property();
            property.setPropertyId(parts[0]);
            property.setTitle(parts[1]);
            property.setLocation(parts[2]);
            property.setPrice(Double.parseDouble(parts[3]));
            property.setDescription(parts[4]);
            property.setSellerId(parts[5]);
            property.setImageUrl(parts[6]);
            return property;
        }
        return null;
    }
}
