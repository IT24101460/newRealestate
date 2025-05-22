package com.realestate.service;

import com.realestate.model.Review;
import com.realestate.util.FileHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    public Review submitReview(String propertyId, String buyerId, int rating, String comment) throws IOException {
        String reviewId = "R" + UUID.randomUUID().toString().substring(0, 8);
        Review review = new Review(reviewId, propertyId, buyerId, rating, comment, LocalDate.now());
        FileHandler.writeReview(review.toDataString());
        return review;
    }

    public List<Review> getReviewsByPropertyId(String propertyId) throws IOException {
        List<String> reviewData = FileHandler.readReviews();
        List<Review> reviews = new ArrayList<>();
        for (String data : reviewData) {
            if (!data.isEmpty()) {
                Review review = Review.fromDataString(data);
                if (review.getPropertyId().equals(propertyId)) {
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }

    public List<Review> getReviewsByBuyerId(String buyerId) throws IOException {
        List<String> reviewData = FileHandler.readReviews();
        return reviewData.stream()
                .filter(data -> !data.isEmpty())
                .map(Review::fromDataString)
                .filter(review -> review.getBuyerId().equals(buyerId))
                .collect(Collectors.toList());
    }
}
