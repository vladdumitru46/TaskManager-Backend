package com.example.taskManager.controller.response;

import java.time.LocalDate;

public record TotalLoggedTimeOnTaskPerDay(LocalDate date, Integer totalTime) {
}
