package com.example.taskManager.models.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "UserSession")
@Table(
        name = "userSessions"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSession {
    @Id
    @SequenceGenerator(
            name = "userSession_sequence",
            sequenceName = "userSession_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "userSession_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @Column(
            name = "token",
            nullable = false
    )
    private String token;

    @Column(
            name = "cratedAt",
            nullable = false
    )
    private LocalDateTime cratedAt;
    @Column(
            name = "expiresAt",
            nullable = false
    )
    private LocalDateTime expiresAt;

    public UserSession(User user, String token, LocalDateTime cratedAt, LocalDateTime expiresAt) {
        this.user = user;
        this.token = token;
        this.cratedAt = cratedAt;
        this.expiresAt = expiresAt;
    }
}
