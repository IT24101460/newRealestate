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
    public ResponseEntity<Review> submitReview(
            @RequestParam String propertyId,
            @RequestParam String buyerId,
            @RequestParam int rating,
            @RequestParam String comment) throws IOException {
        Review review = reviewService.submitReview(propertyId, buyerId, rating, comment);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Review>> getReviewsByPropertyId(@PathVariable String propertyId) throws IOException {
        List<Review> reviews = reviewService.getReviewsByPropertyId(propertyId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Review>> getReviewsByBuyerId(@PathVariable String buyerId) throws IOException {
        List<Review> reviews = reviewService.getReviewsByBuyerId(buyerId);
        return ResponseEntity.ok(reviews);
    }
}
