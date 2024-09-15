package com.belunin.avito_test_task.Services;

import com.belunin.avito_test_task.Models.Bid;
import com.belunin.avito_test_task.Repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BidService {
    private final BidRepository bidRepository;

    @Autowired
    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    public Optional<Bid> getBidById(UUID id) {
        return bidRepository.findById(id);
    }

    @Transactional
    public Bid createBid(Bid bid) {
        return bidRepository.save(bid);
    }

    @Transactional
    public Optional<Bid> updateBid(UUID id, Bid newBidData) {
        return bidRepository.findById(id).map(bid -> {
            bid.setName(newBidData.getName());
            bid.setDescription(newBidData.getDescription());
            bid.setStatus(newBidData.getStatus());
            bid.setUpdatedAt(newBidData.getUpdatedAt());

            return bid;
        });
    }

    @Transactional
    public Optional<Bid> rollbackBid(UUID bidId, int version) {
        return bidRepository.findById(bidId).map(bid -> {
            if (bid.getVersion() > version) {
                Bid bidToRollback = bidRepository.findBidByIdAndVersion(bidId, version)
                        .orElseThrow(() -> new IllegalStateException("Specified version of the bid not found"));

                bid.setName(bidToRollback.getName());
                bid.setDescription(bidToRollback.getDescription());
                bid.setStatus(bidToRollback.getStatus());
                bid.setVersion(version);
                bid.setUpdatedAt(new Date());

                return bidRepository.save(bid);
            } else {
                throw new IllegalArgumentException("Cannot rollback to a newer or same version.");
            }
        });
    }


    public List<Bid> getBidsByCreatorUsername(String username) {
        return bidRepository.findByCreatorUsername(username);
    }

    @Transactional
    public void deleteBid(UUID id) {
        bidRepository.deleteById(id);
    }

    public List<Bid> getBidsByTenderId(UUID tenderId) {
        return bidRepository.findByTenderId(tenderId);
    }
}

