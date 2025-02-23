package com.example.taskManager.controller.controllers.task.logTime;

import com.example.taskManager.bussines.task.TaskService;
import com.example.taskManager.bussines.task.logTime.LogTimeOnTaskService;
import com.example.taskManager.bussines.user.UserSessionService;
import com.example.taskManager.controller.request.AddLogForTaskRequest;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.task.logTime.LogTimeOnTask;
import com.example.taskManager.models.user.User;
import com.example.taskManager.validator.exceptions.NoLogsForUserException;
import com.example.taskManager.validator.exceptions.NoLogsOnTaskException;
import com.example.taskManager.validator.exceptions.TaskDoesNotExistException;
import com.example.taskManager.validator.exceptions.UserSessionNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/logTimeOnTask")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class LogTimeOnTaskController {
    private final LogTimeOnTaskService logTimeOnTaskService;
    private final UserSessionService userSessionService;
    private final TaskService taskService;

    @GetMapping("/currentUser")
    public ResponseEntity<?> getAllForCurrentUser(@RequestParam String token) {
        try {
            User user = userSessionService.getByToken(token).getUser();
            List<LogTimeOnTask> logTimeOnTaskList = logTimeOnTaskService.getAllByUser(user);
            return new ResponseEntity<>(logTimeOnTaskList, HttpStatus.OK);
        } catch (UserSessionNotFoundException | NoLogsForUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/task")
    public ResponseEntity<?> getAllForTask(@RequestParam Long taskId) {
        try {
            Task task = taskService.getById(taskId);
            List<LogTimeOnTask> logTimeOnTaskList = logTimeOnTaskService.getAllByTask(task);
            return new ResponseEntity<>(logTimeOnTaskList, HttpStatus.OK);
        } catch (TaskDoesNotExistException | NoLogsOnTaskException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addLog(@RequestBody AddLogForTaskRequest addLogForTaskRequest) {
        try {
            User user = userSessionService.getByToken(addLogForTaskRequest.token()).getUser();
            Task task = taskService.getById(addLogForTaskRequest.taskId());
            logTimeOnTaskService.add(new LogTimeOnTask(addLogForTaskRequest.description(), user, task, addLogForTaskRequest.date(), addLogForTaskRequest.numberOfHours()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskDoesNotExistException | UserSessionNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
