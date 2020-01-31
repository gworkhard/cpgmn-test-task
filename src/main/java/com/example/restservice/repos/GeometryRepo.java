package com.example.restservice.repos;

import com.example.restservice.domain.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GeometryRepo extends JpaRepository<Geometry, Long> {

    @Modifying
    @Query("DELETE FROM Geometry WHERE project_id = ?1")
    void deleteAllRelatedToProject(Long project_id);
}
