package com.example.restservice.service;

import com.example.restservice.domain.Project;
import com.example.restservice.repos.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final GeometryService geometryService;
    private final AttributeService attributeService;

    @Autowired
    public ProjectService(ProjectRepo projectRepo, GeometryService geometryService, AttributeService attributeService) {
        this.projectRepo = projectRepo;
        this.geometryService = geometryService;
        this.attributeService = attributeService;
    }

    public Project create(String name) {
        Project project = new Project(name);
        Project projectFromDB = projectRepo.save(project);
        geometryService.bulkCreate(project);
        attributeService.bulkCreate(project);
        return projectFromDB;
    }

    @Transactional
    public void delete(Project project) {
        projectRepo.delete(project);
    }
}
