package fr.neosoft.todogame.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String nomUtilisateur;
    private String motDePasse;
    private String email;
    private String nom;
    private String prenom;

    // autres champs du formulaire d'enregistrement
}
