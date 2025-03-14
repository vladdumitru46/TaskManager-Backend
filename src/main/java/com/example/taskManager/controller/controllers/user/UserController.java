package com.example.taskManager.controller.controllers.user;

import com.example.taskManager.bussines.user.UserService;
import com.example.taskManager.bussines.user.UserSessionService;
import com.example.taskManager.controller.request.DeleteSessionRequest;
import com.example.taskManager.controller.request.LoginRequest;
import com.example.taskManager.controller.request.RegisterRequest;
import com.example.taskManager.models.user.Role;
import com.example.taskManager.models.user.User;
import com.example.taskManager.models.user.UserSession;
import com.example.taskManager.validator.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserSessionService userSessionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest.usernameOrEmail(), loginRequest.password());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            userService.register(new User(registerRequest.username(), registerRequest.name(), registerRequest.email(), registerRequest.password(), Role.USER));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserValidationException | UserUsernameAlreadyExistsException | UserEmailAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody DeleteSessionRequest deleteSessionRequest) {
        try {
            UserSession user = userSessionService.getByToken(deleteSessionRequest.token());
            userSessionService.delete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserSessionNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getByToken")
    public ResponseEntity<?> getByToken(@RequestParam String token) {
        try {
            UserSession user = userSessionService.getByToken(token);
            return new ResponseEntity<>(user.getUser(), HttpStatus.OK);
        } catch (UserSessionNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
}
