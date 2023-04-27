package fr.neosoft.todogame.defis_personnes;

import fr.neosoft.todogame.defis.Defi;
import fr.neosoft.todogame.defis.GestionDefiInterface;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneInterface;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionDefiPersonneService implements GestionDefiPersonneInterface {

    private final GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;
    private final GestionDefiInterface gestionDefiInterface;
    private final PersonneInterface personneInterface;
    private final DefiPersonneRepository defiPersonneRepository;

    public GestionDefiPersonneService(
            DefiPersonneRepository repository,
            GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface,
            GestionDefiInterface gestionDefiInterface,
            PersonneInterface personneInterface
    ) {
        this.defiPersonneRepository = repository;
        this.gestionPersonneAuthentifieInterface = gestionPersonneAuthentifieInterface;
        this.gestionDefiInterface = gestionDefiInterface;
        this.personneInterface = personneInterface;
    }

    @Override
    public List<DefiPersonne> findAllByPersonneConnecte() {
        return gestionPersonneAuthentifieInterface.getPersonneAuthentifie().getDefisARealiser();
    }

    @Override
    public void incrementerNbTachesTerminees(Personne personne) {
        List<DefiPersonne> listDefis = personne.getDefisARealiser();

        for (DefiPersonne defiPersonne : listDefis) {
            if (defiPersonne.getDefi().getNbTachesObjectif() > 0) {
                defiPersonne.setNbTachesTerminees(
                        defiPersonne.getNbTachesTerminees() + 1
                );
                this.defiPersonneRepository.save(defiPersonne);

                if (this.isDefiTermine(defiPersonne)) {
                    this.terminerDefi(defiPersonne);
                }
            }
        }
    }

    @Override
    public void incrementerNbPointsGagnes(Personne personne, int nbPoints) {
        List<DefiPersonne> listDefis = personne.getDefisARealiser();

        for (DefiPersonne defiPersonne : listDefis) {
            if (defiPersonne.getDefi().getNbPointsObjectif() > 0) {
                defiPersonne.setNbPointsGagnes(
                        defiPersonne.getNbPointsGagnes() + nbPoints
                );

                if (this.isDefiTermine(defiPersonne)) {
                    this.terminerDefi(defiPersonne);
                }

                this.defiPersonneRepository.save(defiPersonne);
            }
        }
    }

    @Override
    public List<DefiPersonne> ajouterDefi(Long id) {
        Defi defi = gestionDefiInterface.findById(id);
        Personne personne = gestionPersonneAuthentifieInterface.getPersonneAuthentifie();
        personne.getDefisARealiser().add(new DefiPersonne(personne, defi));
        this.personneInterface.save(personne);
        return personne.getDefisARealiser();
    }

    /**
     * Méthode interne qui permet de vérifier si un défi est terminé en fonction du nombre de tâches à effectuer
     * et du nombre de points à gagner
     * @param defiPersonne : le défi que l'on souhaite controler
     * @return un booléen qui indique si le défi est terminé ou non
     */
    private boolean isDefiTermine(DefiPersonne defiPersonne) {
        return defiPersonne.getNbTachesTerminees() == defiPersonne.getDefi().getNbTachesObjectif()
                && defiPersonne.getNbPointsGagnes() > defiPersonne.getDefi().getNbPointsObjectif();
    }

    /**
     * Méthode interne qui permet de terminer un défi et d'effectuer les opérations nécessaires
     * @param defiPersonne : le défi que l'on souhaite terminer
     */
    private void terminerDefi(DefiPersonne defiPersonne) {
        // On augmente les points de la personne qui a réalisé le défi et supprime de sa liste de défis à réaliser
        Personne personne = defiPersonne.getPersonne();
        personneInterface.incrementerNbPoint(personne, defiPersonne.getDefi().getNbPointsRecompense());
        personne.getDefisARealiser().remove(defiPersonne);
        personneInterface.save(personne);

        // Si le défi fait gagner des points alors, on incrémente les points des autres défis
        // Attention à bien supprimer ce défi avant de rappeler la fonction d'incrément de points des défis ci-dessous
        this.incrementerNbPointsGagnes(personne, defiPersonne.getDefi().getNbPointsRecompense());
    }
}
