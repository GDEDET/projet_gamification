package fr.neosoft.todogame.defis_personnes;

import fr.neosoft.todogame.personnes.Personne;

import java.util.List;

public interface DefiPersonneInterface {

    /**
     * Permet de trouver tous les défis associés à la personne authentifiée
     * @return la liste des défis de l'utilisateur donné
     */
    List<DefiPersonne> findAllByPersonneConnecte();

    /**
     * Permet d'incrémenter le nombre de tâches terminées dans les défis associés pour la personne donnée
     * @param personne : la personne dont on souhaite mettre à jour la progression de défis
     */
    void incrementerNbTachesTerminees(Personne personne);

    /**
     * Permet d'incrémenter le nombre de points gagnés dans les défis associés pour la personne donnée
     * @param personne : la personne dont on souhaite mettre à jour la progression de défis
     */
    void incrementerNbPointsGagnes(Personne personne, int nbPoints);

    /**
     * Permet à un utilisateur de s'ajouter un défi à sa liste de défi en cours de réalisation
     * @param id : id du défi que l'on souhaite ajouter
     * @return la liste des défis de l'utilisateur
     */
    List<DefiPersonne> ajouterDefi(Long id);

    void deleteAllByDefi(Long defiId);
}
