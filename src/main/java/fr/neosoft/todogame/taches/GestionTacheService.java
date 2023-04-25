package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.stereotype.Service;

@Service
public class GestionTacheService extends CRUDService<Tache> implements GestionTacheInterface{

    private final TacheRepository tacheRepository;

    private final PersonneService personneService;

    public GestionTacheService(TacheRepository tacheRepository, PersonneService personneService) {
        super(tacheRepository);
        this.tacheRepository = tacheRepository;
        this.personneService = personneService;
    }

    @Override
    public Iterable<Tache> findAllByUser(Long idUser) {
        Personne personne = this.personneService.findById(idUser);
        return personne.getTaches();
    }

    @Override
    public Tache creerTache(Long idUtilisateur, TacheDto tacheDto) {
        Personne personne = personneService.findById(idUtilisateur);
        Tache nouvelleTache = this.tacheDtoToObject(tacheDto);
        this.save(nouvelleTache);
        personne.getTaches().add(nouvelleTache);
        personneService.save(personne);
        return nouvelleTache;
    }

    private Tache tacheDtoToObject(TacheDto tacheDto){
        Tache tache = new Tache();
        tache.setDescription(tacheDto.getDescription());
        tache.setDifficulte(tacheDto.getDifficulte());
        tache.setDateEcheance(tacheDto.getDateEcheance());
        tache.setPriorite(tacheDto.getPriorite());
        tache.setNbPoints(tacheDto.getDifficulte().getNbPoint() + tacheDto.getPriorite().getNbPoint());
        return tache;
    }

}
