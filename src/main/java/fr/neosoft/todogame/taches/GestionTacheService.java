package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.defis.GestionDefiInterface;
import fr.neosoft.todogame.exceptions.NotFoundException;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GestionTacheService extends CRUDService<Tache> implements GestionTacheInterface{

    private final TacheRepository tacheRepository;

    private final PersonneService personneService;

    private final GestionDefiInterface gestionDefiInterface;

    public GestionTacheService(TacheRepository tacheRepository, PersonneService personneService, GestionDefiInterface gestionDefiInterface) {
        super(tacheRepository);
        this.tacheRepository = tacheRepository;
        this.personneService = personneService;
        this.gestionDefiInterface = gestionDefiInterface;
    }

    @Override
    public Iterable<Tache> findAllByUser(Long idUser) {
        Personne personne = this.personneService.findById(idUser);
        return personne.getTaches();
    }

    @Override
    public Tache terminerTache(Long tacheId) {
        Tache tache = this.findById(tacheId);

        tache.setStatut(Statut.TERMINE);
        tache.setDateRealisation(new Date());
        this.gestionDefiInterface.incrementerNbTachesTerminees(tache.getPersonne());
        return this.save(tache);
    }

    @Override
    public Tache creerTache(Personne personne, TacheDto tacheDto) {
        Tache nouvelleTache = this.nouvelleTacheAPartirDuDto(tacheDto);
        this.save(nouvelleTache);
        personne.getTaches().add(nouvelleTache);
        personneService.save(personne);
        return nouvelleTache;
    }

    /**
     * Méthode interne qui permet de transformer un objet de type TacheDto en objet Tache en l'initialisant avec les
     * valeurs souhaitées
     * @param tacheDto : l'objet contenant les information pour la création de la tâche
     * @return la tâche initialisée à partir des données fournies
     */
    private Tache nouvelleTacheAPartirDuDto(TacheDto tacheDto){
        Tache tache = new Tache();
        tache.setDescription(tacheDto.getDescription());
        tache.setDifficulte(tacheDto.getDifficulte());
        tache.setDateEcheance(tacheDto.getDateEcheance());
        tache.setPriorite(tacheDto.getPriorite());
        tache.setNbPoints(tacheDto.getDifficulte().getNbPoint() + tacheDto.getPriorite().getNbPoint());
        tache.setStatut(Statut.EN_COURS);
        return tache;
    }

}
