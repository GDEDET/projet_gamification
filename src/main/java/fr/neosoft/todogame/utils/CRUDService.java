package fr.neosoft.todogame.utils;

import fr.neosoft.todogame.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Classe de base pour les services contenant les méthodes CRUD
 *
 * @param <T> type du document.
 */
public abstract class CRUDService<T> {
        private final JpaRepository<T, Long> repository;

        public CRUDService(JpaRepository<T, Long> repository) {
            this.repository = repository;
        }

        public T save(T entity) {
            return  this.repository.save(entity);
        }

        public T findById(Long id) {
            Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return this.repository.findById(id)
                    .orElseThrow(() -> new NotFoundException(
                            "Aucun(e) " + entityClass.getSimpleName() + " trouvé(e) avec l'id " + id + "."));
        }

        public List<T> findAll() {
        return  this.repository.findAll();
    }

        public void deleteById(Long id) {
            this.repository.deleteById(id);
        }

}
