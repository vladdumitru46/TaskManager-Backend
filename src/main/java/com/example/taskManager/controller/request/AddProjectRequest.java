package com.example.taskManager.controller.request;

import java.time.LocalDate;

public record AddProjectRequest(String name, String description, LocalDate endDate) {
}
