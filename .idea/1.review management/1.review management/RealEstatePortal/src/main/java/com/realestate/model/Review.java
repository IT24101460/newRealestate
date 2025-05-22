package com.realestate.model;

import java.time.LocalDate;

public class Review {
    private String reviewId;
    private String propertyId;
    private String buyerId;
    private int rating;
    private String comment;
    private LocalDate date;

    // Constructor
    public Review(String reviewId, String propertyId, String buyerId, int rating, String comment, LocalDate date) {
        this.reviewId = reviewId;
        this.propertyId = propertyId;
        this.buyerId = buyerId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    // Default Constructor (for parsing)
    public Review() {
    }

    // Getters and Setters
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }
    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }
    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    // Convert to pipe-delimited string for file storage
    public String toDataString() {
        return reviewId + "|" + propertyId + "|" + buyerId + "|" + rating + "|" + comment + "|" + date.toString();
    }

    // Parse from pipe-delimited string
    public static Review fromDataString(String data) {
        String[] parts = data.split("\\|");
        Review review = new Review();
        review.setReviewId(parts[0]);
        review.setPropertyId(parts[1]);
        review.setBuyerId(parts[2]);
        review.setRating(Integer.parseInt(parts[3]));
        review.setComment(parts[4]);
        review.setDate(LocalDate.parse(parts[5]));
        return review;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
