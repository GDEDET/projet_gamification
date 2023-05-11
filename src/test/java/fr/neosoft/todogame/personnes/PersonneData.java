package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.recompenses.Recompense;

import java.util.List;

public class PersonneData {
    public static final Personne DUPOND_MICHEL = getNewPersonne(1L, "Dupond", "Michel", "dupond.michel@yopmail.com", "mdupond", "motdepasse", 0, new Role(1L, "PERSONNE"));
    public static final Personne BON_JEAN = getNewPersonne(2L, "Bon","Jean","jeanBon@yopmail.fr","jeanBon","$2a$10$sEebmn5b2WV1jNXJtrTdOO0r.s1PCzdhuIN2K5jxqzSj25Kah1x/S", 1500, new Role(1L, "PERSONNE"));

    public static Personne getNewPersonne(Long id, String nom, String prenom, String email, String utilisateur, String motDePasse, int nbPoints, Role role) {
        Personne personne = new Personne();
        personne.setId(id);
        personne.setNom(nom);
        personne.setPrenom(prenom);
        personne.setEmail(email);
        personne.setNomUtilisateur(utilisateur);
        personne.setMotDePasse(motDePasse);
        personne.setNbPoints(nbPoints);
        personne.setRoles(List.of(role));

        return personne;
    }

    public static Personne setRecompensePersonne(Personne personne, List<Recompense> recompenses) {
        personne.setRecompenses(recompenses);

        return personne;
    }


}
