package com.example.taskManager.controller.request;

import java.time.LocalDate;

public record GetAllLoggedTimeForUserInPeriodOfTime(String username, LocalDate startDate, LocalDate endDate) {
}
