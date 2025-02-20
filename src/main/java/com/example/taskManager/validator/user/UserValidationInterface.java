package com.example.taskManager.validator.user;

import com.example.taskManager.models.user.User;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface UserValidationInterface extends Function<User, ValidationResult> {

    default UserValidationInterface and(UserValidationInterface other) {
        return user -> {
            ValidationResult result = this.apply(user);
            return result.equals(ValidationResult.SUCCESS) ? other.apply(user) : result;
        };
    }

    String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";

    Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    static UserValidationInterface isEmailValid(){
        return user -> {
            String email = user.getEmail();

            if (email == null || email.trim().isEmpty()) {
                return ValidationResult.INVALID_EMAIL;
            }

            Matcher matcher = EMAIL_PATTERN.matcher(email);

            if (!matcher.matches()) {
                return ValidationResult.INVALID_EMAIL;
            }

            return ValidationResult.SUCCESS;
        };
    }

    String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    static UserValidationInterface isPasswordValid() {
        return user -> {
            String password = user.getPassword();

            if (password == null || password.trim().isEmpty()) {
                return ValidationResult.INVALID_PASSWORD;
            }

            Matcher matcher = PASSWORD_PATTERN.matcher(password);

            if (!matcher.matches()) {
                return ValidationResult.INVALID_PASSWORD;
            }

            return ValidationResult.SUCCESS;
        };
    }

    static UserValidationInterface isUserNameValid() {
        return user -> {
            if (user.getUsername().isEmpty()) {
                return ValidationResult.INVALID_USERNAME;
            }
            return ValidationResult.SUCCESS;
        };
    }
}
