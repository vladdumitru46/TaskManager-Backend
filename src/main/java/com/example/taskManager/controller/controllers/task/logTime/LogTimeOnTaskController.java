package com.example.taskManager.controller.controllers.task.logTime;

import com.example.taskManager.bussines.task.TaskService;
import com.example.taskManager.bussines.task.logTime.LogTimeOnTaskService;
import com.example.taskManager.bussines.user.UserService;
import com.example.taskManager.bussines.user.UserSessionService;
import com.example.taskManager.controller.request.AddLogForTaskRequest;
import com.example.taskManager.controller.request.GetAllLoggedTimeForUserInPeriodOfTime;
import com.example.taskManager.controller.response.AllLoggedTimeForUserResponse;
import com.example.taskManager.controller.response.LoggedTimeOnTaskPerDay;
import com.example.taskManager.controller.response.TotalLoggedTimeOnTaskPerDay;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.task.logTime.LogTimeOnTask;
import com.example.taskManager.models.user.User;
import com.example.taskManager.validator.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/logTimeOnTask")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class LogTimeOnTaskController {
    private final LogTimeOnTaskService logTimeOnTaskService;
    private final UserSessionService userSessionService;
    private final TaskService taskService;
    private final UserService userService;

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

    @GetMapping("/userInPeriodOfTime")
    public ResponseEntity<?> getALlForUserInPeriodOfTime(@RequestBody GetAllLoggedTimeForUserInPeriodOfTime getAllLoggedTimeForUserInPeriodOfTime) {
        try {
            User user = userService.getByUsername(getAllLoggedTimeForUserInPeriodOfTime.username());

            List<LogTimeOnTask> logTimeOnTaskList = logTimeOnTaskService.getAllByUserInPeriodOfTime(user,
                    getAllLoggedTimeForUserInPeriodOfTime.startDate(), getAllLoggedTimeForUserInPeriodOfTime.endDate());

            List<LoggedTimeOnTaskPerDay> loggedTimeOnTaskPerDayList = logTimeOnTaskList.stream()
                    .map(logTimeOnTask -> new LoggedTimeOnTaskPerDay(logTimeOnTask.getTask(),
                            logTimeOnTask.getLogTime(), logTimeOnTask.getLogDate()))
                    .toList();

            List<TotalLoggedTimeOnTaskPerDay> totalLoggedTimeOnTaskPerDayList = logTimeOnTaskService.getAllHoursLoggenByDay(logTimeOnTaskList);

            Integer totalLoggedTime = logTimeOnTaskService.getTotalLoggedTime(logTimeOnTaskList);

            return new ResponseEntity<>(new AllLoggedTimeForUserResponse(loggedTimeOnTaskPerDayList, totalLoggedTime, totalLoggedTimeOnTaskPerDayList), HttpStatus.OK);
        } catch (UserNotFoundException | NoLogsForUserException e) {
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
            task.setNumberOfHoursSpent(task.getNumberOfHoursSpent() + addLogForTaskRequest.numberOfHours());
            taskService.update(task);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskDoesNotExistException | UserSessionNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
