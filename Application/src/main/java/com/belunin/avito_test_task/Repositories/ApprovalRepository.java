package com.belunin.avito_test_task.Repositories;

import com.belunin.avito_test_task.Models.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, UUID> {
    int countByBidIdAndIsApproved(UUID bidId, boolean isApproved);
}
