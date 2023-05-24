package fr.neosoft.todogame.defis_personnes;

import fr.neosoft.todogame.defis.Defi;
import fr.neosoft.todogame.defis.DefiInterface;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneInterface;
import fr.neosoft.todogame.recompenses.RecompensePersonneInterface;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefiPersonneService implements DefiPersonneInterface {

    private final GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;
    private final DefiInterface defiInterface;
    private final PersonneInterface personneInterface;
    private final DefiPersonneRepository defiPersonneRepository;
    private final RecompensePersonneInterface recompensePersonneInterface;

    public DefiPersonneService(
            DefiPersonneRepository repository,
            GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface,
            DefiInterface defiInterface,
            PersonneInterface personneInterface,
            RecompensePersonneInterface recompensePersonneInterface
    ) {
        this.defiPersonneRepository = repository;
        this.gestionPersonneAuthentifieInterface = gestionPersonneAuthentifieInterface;
        this.defiInterface = defiInterface;
        this.personneInterface = personneInterface;
        this.recompensePersonneInterface = recompensePersonneInterface;
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

                if (this.isDefiTermine(defiPersonne)) {
                    this.terminerDefi(defiPersonne);
                }

                defiPersonneRepository.save(defiPersonne);
            }
        }
        recompensePersonneInterface.majRecompensePersonne(personne);
        personneInterface.save(personne);
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

                defiPersonneRepository.save(defiPersonne);
            }
        }
        personneInterface.save(personne);
    }

    @Override
    public List<DefiPersonne> ajouterDefi(Long id) {
        Defi defi = defiInterface.findById(id);
        Personne personne = gestionPersonneAuthentifieInterface.getPersonneAuthentifie();
        DefiPersonne defiPersonne = new DefiPersonne(personne, defi);
        this.defiPersonneRepository.save(defiPersonne);
        personne.getDefisARealiser().add(defiPersonne);
        this.personneInterface.save(personne);
        return personne.getDefisARealiser();
    }

    @Override
    @Transactional
    public void deleteAllByDefi(Long defiId) {
        this.defiPersonneRepository.deleteAllByDefi(defiInterface.findById(defiId));
    }

    /**
     * Méthode interne qui permet de vérifier si un défi est terminé en fonction du nombre de tâches à effectuer
     * et du nombre de points à gagner
     * @param defiPersonne : le défi que l'on souhaite controler
     * @return un booléen qui indique si le défi est terminé ou non
     */
    private boolean isDefiTermine(DefiPersonne defiPersonne) {
        return defiPersonne.getNbTachesTerminees() == defiPersonne.getDefi().getNbTachesObjectif()
                && defiPersonne.getNbPointsGagnes() >= defiPersonne.getDefi().getNbPointsObjectif();
    }

    /**
     * Méthode interne qui permet de terminer un défi et d'effectuer les opérations nécessaires
     * @param defiPersonne : le défi que l'on souhaite terminer
     */
    private void terminerDefi(DefiPersonne defiPersonne) {
        // On augmente les points de la personne qui a réalisé le défi et supprime de sa liste de défis à réaliser
        Personne personne = defiPersonne.getPersonne();
        if (defiPersonne.getDefi().getNbPointsRecompense() > 0) {
            personneInterface.incrementerNbPoint(personne, defiPersonne.getDefi().getNbPointsRecompense());
        }

        // Mise a jour des défis de la personne
        List<DefiPersonne> listDefis = new ArrayList<>(personne.getDefisARealiser());
        listDefis.remove(defiPersonne);
        personne.setDefisARealiser(listDefis);

        // Si le défi fait gagner des points alors, on incrémente les points des autres défis
        // Attention à bien supprimer ce défi avant de rappeler la fonction d'incrément de points des défis ci-dessous
        if (defiPersonne.getDefi().getNbPointsRecompense() > 0) {
            this.incrementerNbPointsGagnes(personne, defiPersonne.getDefi().getNbPointsRecompense());
        }
    }
}
