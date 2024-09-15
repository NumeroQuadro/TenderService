package com.belunin.avito_test_task.Controllers;

import com.belunin.avito_test_task.Models.Dtos.OrganisationResponsibleDTO;
import com.belunin.avito_test_task.Models.OrganisationResponsible;
import com.belunin.avito_test_task.Services.EmployeeService;
import com.belunin.avito_test_task.Services.OrganisationResponsibleService;
import com.belunin.avito_test_task.Services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organisation-responsible")
public class OrganisationResponsibleController {
    private final OrganisationResponsibleService organisationResponsibleService;
    private final EmployeeService employeeService;
    private final OrganizationService organizationService;

    @Autowired
    public OrganisationResponsibleController(
            OrganisationResponsibleService organisationResponsibleService,
            EmployeeService employeeService,
            OrganizationService organizationService) {
        this.organisationResponsibleService = organisationResponsibleService;
        this.employeeService = employeeService;
        this.organizationService = organizationService;
    }

    @PostMapping("/new")
    public ResponseEntity<OrganisationResponsibleDTO> createOrganisationResponsible(
            @RequestBody OrganisationResponsibleDTO dto
    ) {
        OrganisationResponsible savedOrganisationResponsible = organisationResponsibleService.saveOrganisationResponsible(convertToEntity(dto));

        return new ResponseEntity<>(convertToDTO(savedOrganisationResponsible), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrganisationResponsibleDTO>> getAllOrganisationResponsible() {
        List<OrganisationResponsibleDTO> dtos = organisationResponsibleService.findAllOrganisationResponsibles()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrganisationResponsible> updateOrganisationResponsible(
            @RequestBody OrganisationResponsibleDTO organisationResponsibleDTO
    ) {
        return organisationResponsibleService
                .updateOrganisationResponsible(
                        organisationResponsibleDTO.getId(), organisationResponsibleDTO.getOrganizationId(), organisationResponsibleDTO.getUserId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganisationResponsible(@PathVariable UUID id) {
        organisationResponsibleService.deleteOrganisationResponsible(id);

        return ResponseEntity.ok().build();
    }

    private OrganisationResponsibleDTO convertToDTO(OrganisationResponsible entity) {
        OrganisationResponsibleDTO dto = new OrganisationResponsibleDTO();
        dto.setId(entity.getId());
        dto.setOrganizationId(entity.getOrganization().getId());
        dto.setUserId(entity.getUser().getId());

        return dto;
    }

    private OrganisationResponsible convertToEntity(OrganisationResponsibleDTO dto) {
        OrganisationResponsible entity = new OrganisationResponsible();
        entity.setOrganization(organizationService.findOrganizationById(dto.getOrganizationId()));
        entity.setUser(employeeService.getUserById(dto.getUserId()));

        return entity;
    }
}