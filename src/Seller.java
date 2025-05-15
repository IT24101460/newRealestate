import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Seller extends User {
    private List<Property> properties = new ArrayList<>();

    public Seller(int userId, String username, String password, String name, String email, String phone) {
        super(userId, username, password, "SELLER", name, email, phone);
    }

    // Create a new property listing
    public Property listProperty(String propertyName, double price, String location, String type, String description) {
        int propertyId = generatePropertyId();
        Property newProperty = new Property(
                propertyId,
                propertyName,
                price,
                location,
                type,
                getUserId()
        );
        newProperty.setDescription(description);
        newProperty.setStatus("AVAILABLE");
        properties.add(newProperty);
        return newProperty;
    }

    // Generate a unique property ID
    private int generatePropertyId() {
        // Simple implementation - in real system you might use a more robust approach
        return (int) (System.currentTimeMillis() % 1000000);
    }

    // Get all properties
    public List<Property> getProperties() {
        return new ArrayList<>(properties); // Return a copy to maintain encapsulation
    }

    // Get property by ID
    public Property getPropertyById(int propertyId) {
        for (Property property : properties) {
            if (property.getPropertyId() == propertyId) {
                return property;
            }
        }
        return null;
    }

    // Update property details
    public boolean updateProperty(int propertyId, String propertyName, Double price,
                                  String location, String type, String description) {
        Property property = getPropertyById(propertyId);
        if (property != null) {
            if (propertyName != null) property.setPropertyName(propertyName);
            if (price != null) property.setPrice(price);
            if (location != null) property.setLocation(location);
            if (type != null) property.setType(type);
            if (description != null) property.setDescription(description);
            return true;
        }
        return false;
    }

    // Change property status (AVAILABLE, RESERVED, SOLD)
    public boolean changePropertyStatus(int propertyId, String status) {
        Property property = getPropertyById(propertyId);
        if (property != null) {
            property.setStatus(status);
            return true;
        }
        return false;
    }

    // Remove property
    public boolean removeProperty(int propertyId) {
        return properties.removeIf(p -> p.getPropertyId() == propertyId);
    }

    // Get account details
    public String getAccountDetails() {
        return String.format("ID: %d, Username: %s, Name: %s, Email: %s, Phone: %s",
                getUserId(), getUsername(), getName(), getEmail(), getPhone());
    }

    // Sort properties by price using Quick Sort
    public List<Property> sortPropertiesByPrice(boolean ascending) {
        List<Property> sortedProperties = new ArrayList<>(properties);
        quickSort(sortedProperties, 0, sortedProperties.size() - 1, ascending);
        return sortedProperties;
    }

    // QuickSort implementation
    private void quickSort(List<Property> list, int low, int high, boolean ascending) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, ascending);
            quickSort(list, low, pivotIndex - 1, ascending);
            quickSort(list, pivotIndex + 1, high, ascending);
        }
    }

    private int partition(List<Property> list, int low, int high, boolean ascending) {
        double pivot = list.get(high).getPrice();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (ascending ? list.get(j).getPrice() <= pivot : list.get(j).getPrice() >= pivot) {
                i++;
                Property temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        Property temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    // Filter properties by type
    public List<Property> filterByType(String type) {
        List<Property> filtered = new ArrayList<>();
        for (Property property : properties) {
            if (property.getType().equalsIgnoreCase(type)) {
                filtered.add(property);
            }
        }
        return filtered;
    }

    // Filter properties by price range
    public List<Property> filterByPriceRange(double minPrice, double maxPrice) {
        List<Property> filtered = new ArrayList<>();
        for (Property property : properties) {
            double price = property.getPrice();
            if (price >= minPrice && price <= maxPrice) {
                filtered.add(property);
            }
        }
        return filtered;
    }

    // Filter properties by location
    public List<Property> filterByLocation(String location) {
        List<Property> filtered = new ArrayList<>();
        for (Property property : properties) {
            if (property.getLocation().equalsIgnoreCase(location)) {
                filtered.add(property);
            }
        }
        return filtered;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "userId=" + getUserId() +
                ", username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", propertiesCount=" + properties.size() +
                '}';
    }
}
