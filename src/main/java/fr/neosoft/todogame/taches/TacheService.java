package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.stereotype.Service;

@Service
public class TacheService extends CRUDService<Tache> {

    private final TacheRepository tacheRepository;

    private final PersonneService personneService;

    public TacheService(TacheRepository tacheRepository, PersonneService personneService) {
        super(tacheRepository);
        this.tacheRepository = tacheRepository;
        this.personneService = personneService;
    }

    /**
     * Permet de trouver toutes les tâches associées à un utilisateur
     * @param idUser : l'id de l'utilisateur dont on cherche les tâches
     * @return la liste des tâches de l'utilisateur donné
     */
    public Iterable<Tache> findAllByUser(Long idUser) {
        Personne personne = this.personneService.findById(idUser);
        return personne.getTaches();
    }
}
