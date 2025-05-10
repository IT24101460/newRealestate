package RealEstateProperty;

import java.util.ArrayList;
import java.util.List;

public class PropertyManager {
    private List<Property> properties;
    private FileHandler fileHandler;

    public PropertyManager() {
        this.properties = new ArrayList<>();
        this.fileHandler = new FileHandler();
    }

    // CRUD Operations
    public void addProperty(Property property) {
        properties.add(property);
        fileHandler.saveProperties(properties);
    }

    public Property getProperty(int propertyId) {
        return properties.stream()
                .filter(p -> p.getPropertyId() == propertyId)
                .findFirst()
                .orElse(null);
    }

    public void updateProperty(Property property) {
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getPropertyId() == property.getPropertyId()) {
                properties.set(i, property);
                break;
            }
        }
        fileHandler.saveProperties(properties);
    }

    public void deleteProperty(int propertyId) {
        properties.removeIf(p -> p.getPropertyId() == propertyId);
        fileHandler.saveProperties(properties);
    }

    // Quick Sort Implementation
    public void sortPropertiesByPrice() {
        quickSort(properties, 0, properties.size() - 1);
    }

    private void quickSort(List<Property> properties, int low, int high) {
        if (low < high) {
            int pi = partition(properties, low, high);
            quickSort(properties, low, pi - 1);
            quickSort(properties, pi + 1, high);
        }
    }

    private int partition(List<Property> properties, int low, int high) {
        double pivot = properties.get(high).getPrice();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (properties.get(j).getPrice() <= pivot) {
                i++;
                swap(properties, i, j);
            }
        }
        swap(properties, i + 1, high);
        return i + 1;
    }

    private void swap(List<Property> properties, int i, int j) {
        Property temp = properties.get(i);
        properties.set(i, properties.get(j));
        properties.set(j, temp);
    }

    // Getters
    public List<Property> getProperties() {
        return properties;
    }
} 