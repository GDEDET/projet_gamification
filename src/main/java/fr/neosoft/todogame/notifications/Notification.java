package fr.neosoft.todogame.notifications;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String email;
    private String message;
}
