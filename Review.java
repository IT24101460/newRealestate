package com.realestate.model;

public class Review {
    private String reviewId; 
    private String sellerId; 
    private String buyerId; 
    private int rating; 
    private String comment; 
    private String timestamp;     

public Review(String reviewId, String sellerId, String buyerId, int rating, String comment, String timestamp) {
        this.reviewId = reviewId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

  
    public Review() {
    }

    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }
    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

   
    public String toDataString() {
        return reviewId + "|" + sellerId + "|" + buyerId + "|" + rating + "|" + comment + "|" + timestamp;
    }

   
    public static Review fromDataString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length == 6) {
            Review review = new Review();
            review.setReviewId(parts[0]);
            review.setSellerId(parts[1]);
            review.setBuyerId(parts[2]);
            try {
                review.setRating(Integer.parseInt(parts[3]));
            } catch (NumberFormatException e) {
                review.setRating(0);
            }
            review.setComment(parts[4]);
            review.setTimestamp(parts[5]);
            return review;
        }
        return null; // Return null if data format is incorrect
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
