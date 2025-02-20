//package com.example.taskManager.bussines.user.assigned;
//
//import com.example.taskManager.data.user.assigned.TaskToUserRepository;
//import com.example.taskManager.models.user.User;
//import com.example.taskManager.models.user.assigned.TaskToUser;
//import com.example.taskManager.validator.exceptions.NoTaskAssignedException;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service("taskToUser")
//@AllArgsConstructor
//public class TaskToUserService {
//
//    private final TaskToUserRepository taskToUserRepository;
//
//    public List<TaskToUser> getAlLForUser(User user) throws NoTaskAssignedException {
//        return taskToUserRepository.findAllByUser(user)
//                .orElseThrow(() -> new NoTaskAssignedException("This user does not have any tasks yet!"));
//    }
//
//    public void assignTask(TaskToUser taskToUser) {
//        taskToUserRepository.save(taskToUser);
//    }
//}
