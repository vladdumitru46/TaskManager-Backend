package com.example.taskManager.validator.exceptions;

public class ProjectDoesNotExistException extends Exception{
    public ProjectDoesNotExistException(String message) {
        super(message);
    }
}
