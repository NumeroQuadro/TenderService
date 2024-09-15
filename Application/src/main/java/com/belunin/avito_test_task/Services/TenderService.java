package com.belunin.avito_test_task.Services;

import com.belunin.avito_test_task.Exceptions.ResourceNotFoundException;
import com.belunin.avito_test_task.Models.Tender;
import com.belunin.avito_test_task.Models.TenderStatus;
import com.belunin.avito_test_task.Repositories.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenderService {
    private final TenderRepository tenderRepository;

    @Autowired
    public TenderService(TenderRepository tenderRepository) {
        this.tenderRepository = tenderRepository;
    }

    public List<Tender> findAllTenders() {
        return tenderRepository.findAll();
    }

    public Tender findTenderById(UUID tenderId) {
        return tenderRepository.findById(tenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Tender not found with id: " + tenderId));
    }

    public Tender createTender(Tender tender) {
        tender.setStatus(TenderStatus.CREATED);
        return tenderRepository.save(tender);
    }

    @Transactional
    public Optional<Tender> updateTender(UUID id, Tender newTenderData) {
        return tenderRepository.findById(id).map(tender -> {
            tender.setName(newTenderData.getName());
            tender.setDescription(newTenderData.getDescription());
            tender.setStatus(newTenderData.getStatus());
            tender.setUpdatedAt(newTenderData.getUpdatedAt());
            tender.setVersion(newTenderData.getVersion());

            return tenderRepository.save(tender);
        });
    }

    public List<Tender> findTendersByUsername(String username) {
        return tenderRepository.findByCreatorUsername(username);
    }

    @Transactional
    public Optional<Tender> rollbackTender(UUID tenderId, int version) {
        return tenderRepository.findById(tenderId).map(tender -> {
            if (tender.getVersion() > version) {
                tender.setVersion(version);
                tender.setUpdatedAt(new java.util.Date());

                return tenderRepository.save(tender);
            } else {
                throw new IllegalArgumentException("Cannot rollback to a newer or same version.");
            }
        });
    }
}
