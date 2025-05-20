package com.example.propertymanagement.dsa;

import com.example.propertymanagement.model.Property;
import java.util.List;

public class PropertySorter {

    public static void quickSort(List<Property> properties, int low, int high) {
        if (low < high) {
            int pi = partition(properties, low, high);
            quickSort(properties,low,pi-1);
            quickSort(properties, pi + 1, high);
        }
    }

    private static int partition(List<Property> properties, int low, int high) {
        double pivot = properties.get(high).getPrice();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (properties.get(j).getPrice() <= pivot) {
                i++;
                // Swap properties[i] and properties[j]
                Property temp = properties.get(i);
                properties.set(i, properties.get(j));
                properties.set(j, temp);
            }
        }
        // Swap properties[i+1] and properties[high] (or pivot)
        Property temp = properties.get(i + 1);
        properties.set(i + 1, properties.get(high));
        properties.set(high, temp);

        return i + 1;
    }
}
