package com.example.taskManager.bussines.user;

import com.example.taskManager.data.user.UserSessionRepository;
import com.example.taskManager.models.user.UserSession;
import com.example.taskManager.validator.exceptions.UserSessionNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    public void add(UserSession userSession) {
        if (userSessionRepository.findByUser(userSession.getUser()).isEmpty()) {
            userSessionRepository.save(userSession);
        }
    }

    public UserSession getByToken(String token) throws UserSessionNotFoundException {
        return userSessionRepository.findByToken(token)
                .orElseThrow(() -> new UserSessionNotFoundException("There is no user session"));
    }
}
