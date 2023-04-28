package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.personnes.Personne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TacheData {

    public static Tache TACHE_VIDE = new Tache();
    public static Personne PERSONNE_VIDE = new Personne();
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
        personne.setRoles(List.of());
        return personne;
    }
    public static List<Tache> addListTaches(Personne personne) {
        List<Tache> taches = new ArrayList<>();
        taches.add(new Tache(1L, "Tache 1", new Date(), null, Priorite.ELEVEE, Difficulte.FACILE, Statut.EN_COURS, 10, personne));
        taches.add(new Tache(2L, "Tache 2", new Date(), null, Priorite.MOYENNE, Difficulte.MOYENNE, Statut.EN_COURS, 20, personne));
        taches.add(new Tache(3L, "Tache 3", new Date(), null, Priorite.BASSE, Difficulte.DIFFICILE, Statut.TERMINE, 20, personne));

        return taches;
    }

    public static Tache addOneTache(Personne personne) {
        return new Tache(1L, "Tache 1", new Date(), null, Priorite.ELEVEE, Difficulte.FACILE, Statut.EN_COURS, 10, personne);
    }
}
