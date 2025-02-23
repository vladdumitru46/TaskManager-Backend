package com.example.taskManager.controller.controllers.task;

import com.example.taskManager.bussines.project.ProjectService;
import com.example.taskManager.bussines.task.TaskService;
import com.example.taskManager.bussines.user.UserService;
import com.example.taskManager.bussines.user.UserSessionService;
import com.example.taskManager.controller.request.AddTaskRequest;
import com.example.taskManager.controller.request.AssignTaskRequest;
import com.example.taskManager.controller.request.UpdateTaskRequest;
import com.example.taskManager.models.project.Project;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.task.TaskStatus;
import com.example.taskManager.models.user.User;
import com.example.taskManager.models.user.UserSession;
import com.example.taskManager.validator.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserSessionService userSessionService;
    private final UserService userService;

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

    @GetMapping("/getAllForUser")
    public ResponseEntity<?> getAllForUser(@RequestParam String token) {
        try {
            UserSession userSession = userSessionService.getByToken(token);
            User user = userSession.getUser();
            List<Task> taskList = taskService.getAlLForUser(user);
//            List<Task> taskList = taskToUserList.stream()
//                    .map(TaskToUser::getTask)
//                    .toList();
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (UserSessionNotFoundException | NoTaskAssignedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignTask(@RequestBody AssignTaskRequest assignTaskRequest) {
        try {
            UserSession userSession = userSessionService.getByToken(assignTaskRequest.token());
            User user = userSession.getUser();
            Task task = taskService.getByUniqueName(assignTaskRequest.uniqueTaskName());
            task.setUser(user);
            taskService.assignTask(task);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserSessionNotFoundException | TaskDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getByUniqueName")
    public ResponseEntity<?> getByUniqueName(@RequestParam String uniqueName) {
        try {
            Task task = taskService.getByUniqueName(uniqueName);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/statuses")
    public ResponseEntity<?> getAllStatuses() {
        List<String> statusList = new ArrayList<>();
        for (var status : TaskStatus.values()) {
            statusList.add(status.toString());
        }
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest) {
        try {
            User user = userService.getByUsername(updateTaskRequest.username());
            Task task = taskService.getByUniqueName(updateTaskRequest.uniqueName());
            task.setTaskStatus(TaskStatus.valueOf(updateTaskRequest.status()));
            task.setUser(user);
            task.setName(updateTaskRequest.name());
            task.setDescription(updateTaskRequest.description());
            task.setNumberOfHoursSpent(updateTaskRequest.timeSpent());
            taskService.update(task);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException | TaskDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
