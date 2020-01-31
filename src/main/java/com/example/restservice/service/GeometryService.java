package com.example.restservice.service;

import com.example.restservice.domain.Geometry;
import com.example.restservice.domain.Project;
import com.example.restservice.repos.GeometryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GeometryService {
    private final int NUMBER_OF_GEOMETRIES_ON_EACH_PROJECT = 100000;
    private final GeometryRepo geometryRepo;

    @Autowired
    public GeometryService(GeometryRepo geometryRepo) {
        this.geometryRepo = geometryRepo;
    }

    @Transactional
    public void bulkCreate(Project project) {
        for (int i = 0; i<NUMBER_OF_GEOMETRIES_ON_EACH_PROJECT; i++) {
            String geometryName = "Geom" + i;
            Geometry geometry = new Geometry(geometryName);
            geometry.setProject(project);
            geometryRepo.save(geometry);
        }
    }

    public Geometry create(String name, Project project) {
        Geometry geometry = new Geometry(name);
        geometry.setProject(project);
        return geometryRepo.save(geometry);
    }

    @Transactional
    public void bulkDelete(Project project) {
        geometryRepo.deleteAllRelatedToProject(project.getId());
    }
}
