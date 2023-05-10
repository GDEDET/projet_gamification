package fr.neosoft.todogame.recompenses.prerequisrecompense;

import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrerequisRecompenseService extends CRUDService<PrerequisRecompense> implements PrerequisRecompenseInterface {
    private final PrerequisRecompenseRepository prerequisRecompenseRepository;

    public PrerequisRecompenseService(JpaRepository<PrerequisRecompense, Long> repository,
                                      PrerequisRecompenseRepository prerequisRecompenseRepository) {
        super(repository);
        this.prerequisRecompenseRepository = prerequisRecompenseRepository;
    }
}
