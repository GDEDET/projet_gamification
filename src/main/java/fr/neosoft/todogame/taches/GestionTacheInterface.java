package fr.neosoft.todogame.taches;


import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.utils.CRUDInterface;

/**
 * Service qui permet de gérer les tâches
 */
public interface GestionTacheInterface extends CRUDInterface<Tache> {

    /**
     * Permet à un utilisateur de se créer une tâche
     * @param personne la personne qui créer la tâche
     * @param tacheDto la tâche que l'utilisateur souhaite s'ajouter
     * @return la tâche créée
     */
    Tache creerTache(Personne personne, TacheDto tacheDto);

    /**
     * Permet de trouver toutes les tâches de l'utilisateur connecté
     * @return la liste des tâches de l'utilisateur
     */
    Iterable<Tache> findAllByUserConnected();

    /**
     * Permet de terminer une tâche et d'effectuer les traitements nécessaires
     * @param tacheId : l'id de la tâche à terminer
     * @return la tâche terminée
     */
    Tache terminerTache(Long tacheId);
}
