package fr.neosoft.todogame.tache;

import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TacheService extends CRUDService<Tache> {
    public TacheService(JpaRepository<Tache, Long> repository) {
        super(repository);
    }


}
