package com.example.taskManager.bussines.task;

import com.example.taskManager.controller.response.LoggedTimeOnTaskPerDay;
import com.example.taskManager.data.task.TaskRepository;
import com.example.taskManager.models.project.Project;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.task.TaskStatus;
import com.example.taskManager.models.task.logTime.LogTimeOnTask;
import com.example.taskManager.models.user.User;
import com.example.taskManager.validator.exceptions.NoTaskAssignedException;
import com.example.taskManager.validator.exceptions.TaskDoesNotExistException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("taskService")
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllByProject(Project project) throws TaskDoesNotExistException {
        return taskRepository.findAllByProject(project)
                .orElseThrow(() -> new TaskDoesNotExistException("There is no task assigned to this project"));
    }

    public Long add(Task task) {
        task.setNumberOfHoursRemaining(task.getNumberOfHoursToComplete());
        task.setNumberOfHoursSpent(0);
        task.setTaskStatus(TaskStatus.OPEN);
        task.setUniqueName("");
        taskRepository.save(task);
        return task.getId();
    }

    @Transactional
    public void assignUniqueName(Long taskId) {
        try {
            Task task = getById(taskId);
            task.setUniqueName("TMA-" + task.getId());
        } catch (TaskDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    public Task getById(Long id) throws TaskDoesNotExistException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskDoesNotExistException("There is no task with that id"));
    }

    public Task getByUniqueName(String uniqueName) throws TaskDoesNotExistException {
        return taskRepository.findByUniqueName(uniqueName)
                .orElseThrow(() -> new TaskDoesNotExistException("There is no task with that unique name"));
    }

    public List<Task> getAlLForUser(User user) throws NoTaskAssignedException {
        return taskRepository.findAllByUser(user)
                .orElseThrow(() -> new NoTaskAssignedException("This user does not have any tasks yet!"));
    }

    @Transactional
    public void assignTask(Task task) {
        task.setUser(task.getUser());
    }

    @Transactional
    public void update(Task task) {
        task.setUser(task.getUser());
        task.setName(task.getName());
        task.setDescription(task.getDescription());
        task.setTaskStatus(task.getTaskStatus());
        task.setNumberOfHoursSpent(task.getNumberOfHoursSpent());
        task.setNumberOfHoursRemaining(task.getNumberOfHoursToComplete() - task.getNumberOfHoursSpent());
    }
}
