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
     * Permet de trouver toutes les tâches d'un utilisateur donné
     * @param idUser : l'id de l'utilisateur
     * @return la liste des tâches de l'utilisateur
     */
    Iterable<Tache> findAllByUser(Long idUser);

    Tache terminerTache(Long id);
}
