package com.belunin.avito_test_task.Models.ResponseTypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidApprovalResponse {
    private boolean isApproved;
    private String message;
    private int currentApprovalCount;
    private int requiredQuorum;

    public BidApprovalResponse(
            boolean isApproved,
            String message,
            int currentApprovalCount,
            int requiredQuorum
    ) {
        this.isApproved = isApproved;
        this.message = message;
        this.currentApprovalCount = currentApprovalCount;
        this.requiredQuorum = requiredQuorum;
    }
}

