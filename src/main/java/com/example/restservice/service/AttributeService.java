package com.example.restservice.service;

import com.example.restservice.domain.Attribute;
import com.example.restservice.domain.Project;
import com.example.restservice.repos.AttributeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttributeService {
    private final int NUMBER_OF_ATTRIBUTES_ON_EACH_PROJECT = 100000;
    private final AttributeRepo attributeRepo;

    public AttributeService(AttributeRepo attributeRepo) {
        this.attributeRepo = attributeRepo;
    }

    @Transactional
    public void bulkCreate(Project project) {
        for (int i = 0; i<NUMBER_OF_ATTRIBUTES_ON_EACH_PROJECT; i++) {
            String attributeName = "Attr" + i;
            Attribute attribute = new Attribute(attributeName);
            attribute.setProject(project);
            attributeRepo.save(attribute);
        }
    }

    @Transactional
    public void bulkDelete(Project project) {
        attributeRepo.deleteAllRelatedToProject(project.getId());
    }
}
