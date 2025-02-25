package com.example.taskManager.controller.request;

public record AddTaskRequest(String name, String description, String projectName, String username,
                             Integer numberOfHoursToComplete) {
}
