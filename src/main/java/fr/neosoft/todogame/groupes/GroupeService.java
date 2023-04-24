package fr.neosoft.todogame.groupes;

import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupeService extends CRUDService<Groupe> {
    public GroupeService(JpaRepository<Groupe, Long> repository) {
        super(repository);
    }
}
