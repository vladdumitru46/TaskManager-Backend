package com.example.taskManager.controller.request;

import java.time.LocalDate;

public record GetByTaskAndDateRequest(Long taskId, LocalDate date) {
}
