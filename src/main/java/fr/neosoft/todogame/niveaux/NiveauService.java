package fr.neosoft.todogame.niveaux;

import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class NiveauService extends CRUDService<Niveau> {
    public NiveauService(CrudRepository<Niveau, Long> repository) {
        super(repository);
    }

}
