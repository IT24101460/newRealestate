package com.realestate.service;

import com.realestate.model.Review;
import com.realestate.util.FileHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    // CREATE Operation: Adds a new review to reviews.txt
    public Review addReview(String sellerId, String buyerId, int rating, String comment) throws IOException {
        String reviewId = "R" + UUID.randomUUID().toString().substring(0, 7); // Unique ID with review prefix
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // Current timestamp
        Review review = new Review(reviewId, sellerId, buyerId, rating, comment, timestamp); // Create new Review object
        FileHandler.writeReview(review.toDataString()); // Write to reviews.txt using FileHandler
        return review; // Return the created review
    }

    // READ Operation: Retrieves all reviews from reviews.txt
    public List<Review> getAllReviews() throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all lines from reviews.txt
        List<Review> reviews = new ArrayList<>();
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data); // Parse each line into a Review object
                if (review != null) {
                    reviews.add(review);
                }
            }
        }
        return reviews; // Return the list of all reviews
    }

    // READ Operation: Retrieves reviews by seller ID
    public List<Review> getReviewsBySellerId(String sellerId) throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all lines from reviews.txt
        List<Review> reviews = new ArrayList<>();
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data); // Parse each line into a Review object
                if (review != null && review.getSellerId().equals(sellerId)) {
                    reviews.add(review); // Add only reviews for the specified seller
                }
            }
        }
        return reviews; // Return the list of reviews for the seller
    }

    // READ Operation: Retrieves reviews by buyer ID
    public List<Review> getReviewsByBuyerId(String buyerId) throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all lines from reviews.txt
        List<Review> reviews = new ArrayList<>();
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data); // Parse each line into a Review object
                if (review != null && review.getBuyerId().equals(buyerId)) {
                    reviews.add(review); // Add only reviews by the specified buyer
                }
            }
        }
        return reviews; // Return the list of reviews by the buyer
    }

    // READ Operation: Retrieves a single review by its ID
    public Review getReviewById(String reviewId) throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all lines from reviews.txt
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data); // Parse line to Review object
                if (review != null && review.getReviewId().equals(reviewId)) {
                    return review; // Return matching review
                }
            }
        }
        return null; // Return null if not found
    }

    // UPDATE Operation: Updates an existing review in reviews.txt
    public Review updateReview(String reviewId, int rating, String comment) throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all current reviews from file
        List<String> updatedData = new ArrayList<>();
        Review updatedReview = null;
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data);
                if (review != null && review.getReviewId().equals(reviewId)) {
                    // Update the review with new values, retaining original IDs and timestamp
                    updatedReview = new Review(reviewId, review.getSellerId(), review.getBuyerId(), rating, comment, review.getTimestamp());
                    updatedData.add(updatedReview.toDataString()); // Add updated data to list
                } else {
                    updatedData.add(data); // Keep unchanged data
                }
            }
        }
        if (updatedReview != null) {
            FileHandler.updateReviews(updatedData); // Write updated list back to reviews.txt
        }
        return updatedReview; // Return updated review or null if not found
    }

    // DELETE Operation: Removes a review from reviews.txt
    public boolean deleteReview(String reviewId) throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all current reviews from file
        List<String> updatedData = new ArrayList<>();
        boolean deleted = false;
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data);
                if (review == null || !review.getReviewId().equals(reviewId)) {
                    updatedData.add(data); // Keep non-matching reviews
                } else {
                    deleted = true; // Mark as deleted if found
                }
            }
        }
        if (deleted) {
            FileHandler.updateReviews(updatedData); // Write updated list back to reviews.txt
        }
        return deleted; // Return true if deletion occurred, false if not found
    }
}
