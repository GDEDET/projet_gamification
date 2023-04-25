package fr.neosoft.todogame.groupes;

import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupeService extends CRUDService<Groupe> {

    private final GroupeRepository groupeRepository;
    private final PersonneService personneService;
    
    public GroupeService(JpaRepository<Groupe, Long> repository, GroupeRepository groupeRepository, PersonneService personneService) {
        super(repository);
        this.groupeRepository = groupeRepository;
        this.personneService = personneService;
    }

    /**
     * Ajoute un membre à un groupe
     *
     * @param idGroupe id du groupe
     * @param idPersonne id du membre à ajouter
     * @return Groupe
     */
    public Groupe addMembre(Long idGroupe, Long idPersonne)
    {
        Groupe groupe = this.findById(idGroupe);
        groupe.addMembre(this.personneService.findById(idPersonne));
        return this.save(groupe);
    }

    /**
     * Supprime un membre d'un groupe
     *
     * @param idGroupe id du groupe
     * @param idPersonne id du membre à supprimer
     * @return Groupe
     */
    public Groupe removeMembre(Long idGroupe, Long idPersonne)
    {
        Groupe groupe = this.findById(idGroupe);
        groupe.removeMembre(this.personneService.findById(idPersonne));

        return this.save(groupe);
    }
}
