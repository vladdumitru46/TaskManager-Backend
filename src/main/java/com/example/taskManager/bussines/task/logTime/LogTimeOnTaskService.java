package com.example.taskManager.bussines.task.logTime;

import com.example.taskManager.controller.response.TotalLoggedTimeOnTaskPerDay;
import com.example.taskManager.data.task.logTime.LogTimeOnTaskRepository;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.task.logTime.LogTimeOnTask;
import com.example.taskManager.models.user.User;
import com.example.taskManager.validator.exceptions.NoLogsForUserException;
import com.example.taskManager.validator.exceptions.NoLogsOnTaskException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service("logTimeOnTask")
public class LogTimeOnTaskService {

    private final LogTimeOnTaskRepository logTimeOnTaskRepository;

    public void add(LogTimeOnTask logTimeOnTask) {
        logTimeOnTaskRepository.save(logTimeOnTask);
    }

    public List<LogTimeOnTask> getAllByUser(User user) throws NoLogsForUserException {
        return logTimeOnTaskRepository.getAllByUser(user)
                .orElseThrow(() -> new NoLogsForUserException("This user does not have any logs yet!"));
    }

    public List<LogTimeOnTask> getAllByTask(Task task) throws NoLogsOnTaskException {
        return logTimeOnTaskRepository.getAllByTask(task)
                .orElseThrow(() -> new NoLogsOnTaskException("This task does not have any logs yet!"));
    }

    public void delete(LogTimeOnTask logTimeOnTask) {
        logTimeOnTaskRepository.delete(logTimeOnTask);
    }

    @Transactional
    public void update(LogTimeOnTask logTimeOnTask) {
        logTimeOnTask.setDescription(logTimeOnTask.getDescription());
        logTimeOnTask.setLogTime(logTimeOnTask.getLogTime());
        logTimeOnTask.setLogDate(logTimeOnTask.getLogDate());
    }

    public List<LogTimeOnTask> getAllByUserInPeriodOfTime(User user, LocalDate startDate, LocalDate endDate) throws NoLogsForUserException {
        return logTimeOnTaskRepository.getAllByUserAndLogDate(user, startDate, endDate)
                .orElseThrow(() -> new NoLogsForUserException("There are no logs in that period of time for this user"));
    }

    public List<TotalLoggedTimeOnTaskPerDay> getAllHoursLoggenByDay(List<LogTimeOnTask> logTimeOnTaskList) {
        List<TotalLoggedTimeOnTaskPerDay> totalLoggedTimeOnTaskPerDayList = new ArrayList<>();
        Map<LocalDate, Integer> mapOfTimeLoggedPerDay = new HashMap<>();
        for (var logTime : logTimeOnTaskList) {
            if (!mapOfTimeLoggedPerDay.containsKey(logTime.getLogDate())) {
                mapOfTimeLoggedPerDay.put(logTime.getLogDate(), logTime.getLogTime());
            } else {
                mapOfTimeLoggedPerDay.put(logTime.getLogDate(), mapOfTimeLoggedPerDay.get(logTime.getLogDate()) + logTime.getLogTime());
            }
        }

        for (var logTime : mapOfTimeLoggedPerDay.entrySet()) {
            totalLoggedTimeOnTaskPerDayList.add(new TotalLoggedTimeOnTaskPerDay(logTime.getKey(), logTime.getValue()));
        }

        return totalLoggedTimeOnTaskPerDayList;
    }

    public Integer getTotalLoggedTime(List<LogTimeOnTask> logTimeOnTaskList) {
        return logTimeOnTaskList.stream()
                .mapToInt(LogTimeOnTask::getLogTime)
                .sum();

    }
}
