package com.example.taskManager.controller.controllers.task;

import com.example.taskManager.bussines.project.ProjectService;
import com.example.taskManager.bussines.task.TaskService;
import com.example.taskManager.controller.request.AddTaskRequest;
import com.example.taskManager.models.project.Project;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.validator.exceptions.ProjectDoesNotExistException;
import com.example.taskManager.validator.exceptions.TaskDoesNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAllByProject(@RequestParam String projectName) {
        try {
            Project project = projectService.getByName(projectName);
            List<Task> taskList = taskService.getAllByProject(project);
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (TaskDoesNotExistException | ProjectDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AddTaskRequest addTaskRequest) {
        try {
            Project project = projectService.getByName(addTaskRequest.projectName());
            taskService.add(new Task(addTaskRequest.name(), addTaskRequest.description(), project, addTaskRequest.numberOfHoursToComplete()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProjectDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
