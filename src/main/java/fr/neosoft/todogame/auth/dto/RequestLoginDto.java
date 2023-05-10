package fr.neosoft.todogame.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginDto {
    private String email;
    private String motDePasse;
}
