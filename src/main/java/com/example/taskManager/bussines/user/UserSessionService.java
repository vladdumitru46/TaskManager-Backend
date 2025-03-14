package com.example.taskManager.bussines.user;

import com.example.taskManager.data.user.UserSessionRepository;
import com.example.taskManager.models.user.UserSession;
import com.example.taskManager.validator.exceptions.UserSessionNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    public void add(UserSession userSession) {
        Optional<UserSession> exisingUserSession = userSessionRepository.findByUser(userSession.getUser());
        if (exisingUserSession.isEmpty()) {
            userSessionRepository.save(userSession);
        } else {
            exisingUserSession.get().setToken(userSession.getToken());
            updateToken(exisingUserSession.get());
        }
    }

    public UserSession getByToken(String token) throws UserSessionNotFoundException {
        return userSessionRepository.findByToken(token)
                .orElseThrow(() -> new UserSessionNotFoundException("There is no user session"));
    }

    public void delete(UserSession user) {
        userSessionRepository.delete(user);
    }

    @Transactional
    public void updateToken(UserSession userSession) {
        userSession.setToken(userSession.getToken());
    }
}
