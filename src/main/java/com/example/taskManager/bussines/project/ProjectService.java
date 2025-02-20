package com.example.taskManager.bussines.project;

import com.example.taskManager.data.project.ProjectRepository;
import com.example.taskManager.models.project.Project;
import com.example.taskManager.validator.exceptions.ProjectDoesNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("projectService")
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public void add(Project project) {
        projectRepository.save(project);
    }

    public Project getByName(String name) throws ProjectDoesNotExistException {
        return projectRepository.findByName(name)
                .orElseThrow(() -> new ProjectDoesNotExistException("There is no project with this name: " + name));
    }

    public Project getById(Long id) throws ProjectDoesNotExistException {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectDoesNotExistException("There is no project with this id: " + id));
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }


}
