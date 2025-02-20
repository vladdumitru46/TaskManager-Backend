package com.example.taskManager.models.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "Project")
@Table(
        name = "projects",
        uniqueConstraints = {
                @UniqueConstraint(name = "project_name_unique", columnNames = "name")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
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
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @Column(
            name = "start_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate startDate;
    @Column(
            name = "end_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate endDate;

    public Project(String name, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
