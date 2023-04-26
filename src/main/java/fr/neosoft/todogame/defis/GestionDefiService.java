package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class GestionDefiService extends CRUDService<Defi> implements GestionDefiInterface {

    public GestionDefiService(JpaRepository<Defi, Long> repository) {
        super(repository);
    }
}
