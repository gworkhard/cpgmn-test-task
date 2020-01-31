package com.example.restservice.repos;

import com.example.restservice.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AttributeRepo extends JpaRepository<Attribute, Long> {

    @Modifying
    @Query("DELETE FROM Attribute WHERE project_id = ?1")
    void deleteAllRelatedToProject(Long id);
}
