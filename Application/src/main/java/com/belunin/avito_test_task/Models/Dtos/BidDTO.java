package com.belunin.avito_test_task.Models.Dtos;

import com.belunin.avito_test_task.Models.BidStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class BidDTO {
    private UUID id;
    private UUID tenderId;
    private UUID organizationId;
    private UUID creatorId;
    private String name;
    private String description;
    private BidStatus status;
    private Integer version;
    private Date createdAt;
    private Date updatedAt;

    public BidDTO() { }

    public BidDTO(
            UUID id,
            UUID tenderId,
            UUID organizationId,
            UUID creatorId,
            String name,
            String description,
            BidStatus status,
            Integer version,
            Date createdAt,
            Date updatedAt
    ) {
        this.id = id;
        this.tenderId = tenderId;
        this.organizationId = organizationId;
        this.creatorId = creatorId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}