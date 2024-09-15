package com.belunin.avito_test_task.Services;

import com.belunin.avito_test_task.Exceptions.ResourceNotFoundException;
import com.belunin.avito_test_task.Models.Bid;
import com.belunin.avito_test_task.Models.Organization;
import com.belunin.avito_test_task.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final BidService bidService;

    @Autowired
    public OrganizationService(
            OrganizationRepository organizationRepository,
            BidService bidService
    ) {
        this.organizationRepository = organizationRepository;
        this.bidService = bidService;
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> getOrganizationById(UUID id) {
        return organizationRepository.findById(id);
    }

    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Transactional
    public Optional<Organization> updateOrganization(UUID id, Organization newOrganizationData) {
        return organizationRepository.findById(id).map(org -> {
            org.setName(newOrganizationData.getName());
            org.setDescription(newOrganizationData.getDescription());
            org.setType(newOrganizationData.getType());
            org.setUpdatedAt(newOrganizationData.getUpdatedAt());
            return org;
        });
    }

    public Organization getOrganizationByBid(UUID bidId) {
        Bid bid = bidService.getBidById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found with id: " + bidId));

        return organizationRepository.findById(bid.getOrganization().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + bid.getOrganization().getId()));
    }

    public Organization findOrganizationById(UUID organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + organizationId));
    }

    public List<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    public void deleteOrganization(UUID id) {
        organizationRepository.deleteById(id);
    }
}

