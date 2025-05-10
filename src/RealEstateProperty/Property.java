package RealEstateProperty;

public class Property {
    private int propertyId;
    private String propertyName;
    private double price;
    private String location;
    private String type;
    private String status; // Available, Sold, Reserved
    private int sellerId;
    private String description; // New attribute for property description

    public Property(int propertyId, String propertyName, double price, String location, String type, int sellerId) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.price = price;
        this.location = location;
        this.type = type;
        this.status = "Available";
        this.sellerId = sellerId;
        this.description = ""; // Initialize with empty description
    }

    // Getters and Setters
    public int getPropertyId() { return propertyId; }
    public void setPropertyId(int propertyId) { this.propertyId = propertyId; }

    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "RealEstateProperty.Property{" +
                "id=" + propertyId +
                ", name='" + propertyName + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", sellerId=" + sellerId +
                ", description='" + description + '\'' +
                '}';
    }
}