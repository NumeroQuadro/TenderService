package com.belunin.avito_test_task.Models.Dtos;

import com.belunin.avito_test_task.Models.OrganisationResponsible;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrganisationResponsibleDTO {
    private UUID id;
    private UUID organizationId;
    private UUID userId;

    public OrganisationResponsibleDTO() { }

    public OrganisationResponsibleDTO(
            UUID id,
            UUID organizationId,
            UUID userId) {
        this.id = id;
        this.organizationId = organizationId;
        this.userId = userId;
    }
}
