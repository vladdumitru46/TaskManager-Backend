package com.example.taskManager.data.user;

import com.example.taskManager.models.user.User;
import com.example.taskManager.models.user.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByUser(User user);

    Optional<UserSession> findByToken(String token);
}
