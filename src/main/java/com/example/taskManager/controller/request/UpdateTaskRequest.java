package com.example.taskManager.controller.request;

public record UpdateTaskRequest(String name, String description, String uniqueName, String username, Integer timeSpent,
                                String status) {
}
