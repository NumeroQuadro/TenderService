package com.belunin.avito_test_task.Repositories;

import com.belunin.avito_test_task.Models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {
    List<Bid> findByTenderId(UUID tenderId);
    List<Bid> findByCreatorUsername(String username);
    Optional<Bid> findBidByIdAndVersion(UUID id, int version);
}
