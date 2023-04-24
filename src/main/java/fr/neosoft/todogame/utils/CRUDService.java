package fr.neosoft.todogame.utils;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Classe de base pour les services contenant les méthodes CRUD.
 * @param <T> type de donnee.
 */
public abstract class CRUDService<T> {

    private final CrudRepository<T, Long> repository;

    public CRUDService(CrudRepository<T, Long> repository) {
        this.repository = repository;
    }

    /**
     * Sauvegarde la donnée dans sa table MySQL.
     * @param donnee à sauvegarder
     * @return la donnee sauvegardé.
     */
    public T save(T donnee){
        return this.repository.save(donnee);
    }

    /**
     * Sauvegarde la donnée dans sa table MySQL.
     * @param donnee à sauvegarder
     * @return la donnee sauvegardé.
     */
    public T update(T donnee){
        return this.repository.save(donnee);
    }

    /**
     * Retourne la donnée correspondant à l'identifiant id.
     * @param id de l'entité à retourner
     * @return l'entité correspondante à l'id
     * @throws ResponseStatusException si aucune donnée ne porte cet id.
     */
    public T findById(Long id){
        return this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donnée non trouvée")
        );
    }

    /**
     * Retourne la liste de toutes les données de la table
     * @return la liste de toutes les données de la table
     */
    public Iterable<T> findAll(){
        return this.repository.findAll();
    }

    /**
     * Supprime la donnée correspondante à l'identifiant id.
     * @param id de l'entité à supprimer
     */
    public void deleteById(Long id){
        this.repository.deleteById(id);
    }




}
