package fr.neosoft.todogame.groupes;

import fr.neosoft.todogame.utils.CRUDInterface;

public interface GestionGroupeInterface extends CRUDInterface<Groupe> {

    /**
     * Ajoute un membre à un groupe
     *
     * @param idGroupe id du groupe
     * @param idPersonne id du membre à ajouter
     * @return Groupe
     */
    Groupe addMembre(Long idGroupe, Long idPersonne);

    /**
     * Supprime un membre d'un groupe
     *
     * @param idGroupe id du groupe
     * @param idPersonne id du membre à supprimer
     * @return Groupe
     */
    Groupe removeMembre(Long idGroupe, Long idPersonne);
}
