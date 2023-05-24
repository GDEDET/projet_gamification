package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.defis_personnes.DefiPersonneInterface;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.CRUDService;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GestionTacheService extends CRUDService<Tache> implements GestionTacheInterface{

    private final PersonneService personneService;

    private final DefiPersonneInterface defiPersonneInterface;

    private final GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;

    @Autowired
    private JavaMailSender mailSender;

    public GestionTacheService(TacheRepository tacheRepository, PersonneService personneService, DefiPersonneInterface defiPersonneInterface, GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface) {
        super(tacheRepository);
        this.personneService = personneService;
        this.defiPersonneInterface = defiPersonneInterface;
        this.gestionPersonneAuthentifieInterface = gestionPersonneAuthentifieInterface;
    }

    @Override
    public Iterable<Tache> findAllByUserConnected() {
        return this.gestionPersonneAuthentifieInterface.getPersonneAuthentifie().getTaches();
    }

    @Override
    public Tache terminerTache(Long tacheId) {
        Tache tache = this.findById(tacheId);

        tache.setStatut(Statut.TERMINE);
        tache.setDateRealisation(new Date());
        this.defiPersonneInterface.incrementerNbTachesTerminees(tache.getPersonne());

        String subject = "Tâche terminée : " + tache.getDescription();
        String text = "Bravo" + tache.getPersonne().getPrenom() + "Votre tâche est terminée, fécilitation.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(tache.getPersonne().getEmail());
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        return this.save(tache);
    }

    @Override
    public void supprimerTacheParId(Long tacheId) {
        Tache tache = this.findById(tacheId);
        Personne personneAssocie = tache.getPersonne();
        personneAssocie.getTaches().remove(tache);
        this.personneService.save(personneAssocie);
        this.deleteById(tacheId);
    }

    @Override
    public Tache creerTache(Personne personne, TacheDto tacheDto) {
        Tache nouvelleTache = this.nouvelleTacheAPartirDuDto(tacheDto);
        nouvelleTache.setPersonne(personne);
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
