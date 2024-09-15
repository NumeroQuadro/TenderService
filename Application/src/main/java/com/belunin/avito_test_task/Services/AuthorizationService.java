package com.belunin.avito_test_task.Services;

import com.belunin.avito_test_task.Repositories.OrganisationResponsibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AuthorizationService {
    private final OrganisationResponsibleRepository organizationResponsibleRepository;
    private final BidService bidService;

    @Autowired
    public AuthorizationService(
            OrganisationResponsibleRepository organizationResponsibleRepository,
            BidService bidService
    ) {
        this.organizationResponsibleRepository = organizationResponsibleRepository;
        this.bidService = bidService;
    }

    public boolean checkAuthorization(UUID userId, UUID bidId) {
        return bidService.getBidById(bidId)
                .map(bid -> {
                    UUID organizationId = bid.getTender().getOrganization().getId();
                    return organizationResponsibleRepository.existsByUserIdAndOrganizationId(userId, organizationId);
                })
                .orElse(false);
    }
}

