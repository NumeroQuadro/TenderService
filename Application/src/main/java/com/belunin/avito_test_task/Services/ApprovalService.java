package com.belunin.avito_test_task.Services;

import com.belunin.avito_test_task.Models.Approval;
import com.belunin.avito_test_task.Models.Organization;
import com.belunin.avito_test_task.Models.ResponseTypes.BidApprovalResponse;
import com.belunin.avito_test_task.Repositories.ApprovalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApprovalService {
    private final ApprovalRepository approvalRepository;
    private final OrganizationService organizationService;
    private final OrganisationResponsibleService organisationResponsibleService;

    @Autowired
    public ApprovalService(
            ApprovalRepository approvalRepository,
            OrganizationService organizationService,
            OrganisationResponsibleService organisationResponsibleService) {
        this.approvalRepository = approvalRepository;
        this.organizationService = organizationService;
        this.organisationResponsibleService = organisationResponsibleService;
    }

    @Transactional
    public BidApprovalResponse approveOrRejectBid(UUID bidId, UUID userId, boolean isApproved) {
        Approval approval = new Approval();
        approval.setBidId(bidId);
        approval.setUserId(userId);
        approval.setApproved(isApproved);
        approvalRepository.save(approval);

        if (!isApproved) {
            return new BidApprovalResponse(
                    false,
                    "Bid has been rejected due to a disapproval.",
                    0,
                    0);
        }

        int approvalCount = approvalRepository.countByBidIdAndIsApproved(bidId, true);
        Organization organization = organizationService.getOrganizationByBid(bidId);
        int responsibleCount = organisationResponsibleService.getResponsibleCount(organization.getId());
        int quorum = Math.min(3, responsibleCount);

        if (approvalCount >= quorum) {
            return new BidApprovalResponse(
                    true,
                    "Bid has been approved.",
                    approvalCount,
                    quorum);
        }

        return new BidApprovalResponse(
                false,
                "Bid approval is pending more approvals.",
                approvalCount,
                quorum);
    }
}

