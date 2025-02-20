package com.example.taskManager.models.task;

import com.example.taskManager.models.project.Project;
import com.example.taskManager.models.user.Role;
import com.example.taskManager.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Task")
@Table(
        name = "tasks",
        uniqueConstraints = {
                @UniqueConstraint(name = "task_uniqueName_unique", columnNames = "unique_name")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            name = "unique_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String uniqueName;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "project_id",
            referencedColumnName = "id"
    )
    private Project project;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
    @Column(
            name = "number_of_hours_to_complete",
            nullable = false
//            columnDefinition = "NUMBER"
    )
    private Integer numberOfHoursToComplete;
    @Column(
            name = "number_of_hours_spent",
            nullable = false
    )
    private Integer numberOfHoursSpent;
    @Column(
            name = "number_of_hours_remaining",
            nullable = false
    )
    private Integer numberOfHoursRemaining;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    public Task(String name, String description, Project project, Integer numberOfHoursToComplete) {
        this.name = name;
        this.description = description;
        this.project = project;
        this.numberOfHoursToComplete = numberOfHoursToComplete;
    }
}
