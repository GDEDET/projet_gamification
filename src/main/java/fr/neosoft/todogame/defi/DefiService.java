package fr.neosoft.todogame.defi;

import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DefiService extends CRUDService<Defi> {
    public DefiService(JpaRepository<Defi, Long> repository) {
        super(repository);
    }
}
