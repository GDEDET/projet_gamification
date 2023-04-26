package fr.neosoft.todogame.utils;

import fr.neosoft.todogame.personnes.Personne;

/**
 * Permet de gérer l'utilisateur connecté
 */
public interface GestionPersonneAuthentifieInterface {

    /**
     * Méthode qui permet de retourner la Personne connecté à l'application à partir du token d'authentification
     * @return la personne connecté
     */
    Personne getPersonneAuthentifie();
}
