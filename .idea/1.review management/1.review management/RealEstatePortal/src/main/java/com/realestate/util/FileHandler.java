package com.realestate.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class FileHandler {
    // Path to reviews.txt (adjust if deploying as JAR, use ClassPathResource)
    private static final String REVIEWS_FILE = "src/main/resources/data/reviews.txt";

    public static List<String> readReviews() throws IOException {
        Path path = Paths.get(REVIEWS_FILE);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent()); // Ensure directory exists
            Files.createFile(path);
            return Collections.emptyList();
        }
        return Files.readAllLines(path);
    }

    public static void writeReview(String reviewData) throws IOException {
        Path path = Paths.get(REVIEWS_FILE);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent()); // Ensure directory exists
            Files.createFile(path);
        }
        Files.writeString(path, reviewData + "\n", StandardOpenOption.APPEND);
    }
}
