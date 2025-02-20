package com.example.taskManager.validator.exceptions;

public class UserUsernameAlreadyExistsException extends Exception{

    public UserUsernameAlreadyExistsException(String message) {
        super(message);
    }
}
