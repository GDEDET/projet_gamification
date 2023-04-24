package fr.neosoft.todogame.auth.dto;

import lombok.Data;

@Data
public class RequestLoginDto {
    private String username;
    private String password;
}
