package com.belunin.avito_test_task.Controllers;

import com.belunin.avito_test_task.Models.Dtos.OrganizationDTO;
import com.belunin.avito_test_task.Models.Organization;
import com.belunin.avito_test_task.Models.OrganizationType;
import com.belunin.avito_test_task.Services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        List<OrganizationDTO> dtos = organizationService.findAllOrganizations()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable UUID organizationId) {
        Organization organization = organizationService.findOrganizationById(organizationId);

        return ResponseEntity.ok(convertToDTO(organization));
    }

    @PostMapping("/new")
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO organizationDto) {
        Organization organization = convertToEntity(organizationDto);
        organization = organizationService.createOrganization(organization);

        return new ResponseEntity<>(convertToDTO(organization), HttpStatus.CREATED);
    }

    @PatchMapping("/{organizationId}/edit")
    public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable UUID organizationId, @RequestBody OrganizationDTO organizationDto) {
        Organization organization = convertToEntity(organizationDto);
        Optional<Organization> updatedOrganization = organizationService.updateOrganization(organizationId, organization);

        return updatedOrganization
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ping")
    public ResponseEntity<String> pingServer() {
        return ResponseEntity.ok("ok");
    }

    private OrganizationDTO convertToDTO(Organization organization) {
        OrganizationDTO organizationDto = new OrganizationDTO();
        organizationDto.setId(organization.getId());
        organizationDto.setName(organization.getName());
        organizationDto.setDescription(organization.getDescription());
        organizationDto.setType(organization.getType().name());
        organizationDto.setCreatedAt(organization.getCreatedAt());
        organizationDto.setUpdatedAt(organization.getUpdatedAt());

        return organizationDto;
    }

    private Organization convertToEntity(OrganizationDTO organizationDto) {
        Organization organization = new Organization();
        organization.setId(organizationDto.getId());
        organization.setName(organizationDto.getName());
        organization.setDescription(organizationDto.getDescription());
        organization.setType(convertOrganizationType(organizationDto.getType()));
        organization.setCreatedAt(organizationDto.getCreatedAt());
        organization.setUpdatedAt(organizationDto.getUpdatedAt());

        return organization;
    }


    private OrganizationType convertOrganizationType(String type) {
        if (type == null) {
            return null;
        }
        try {
            return OrganizationType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid organization type: " + type, ex);
        }
    }
}
