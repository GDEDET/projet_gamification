package fr.neosoft.todogame.groupes;

import fr.neosoft.todogame.personnes.PersonneInterface;
import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class GestionGroupeService extends CRUDService<Groupe> implements GestionGroupeInterface {

    private final PersonneInterface personneInterface;
    
    public GestionGroupeService(JpaRepository<Groupe, Long> repository, PersonneInterface personneInterface) {
        super(repository);
        this.personneInterface = personneInterface;
    }

    @Override
    public Groupe addMembre(Long idGroupe, Long idPersonne)
    {
        Groupe groupe = this.findById(idGroupe);
        groupe.addMembre(this.personneInterface.findById(idPersonne));
        return this.save(groupe);
    }

    @Override
    public Groupe removeMembre(Long idGroupe, Long idPersonne)
    {
        Groupe groupe = this.findById(idGroupe);
        groupe.removeMembre(this.personneInterface.findById(idPersonne));

        return this.save(groupe);
    }
}
