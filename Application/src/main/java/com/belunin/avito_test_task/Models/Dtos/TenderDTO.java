package com.belunin.avito_test_task.Models.Dtos;

import com.belunin.avito_test_task.Models.TenderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class TenderDTO {
    private UUID id;
    private String name;
    private String description;
    private TenderStatus status;
    private UUID organizationId;
    private UUID creatorId;
    private Integer version;
    private Date createdAt;
    private Date updatedAt;

    public TenderDTO() { }

    public TenderDTO(
            UUID id,
            String name,
            String description,
            TenderStatus status,
            UUID organizationId,
            UUID creatorId,
            Integer version,
            Date createdAt,
            Date updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.organizationId = organizationId;
        this.creatorId = creatorId;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

