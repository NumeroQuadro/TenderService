package com.belunin.avito_test_task.Models.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ApprovalDTO {
    private UUID id;
    private UUID bidId;
    private UUID userId;
    private boolean isApproved;

    public ApprovalDTO() { }

    public ApprovalDTO(
            UUID id,
            UUID bidId,
            UUID userId,
            boolean isApproved
    ) {
        this.id = id;
        this.bidId = bidId;
        this.userId = userId;
        this.isApproved = isApproved;
    }
}
