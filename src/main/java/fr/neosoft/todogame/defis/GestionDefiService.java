package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionDefiService extends CRUDService<Defi> implements GestionDefiInterface {

    public final PersonneService personneService;

    public GestionDefiService(JpaRepository<Defi, Long> repository, PersonneService personneService) {
        super(repository);
        this.personneService = personneService;
    }

    @Override
    public Iterable<Defi> findAllByUser(Long idUser) {
        Personne personne = this.personneService.findById(idUser);
        return personne.getDefisARealiser();
    }

    @Override
    public void incrementerNbTachesTerminees(Personne personne) {
        List<Defi> listDefis = personne.getDefisARealiser();

        for (Defi defi : listDefis) {
            if (defi.getDefiPrerequis().getNbTachesObjectif() > 0) {
                defi.getDefiPrerequis().setNbTachesTerminees(
                        defi.getDefiPrerequis().getNbTachesTerminees() + 1
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
            if (defi.getDefiPrerequis().getNbPointsObjectif() > 0) {
                defi.getDefiPrerequis().setNbPointsGagnes(
                        defi.getDefiPrerequis().getNbPointsGagnes() + nbPoints
                );

                if (this.isDefiTermine(defi)) {
                    this.terminerDefi(defi);
                }

                this.save(defi);
            }
        }
    }

    public boolean isDefiTermine(Defi defi) {
        return defi.getDefiPrerequis().getNbTachesTerminees() == defi.getDefiPrerequis().getNbTachesObjectif()
                && defi.getDefiPrerequis().getNbPointsGagnes() > defi.getDefiPrerequis().getNbPointsObjectif();
    }

    public Defi terminerDefi(Defi defi) {
        // On augmente les points de la personne qui a réalisé le défi et supprime de sa liste de défis à réaliser
        Personne personne = defi.getPersonneRealisantLeDefi();
        personneService.incrementerNbPoint(personne, defi.getNbPointsRecompense());
        personne.getDefisARealiser().remove(defi);
        personneService.save(personne);

        // Si le défi fait gagner des points alors, on incrémente les points des autres défis
        // Attention à bien supprimer ce défi avant de rappeler la fonction d'incrément de points des défis ci-dessous
        this.incrementerNbPointsGagnes(personne, defi.getNbPointsRecompense());

        return this.save(defi);
    }
}
