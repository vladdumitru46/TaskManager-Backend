package com.example.taskManager.models.user.assigned;

import com.example.taskManager.models.project.Project;
import com.example.taskManager.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ProjectToUser")
@Table(
        name = "project_to_user"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectToUser {
    @Id
    @SequenceGenerator(
            name = "project_to_user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_to_user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
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
            name = "project_id",
            referencedColumnName = "id"
    )
    private Project project;
}
