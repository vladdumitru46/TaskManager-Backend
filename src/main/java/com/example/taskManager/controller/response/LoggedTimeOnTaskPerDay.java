package com.example.taskManager.controller.response;

import com.example.taskManager.models.task.Task;

import java.time.LocalDate;

public record LoggedTimeOnTaskPerDay(Task task, Integer timeLogged, LocalDate day) {
}
