package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.CRUDService;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionDefiService extends CRUDService<Defi> implements GestionDefiInterface {

    private final PersonneService personneService;

    private final GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;

    public GestionDefiService(JpaRepository<Defi, Long> repository, PersonneService personneService, GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface) {
        super(repository);
        this.personneService = personneService;
        this.gestionPersonneAuthentifieInterface = gestionPersonneAuthentifieInterface;
    }

    @Override
    public Iterable<Defi> findAllByPersonneConnecte() {
        return gestionPersonneAuthentifieInterface.getPersonneAuthentifie().getDefisARealiser();
    }

    @Override
    public void incrementerNbTachesTerminees(Personne personne) {
        List<Defi> listDefis = personne.getDefisARealiser();

        for (Defi defi : listDefis) {
            if (defi.getNbTachesObjectif() > 0) {
                defi.setNbTachesTerminees(
                        defi.getNbTachesTerminees() + 1
                );
                this.save(defi);

                if (this.isDefiTermine(defi)) {
                    this.terminerDefi(defi);
                }
            }
        }
    }

    @Override
    public void incrementerNbPointsGagnes(Personne personne, int nbPoints) {
        List<Defi> listDefis = personne.getDefisARealiser();

        for (Defi defi : listDefis) {
            if (defi.getNbPointsObjectif() > 0) {
                defi.setNbPointsGagnes(
                        defi.getNbPointsGagnes() + nbPoints
                );

                if (this.isDefiTermine(defi)) {
                    this.terminerDefi(defi);
                }

                this.save(defi);
            }
        }
    }

    @Override
    public List<Defi> ajouterDefi(Long id) {
        Defi defi = this.findById(id);
        Personne personne = gestionPersonneAuthentifieInterface.getPersonneAuthentifie();
        personne.getDefisARealiser().add(defi);
        this.personneService.save(personne);
        return personne.getDefisARealiser();
    }

    /**
     * Méthode interne qui permet de vérifier si un défi est terminé en fonction du nombre de tâches à effectuer
     * et du nombre de points à gagner
     * @param defi : le défi que l'on souhaite controler
     * @return un booléen qui indique si le défi est terminé ou non
     */
    private boolean isDefiTermine(Defi defi) {
        return defi.getNbTachesTerminees() == defi.getNbTachesObjectif()
                && defi.getNbPointsGagnes() > defi.getNbPointsObjectif();
    }

    /**
     * Méthode interne qui permet de terminer un défi et d'effectuer les opérations nécessaires
     * @param defi : le défi que l'on souhaite terminer
     */
    private void terminerDefi(Defi defi) {
        // On augmente les points de la personne qui a réalisé le défi et supprime de sa liste de défis à réaliser
        Personne personne = defi.getPersonneRealisantLeDefi();
        personneService.incrementerNbPoint(personne, defi.getNbPointsRecompense());
        personne.getDefisARealiser().remove(defi);
        personneService.save(personne);

        // Si le défi fait gagner des points alors, on incrémente les points des autres défis
        // Attention à bien supprimer ce défi avant de rappeler la fonction d'incrément de points des défis ci-dessous
        this.incrementerNbPointsGagnes(personne, defi.getNbPointsRecompense());
    }
}
