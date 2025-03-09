package com.example.taskManager.data.task.logTime;

import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.task.logTime.LogTimeOnTask;
import com.example.taskManager.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogTimeOnTaskRepository extends JpaRepository<LogTimeOnTask, Long> {

    Optional<List<LogTimeOnTask>> getAllByUser(User user);

    Optional<List<LogTimeOnTask>> getAllByTask(Task task);

    @Query("SELECT logTime FROM LogTimeOnTask logTime WHERE logTime.user = :user AND logTime.logDate BETWEEN :startTime AND :endTime")
    Optional<List<LogTimeOnTask>> getAllByUserAndLogDate(User user, LocalDate startTime, LocalDate endTime);

    Optional<LogTimeOnTask> getAllByTaskAndLogDate(Task task, LocalDate date);

}
