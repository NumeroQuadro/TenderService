package com.belunin.avito_test_task.Repositories;

import com.belunin.avito_test_task.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByReviewerId(UUID reviewerId);
    List<Review> findByBidId(UUID bidId);
}
