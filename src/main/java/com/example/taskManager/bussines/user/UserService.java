package com.example.taskManager.bussines.user;

import com.example.taskManager.data.user.UserRepository;
import com.example.taskManager.models.user.User;
import com.example.taskManager.models.user.UserSession;
import com.example.taskManager.validator.exceptions.UserEmailAlreadyExistsException;
import com.example.taskManager.validator.exceptions.UserNotFoundException;
import com.example.taskManager.validator.exceptions.UserUsernameAlreadyExistsException;
import com.example.taskManager.validator.exceptions.UserValidationException;
import com.example.taskManager.validator.user.UserValidator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("userService")
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserSessionService userSessionService;

    public String login(String usernameOrEmail, String password) throws UserNotFoundException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new UserNotFoundException("This username or email does not exist!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException("Password is incorrect!");
        }

        String token = generateToken(usernameOrEmail);
        userSessionService.add(new UserSession(user, token, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));

        return token;
    }

    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }

    public void register(User user) throws UserUsernameAlreadyExistsException, UserEmailAlreadyExistsException, UserValidationException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserUsernameAlreadyExistsException("This username " + user.getUsername() + " already exists");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistsException("This email " + user.getEmail() + " already exists");
        }

        try {
            userValidator.validateUser(user);
        } catch (UserValidationException e) {
            throw new UserValidationException(e.getMessage());
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    
}
