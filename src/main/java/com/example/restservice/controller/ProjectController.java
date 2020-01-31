package com.example.restservice.controller;

import com.example.restservice.domain.Project;
import com.example.restservice.exceptions.BadRequestException;
import com.example.restservice.exceptions.NotFoundException;
import com.example.restservice.repos.ProjectRepo;
import com.example.restservice.service.AttributeService;
import com.example.restservice.service.GeometryService;
import com.example.restservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping(path = "/api/projects")
public class ProjectController {

    private final ProjectRepo projectRepo;
    private final ProjectService projectService;
    private final GeometryService geometryService;
    private final AttributeService attributeService;

    @Autowired
    public ProjectController(ProjectRepo projectRepo, ProjectService projectService, GeometryService geometryService, AttributeService attributeService) {
        this.projectRepo = projectRepo;
        this.projectService = projectService;
        this.geometryService = geometryService;
        this.attributeService = attributeService;
    }

    @GetMapping
    public Iterable<Project> list() {
        return projectRepo.findAll();
    }

    @GetMapping("{id}")
    public Project getOne(@PathVariable("id") Optional<Project> project) {
        return project.orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Project create(@RequestBody String body) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader(body));
        String name = properties.getProperty("name");
        if (name != null) {
            return projectService.create(name);
        } else throw new BadRequestException();

    }

    @PutMapping("{id}")
    public boolean update(@PathVariable("id") Project projectFromDB, @RequestBody String newProjectName) {
        if (projectFromDB == null) {
            throw new NotFoundException();
        }
        projectFromDB.setName(newProjectName);
        projectRepo.save(projectFromDB);
        return true;
    }



    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Project project) {
        attributeService.bulkDelete(project);
        geometryService.bulkDelete(project);
        projectService.delete(project);
    }

}
