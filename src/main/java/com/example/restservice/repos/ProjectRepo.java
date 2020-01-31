package com.example.restservice.repos;

import com.example.restservice.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProjectRepo extends JpaRepository<Project, Long> {
}


