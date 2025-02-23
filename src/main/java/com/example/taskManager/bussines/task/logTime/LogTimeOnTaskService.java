package com.example.taskManager.bussines.task.logTime;

import com.example.taskManager.data.task.logTime.LogTimeOnTaskRepository;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.task.logTime.LogTimeOnTask;
import com.example.taskManager.models.user.User;
import com.example.taskManager.validator.exceptions.NoLogsForUserException;
import com.example.taskManager.validator.exceptions.NoLogsOnTaskException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
