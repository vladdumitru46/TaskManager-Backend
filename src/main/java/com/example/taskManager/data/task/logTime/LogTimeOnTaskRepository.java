package com.example.taskManager.data.task.logTime;

import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.task.logTime.LogTimeOnTask;
import com.example.taskManager.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogTimeOnTaskRepository extends JpaRepository<LogTimeOnTask, Long> {

    Optional<List<LogTimeOnTask>> getAllByUser(User user);

    Optional<List<LogTimeOnTask>> getAllByTask(Task task);

}
