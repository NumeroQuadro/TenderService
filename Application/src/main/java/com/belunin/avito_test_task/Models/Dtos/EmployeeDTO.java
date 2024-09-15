package com.belunin.avito_test_task.Models.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class EmployeeDTO {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;

    public EmployeeDTO() { }

    public EmployeeDTO(
            UUID id,
            String username,
            String firstName,
            String lastName
    ) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

