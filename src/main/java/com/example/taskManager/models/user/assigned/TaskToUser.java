//package com.example.taskManager.models.user.assigned;
//
//import com.example.taskManager.models.task.Task;
//import com.example.taskManager.models.user.User;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity(name = "TaskToUser")
//@Table(
//        name = "task_to_user"
//)
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class TaskToUser {
//    @Id
//    @SequenceGenerator(
//            name = "task_to_user_sequence",
//            sequenceName = "user_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "task_to_user_sequence"
//    )
//    @Column(
//            name = "id",
//            updatable = false
//    )
//    private Long id;
//    @ManyToOne
//    @JoinColumn(
//            nullable = false,
//            name = "user_id",
//            referencedColumnName = "id"
//    )
//    private User user;
//    @ManyToOne
//    @JoinColumn(
//            nullable = false,
//            name = "task_id",
//            referencedColumnName = "id"
//    )
//    private Task task;
//
//    public TaskToUser(User user, Task task) {
//        this.user = user;
//        this.task = task;
//    }
//}
