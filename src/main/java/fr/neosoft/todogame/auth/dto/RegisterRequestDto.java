package fr.neosoft.todogame.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
