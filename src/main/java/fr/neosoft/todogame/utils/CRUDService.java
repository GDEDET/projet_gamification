package fr.neosoft.todogame.utils;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Classe de base pour les services contenant les méthodes CRUD.
 * @param <T> type de donnee.
 */
public abstract class CRUDService<T> implements CRUDInterface<T> {

    private final CrudRepository<T, Long> repository;

    public CRUDService(CrudRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T donnee){
        return this.repository.save(donnee);
    }

    @Override
    public T update(T donnee){
        return this.repository.save(donnee);
    }

    @Override
    public T findById(Long id){
        return this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donnée non trouvée")
        );
    }

    @Override
    public Iterable<T> findAll(){
        return this.repository.findAll();
    }

    @Override
    public void deleteById(Long id){
        this.repository.deleteById(id);
    }




}
