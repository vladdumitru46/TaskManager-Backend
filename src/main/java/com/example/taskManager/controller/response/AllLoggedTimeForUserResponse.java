package com.example.taskManager.controller.response;

import java.util.List;

public record AllLoggedTimeForUserResponse(List<LoggedTimeOnTaskPerDay> loggedTimeOnTaskPerDays,
                                           Integer totalTimeLogged,
                                           List<TotalLoggedTimeOnTaskPerDay> totalLoggedTimeOnTaskPerDays) {
}
