package com.belunin.avito_test_task.Models.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ReviewDTO {
    private UUID id;
    private UUID bidId;
    private UUID reviewerId;
    private String reviewText;
    private Date createdAt;

    public ReviewDTO() { }

    public ReviewDTO(
            UUID id,
            UUID bidId,
            UUID reviewerId,
            String reviewText
    ) {
        this.id = id;
        this.bidId = bidId;
        this.reviewerId = reviewerId;
        this.reviewText = reviewText;
        this.createdAt = new Date();
    }
}

