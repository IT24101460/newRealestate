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

        public Review addReview(String sellerId, String buyerId, int rating, String comment) throws IOException {
        String reviewId = "R" + UUID.randomUUID().toString().substring(0, 7);         String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());         Review review = new Review(reviewId, sellerId, buyerId, rating, comment, timestamp);        FileHandler.writeReview(review.toDataString());         return review;     }

     public List<Review> getReviewsBySellerId(String sellerId) throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all lines from reviews.txt
        List<Review> reviews = new ArrayList<>();
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data);
                if (review != null && review.getSellerId().equals(sellerId)) {
                    reviews.add(review);                 }
            }
        }
        return reviews; reviews.txt
    public List<Review> getReviewsByBuyerId(String buyerId) throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all lines from reviews.txt
        List<Review> reviews = new ArrayList<>();
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data);
                if (review != null && review.getBuyerId().equals(buyerId)) {
                    reviews.add(review);                 }
            }
        }
        return reviews; // Return the list of reviews by the buyer
    }

public Review getReviewById(String reviewId) throws IOException {
        List<String> reviewData = FileHandler.readReviews();         
for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data);                if (review != null && review.getReviewId().equals(reviewId)) {
                    return review;                 }
            }
        }
        return null; // Return null if not found
    }

        public Review updateReview(String reviewId, int rating, String comment) throws IOException {
        List<String> reviewData = FileHandler.readReviews();
        List<String> updatedData = new ArrayList<>();
        Review updatedReview = null;
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data);
                if (review != null && review.getReviewId().equals(reviewId)) {
                    
                    updatedReview = new Review(reviewId, review.getSellerId(), review.getBuyerId(), rating, comment, review.getTimestamp());
                    updatedData.add(updatedReview.toDataString()); // Add updated data to list
                } else {
                    updatedData.add(data); // Keep unchanged data
                }
            }
        }
        if (updatedReview != null) {
            FileHandler.updateReviews(updatedData);         }
        return updatedReview;    }

    public boolean deleteReview(String reviewId) throws IOException {
        List<String> reviewData = FileHandler.readReviews(); // Read all current reviews from file
        List<String> updatedData = new ArrayList<>();
        boolean deleted = false;
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data);
                if (review == null || !review.getReviewId().equals(reviewId)) {
                    updatedData.add(data);                 } else {
                    deleted = true; 
                }
            }
        }
        if (deleted) {
            FileHandler.updateReviews(updatedData);        }
        return deleted;
    }
}
