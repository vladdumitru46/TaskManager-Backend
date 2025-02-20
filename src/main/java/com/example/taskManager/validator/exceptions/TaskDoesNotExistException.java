package com.example.taskManager.validator.exceptions;

public class TaskDoesNotExistException extends Exception {
    public TaskDoesNotExistException(String message) {
        super(message);
    }
}
