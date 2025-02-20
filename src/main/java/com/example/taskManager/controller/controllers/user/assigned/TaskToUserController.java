package com.example.taskManager.controller.controllers.user.assigned;

import com.example.taskManager.bussines.task.TaskService;
import com.example.taskManager.bussines.user.UserService;
import com.example.taskManager.bussines.user.UserSessionService;
import com.example.taskManager.controller.request.AssignTaskRequest;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.user.User;
import com.example.taskManager.models.user.UserSession;
import com.example.taskManager.validator.exceptions.NoTaskAssignedException;
import com.example.taskManager.validator.exceptions.TaskDoesNotExistException;
import com.example.taskManager.validator.exceptions.UserSessionNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/taskToUser")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TaskToUserController {

    //    private final TaskToUserService taskToUserService;
    private final TaskService taskService;
    private final UserService userService;
    private final UserSessionService userSessionService;

    @GetMapping
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
}
