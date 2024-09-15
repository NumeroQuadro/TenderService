package com.belunin.avito_test_task.Services;

import com.belunin.avito_test_task.Models.Review;
import com.belunin.avito_test_task.Repositories.BidRepository;
import com.belunin.avito_test_task.Repositories.EmployeeRepository;
import com.belunin.avito_test_task.Repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.belunin.avito_test_task.Models.Bid;
import com.belunin.avito_test_task.Models.Employee;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BidRepository bidRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ReviewService(
            ReviewRepository reviewRepository,
            BidRepository bidRepository,
            EmployeeRepository employeeRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.bidRepository = bidRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(UUID id) {
        return reviewRepository.findById(id);
    }

    @Transactional
    public Review createReview(UUID bidId, UUID reviewerId, String reviewText) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new IllegalArgumentException("Bid not found"));
        Employee reviewer = employeeRepository.findById(reviewerId).orElseThrow(() -> new IllegalArgumentException("Reviewer not found"));

        Review review = new Review();
        review.setBid(bid);
        review.setReviewer(reviewer);
        review.setReviewText(reviewText);
        review.setCreatedAt(new java.util.Date());

        return reviewRepository.save(review);
    }

    @Transactional
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public Optional<Review> updateReview(UUID id, String newReviewText) {
        return reviewRepository.findById(id).map(review -> {
            review.setReviewText(newReviewText);

            return reviewRepository.save(review);
        });
    }

    @Transactional
    public void deleteReview(UUID id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByBidId(UUID bidId) {
        return reviewRepository.findByBidId(bidId);
    }

    public List<Review> findAllByBidId(UUID bidId) {
        return reviewRepository.findByBidId(bidId);
    }

    public List<Review> getReviewsByReviewerId(UUID reviewerId) {
        return reviewRepository.findByReviewerId(reviewerId);
    }
}
