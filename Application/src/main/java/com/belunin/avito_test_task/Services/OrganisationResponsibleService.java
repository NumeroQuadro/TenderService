package com.belunin.avito_test_task.Services;

import com.belunin.avito_test_task.Models.OrganisationResponsible;
import com.belunin.avito_test_task.Repositories.OrganisationResponsibleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganisationResponsibleService {
    private final OrganisationResponsibleRepository organisationResponsibleRepository;
    private final OrganizationService organizationService;
    private final EmployeeService employeeService;

    @Autowired
    public OrganisationResponsibleService(
            OrganisationResponsibleRepository organisationResponsibleRepository,
            OrganizationService organizationService,
            EmployeeService employeeService) {
        this.organisationResponsibleRepository = organisationResponsibleRepository;
        this.organizationService = organizationService;
        this.employeeService = employeeService;
    }

    public OrganisationResponsible saveOrganisationResponsible(
            OrganisationResponsible organisationResponsible) {
        return organisationResponsibleRepository.save(organisationResponsible);
    }

    public Optional<OrganisationResponsible> findOrganisationResponsibleById(UUID id) {
        return organisationResponsibleRepository.findById(id);
    }

    public int getResponsibleCount(UUID organizationId) {
        return organisationResponsibleRepository.countByOrganizationId(organizationId);
    }

    public List<OrganisationResponsible> findAllOrganisationResponsibles() {
        return organisationResponsibleRepository.findAll();
    }

    public void deleteOrganisationResponsible(UUID id) {
        organisationResponsibleRepository.deleteById(id);
    }

    @Transactional
    public Optional<OrganisationResponsible> updateOrganisationResponsible(UUID id, UUID organizationId, UUID userId) {
        return organisationResponsibleRepository.findById(id)
                .map(existingOrgResponsible -> {
                    existingOrgResponsible.setOrganization(organizationService.findOrganizationById(organizationId));
                    existingOrgResponsible.setUser(employeeService.getUserById(userId));

                    return organisationResponsibleRepository.save(existingOrgResponsible);
                });
    }
}
