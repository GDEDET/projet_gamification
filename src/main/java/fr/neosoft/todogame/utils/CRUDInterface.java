package fr.neosoft.todogame.utils;

import org.springframework.web.server.ResponseStatusException;

public interface CRUDInterface<T> {

    /**
     * Sauvegarde la donnée dans sa table PostgreSQL.
     * @param donnee à sauvegarder
     * @return la donnee sauvegardé.
     */
    T save(T donnee);

    /**
     * Retourne la liste de toutes les données de la table
     * @return la liste de toutes les données de la table
     */
    Iterable<T> findAll();

    /**
     * Retourne la donnée correspondant à l'identifiant id.
     * @param id de l'entité à retourner
     * @return l'entité correspondante à l'id
     * @throws ResponseStatusException si aucune donnée ne porte cet id.
     */
    T findById(Long id);

    /**
     * Sauvegarde la donnée dans sa table PostgreSQL.
     * @param donnee à sauvegarder
     * @return la donnee sauvegardé.
     */
    T update(T donnee);

    /**
     * Supprime la donnée correspondante à l'identifiant id.
     * @param id de l'entité à supprimer
     */
    void deleteById(Long id);
}
