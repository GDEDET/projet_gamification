package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.roles.Role;

import java.util.List;

public class PersonneData {
    public static Personne DUPOND_MICHEL = getNewPersonne();
    public static Personne getNewPersonne() {
        Personne personne = new Personne();
        personne.setId(1L);
        personne.setNom("Dupond");
        personne.setPrenom("Michel");
        personne.setEmail("dupond.michel@yopmail.com");
        personne.setNomUtilisateur("mdupond");
        personne.setMotDePasse("motdepasse");
        personne.setNbPoints(0);
        personne.setRoles(List.of(new Role(1L, "PERSONNE")));
        return personne;
    }
}
