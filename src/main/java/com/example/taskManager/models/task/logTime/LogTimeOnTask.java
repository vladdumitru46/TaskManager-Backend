package com.example.taskManager.models.task.logTime;

import com.example.taskManager.models.task.Task;
import com.example.taskManager.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "LogTimeOnTask")
@Table(
        name = "log_time_on_task"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogTimeOnTask {
    @Id
    @SequenceGenerator(
            name = "log_time_on_task_sequence",
            sequenceName = "log_time_on_task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "log_time_on_task_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "task_id",
            referencedColumnName = "id"
    )
    private Task task;
    @Column(
            name = "log_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate logDate;
    @Column(
            name = "log_time",
            nullable = false
    )
    private Integer logTime;

    public LogTimeOnTask(String description, User user, Task task, LocalDate logDate, Integer logTime) {
        this.description = description;
        this.user = user;
        this.task = task;
        this.logDate = logDate;
        this.logTime = logTime;
    }
}
