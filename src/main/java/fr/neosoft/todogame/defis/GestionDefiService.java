package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.CRUDService;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionDefiService extends CRUDService<Defi> implements GestionDefiInterface {

    public GestionDefiService(JpaRepository<Defi, Long> repository) {
        super(repository);
    }
}
