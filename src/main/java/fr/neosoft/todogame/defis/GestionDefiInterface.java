package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.utils.CRUDInterface;

import java.util.List;

public interface GestionDefiInterface extends CRUDInterface<Defi> {

    /**
     * Permet de trouver tout les défis associés à la personne authentifiée
     * @return la liste des défis de l'utilisateur donné
     */
     Iterable<Defi> findAllByPersonneConnecte();

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

    List<Defi> ajouterDefi(Long id);
}