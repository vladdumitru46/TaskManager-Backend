package com.example.taskManager.data.task;

import com.example.taskManager.models.project.Project;
import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findAllByProject(Project project);

    Optional<Task> findByUniqueName(String uniqueName);

    Optional<List<Task>> findAllByUser(User user);
}
