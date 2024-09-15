package com.belunin.avito_test_task.Controllers;

import com.belunin.avito_test_task.Models.Dtos.TenderDTO;
import com.belunin.avito_test_task.Models.Tender;
import com.belunin.avito_test_task.Services.EmployeeService;
import com.belunin.avito_test_task.Services.OrganizationService;
import com.belunin.avito_test_task.Services.TenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tenders")
public class TenderController {
    private final TenderService tenderService;
    private final EmployeeService employeeService;
    private final OrganizationService organizationService;

    @Autowired
    public TenderController(
            TenderService tenderService,
            EmployeeService employeeService,
            OrganizationService organizationService
    ) {
        this.tenderService = tenderService;
        this.employeeService = employeeService;
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<List<TenderDTO>> getAllTenders() {
        List<TenderDTO> dtos = tenderService.findAllTenders()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/new")
    public ResponseEntity<TenderDTO> createTender(@RequestBody TenderDTO tenderDto) {
        Tender tender = convertToEntity(tenderDto);
        tender = tenderService.createTender(tender);

        return new ResponseEntity<>(convertToDTO(tender), HttpStatus.CREATED);
    }

    @GetMapping("/{tenderId}/status")
    public ResponseEntity<String> getTenderStatus(@PathVariable UUID tenderId) {
        var tender = tenderService.findTenderById(tenderId);
        if (tender != null) {
            return ResponseEntity.ok(tender.getStatus().toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{tenderId}")
    public ResponseEntity<TenderDTO> getTenderById(@PathVariable UUID tenderId) {
        Tender tender = tenderService.findTenderById(tenderId);

        return ResponseEntity.ok(convertToDTO(tender));
    }


    @GetMapping("/my")
    public ResponseEntity<List<TenderDTO>> getTendersByUsername(@RequestParam String username) {
        List<Tender> tenders = tenderService.findTendersByUsername(username);
        List<TenderDTO> tenderDTOs = tenders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tenderDTOs);
    }

    @PatchMapping("/{tenderId}/edit")
    public ResponseEntity<TenderDTO> updateTender(@PathVariable UUID tenderId, @RequestBody TenderDTO tenderDto) {
        Tender tender = convertToEntity(tenderDto);
        Optional<Tender> updatedTender = tenderService.updateTender(tenderId, tender);

        return updatedTender
                .map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{tenderId}/rollback/{version}")
    public ResponseEntity<TenderDTO> rollbackTenderVersion(@PathVariable UUID tenderId, @PathVariable Integer version) {
        Optional<Tender> rolledBackTender = tenderService.rollbackTender(tenderId, version);

        return rolledBackTender
                .map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Tender convertToEntity(TenderDTO tenderDto) {
        Tender tender = new Tender();
        tender.setId(tenderDto.getId());
        tender.setName(tenderDto.getName());
        tender.setCreator(employeeService.getUserById(tenderDto.getCreatorId()));
        tender.setOrganization(organizationService.findOrganizationById(tenderDto.getOrganizationId()));
        tender.setDescription(tenderDto.getDescription());
        tender.setStatus(tenderDto.getStatus());
        tender.setCreatedAt(tenderDto.getCreatedAt());
        tender.setUpdatedAt(tenderDto.getUpdatedAt());
        tender.setVersion(tenderDto.getVersion());

        return tender;
    }

    private TenderDTO convertToDTO(Tender tender) {
        TenderDTO tenderDto = new TenderDTO();
        tenderDto.setId(tender.getId());
        tenderDto.setName(tender.getName());
        tenderDto.setCreatorId(tender.getCreator().getId());
        tenderDto.setOrganizationId(tender.getOrganization().getId());
        tenderDto.setDescription(tender.getDescription());
        tenderDto.setStatus(tender.getStatus());
        tenderDto.setCreatedAt(tender.getCreatedAt());
        tenderDto.setUpdatedAt(tender.getUpdatedAt());
        tenderDto.setVersion(tender.getVersion());

        return tenderDto;
    }
}
