package com.example.taskManager.validator.user;

import com.example.taskManager.models.user.User;
import com.example.taskManager.validator.exceptions.UserValidationException;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    public void validateUser(User user) throws UserValidationException {
        ValidationResult result = UserValidationInterface
                .isEmailValid()
                .and(UserValidationInterface.isUserNameValid())
                .and(UserValidationInterface.isPasswordValid())
                .apply(user);

        if (result != ValidationResult.SUCCESS) {
            switch (result) {
                case INVALID_USERNAME -> throw new UserValidationException("Username cannot be empty");
                case INVALID_EMAIL -> throw new UserValidationException("Email is not in correct format!");
                case INVALID_PASSWORD ->
                        throw new UserValidationException("Password must contain at least an uppercase letter, a special character, a number, and must be at least" +
                                " 8 characters");
            }

        }
    }
}
