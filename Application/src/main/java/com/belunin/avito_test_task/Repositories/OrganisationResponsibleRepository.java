package com.belunin.avito_test_task.Repositories;

import com.belunin.avito_test_task.Models.OrganisationResponsible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganisationResponsibleRepository extends JpaRepository<OrganisationResponsible, UUID> {
    @Query("SELECT COUNT(or) FROM OrganisationResponsible or WHERE or.organization.id = :organizationId")
    int countByOrganizationId(UUID organizationId);
    boolean existsByUserIdAndOrganizationId(UUID userId, UUID organizationId);
}

