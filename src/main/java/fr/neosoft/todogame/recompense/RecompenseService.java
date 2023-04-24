package fr.neosoft.todogame.recompense;

import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RecompenseService extends CRUDService<Recompense> {
    public RecompenseService(JpaRepository<Recompense, Long> repository) {
        super(repository);
    }
}
