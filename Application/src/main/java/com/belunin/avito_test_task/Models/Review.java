package com.belunin.avito_test_task.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.UUID;
import java.util.Date;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;
    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Employee reviewer;
    @Column(nullable = false)
    private String reviewText;
    @CreationTimestamp
    private Date createdAt = new Date();
}
