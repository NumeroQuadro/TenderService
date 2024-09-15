package com.belunin.avito_test_task.Repositories;

import com.belunin.avito_test_task.Models.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TenderRepository extends JpaRepository<Tender, UUID> {
    List<Tender> findByCreatorUsername(String username);
}
