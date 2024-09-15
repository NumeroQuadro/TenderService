package com.belunin.avito_test_task.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="approval")
@Getter
@Setter
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private UUID bidId;
    @Column(nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private boolean isApproved;
}

