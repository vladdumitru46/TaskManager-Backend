package com.example.taskManager.validator.exceptions;

public class UserEmailAlreadyExistsException extends Exception{
    public UserEmailAlreadyExistsException(String message) {
        super(message);
    }
}
