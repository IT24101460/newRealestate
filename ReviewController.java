package com.realestate.controller;

import com.realestate.model.Review;
import com.realestate.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

      @PostMapping
    public ResponseEntity<Review> addReview(
            @RequestParam String sellerId,
            @RequestParam String buyerId,
            @RequestParam int rating,
            @RequestParam String comment) throws IOException {
        try {
            Review review = reviewService.addReview(sellerId, buyerId, rating, comment);
            return ResponseEntity.ok(review); // Returns the created review as JSON
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 if error occurs
        }
    }

      @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Review>> getReviewsBySellerId(@PathVariable String sellerId) throws IOException {
        List<Review> reviews = reviewService.getReviewsBySellerId(sellerId);
        return ResponseEntity.ok(reviews); // Returns list of reviews for the seller
    }

        @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Review>> getReviewsByBuyerId(@PathVariable String buyerId) throws IOException {
        List<Review> reviews = reviewService.getReviewsByBuyerId(buyerId);
        return ResponseEntity.ok(reviews); // Returns list of reviews by the buyer
    }

       @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable String reviewId) throws IOException {
        Review review = reviewService.getReviewById(reviewId);
        if (review != null) {
            return ResponseEntity.ok(review); // Returns the review if found
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }

    // UPDATE Operation: Updates an existing review
    // Endpoint: PUT /reviews/{reviewId}
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable String reviewId,
            @RequestParam int rating,
            @RequestParam String comment) throws IOException {
        Review review = reviewService.updateReview(reviewId, rating, comment);
        if (review != null) {
            return ResponseEntity.ok(review); // Returns updated review
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }

        @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) throws IOException {
        boolean deleted = reviewService.deleteReview(reviewId);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Returns 204 on successful deletion
        }
        return ResponseEntity.notFound().build(); // Returns 404 if not found
    }
}
