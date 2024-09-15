package com.belunin.avito_test_task.Models.Dtos;

import com.belunin.avito_test_task.Models.OrganizationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrganizationDTO {
    private UUID id;
    private String name;
    private String description;
    private String type;
    private Date createdAt;
    private Date updatedAt;

    public OrganizationDTO(
            UUID id,
            String name,
            String description,
            String type,
            Date createdAt,
            Date updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
