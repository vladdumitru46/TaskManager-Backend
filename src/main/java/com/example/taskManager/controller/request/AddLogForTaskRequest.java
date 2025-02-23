package com.example.taskManager.controller.request;

import java.time.LocalDate;

public record AddLogForTaskRequest(String token, Long taskId, String description, Integer numberOfHours,
                                   LocalDate date) {
}
