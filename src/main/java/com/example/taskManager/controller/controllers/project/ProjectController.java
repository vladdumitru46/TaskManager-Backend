package com.example.taskManager.controller.controllers.project;

import com.example.taskManager.bussines.project.ProjectService;
import com.example.taskManager.controller.request.AddProjectRequest;
import com.example.taskManager.models.project.Project;
import com.example.taskManager.validator.exceptions.ProjectDoesNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/project")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getProjectByName(@RequestParam String name) {
        try {
            Project project = projectService.getByName(name);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (ProjectDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(projectService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AddProjectRequest addProjectRequest) {
        Project project = new Project(addProjectRequest.name(), addProjectRequest.description(), LocalDate.now(), addProjectRequest.endDate());
        projectService.add(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
