package RealEstateProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String PROPERTY_FILE = "properties.txt";

    // Save properties to file
    public void saveProperties(List<Property> properties) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROPERTY_FILE))) {
            for (Property property : properties) {
                writer.write(String.format("%d,%s,%.2f,%s,%s,%s,%d,%s%n",
                        property.getPropertyId(),
                        property.getPropertyName(),
                        property.getPrice(),
                        property.getLocation(),
                        property.getType(),
                        property.getStatus(),
                        property.getSellerId(),
                        property.getDescription()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving properties: " + e.getMessage());
        }
    }

    // Load properties from file
    public List<Property> loadProperties() {
        List<Property> properties = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PROPERTY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Property property = new Property(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Double.parseDouble(parts[2]),
                        parts[3],
                        parts[4],
                        Integer.parseInt(parts[6])
                );
                property.setStatus(parts[5]);
                property.setDescription(parts[7]);
                properties.add(property);
            }
        } catch (IOException e) {
            System.err.println("Error loading properties: " + e.getMessage());
        }
        return properties;
    }
} 