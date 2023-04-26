package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.exceptions.NotFoundException;
import fr.neosoft.todogame.utils.CRUDService;
import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonneService extends CRUDService<Personne> {

    private final PersonneRepository personneRepository;

    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PersonneService(PersonneRepository personneRepository, RoleRepository roleRepository) {
        super(personneRepository);
        this.personneRepository = personneRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Permet de créer une personne en lui affectant le role personne par défaut et en encodant son mot de passe
     *
     * @param personneDto : les infos de la personne à creer
     * @return la personne créée
     */
    public Personne creerPersonne(RegisterRequestDto personneDto){
        Personne personne = new Personne();
        personne.setNomUtilisateur(personneDto.getNomUtilisateur());
        personne.setEmail(personne.getEmail());
        Optional<Role> personneRole = roleRepository.findByAuthority("PERSONNE");
        personne.setRoles(List.of(personneRole.get()));
        String password = passwordEncoder.encode(personneDto.getMotDePasse());
        personne.setMotDePasse(password);
        personne.setNom(personneDto.getNom());
        personne.setPrenom(personneDto.getPrenom());
        personne.setNbPoints(0);
        return this.personneRepository.save(personne);
    }

    /**
     * Méthode qui permet de trouver une personne à partir de son nomUtilisateur
     * @param nomUtilisateur : le nom d'utilisateur de la personne
     * @return la personne recherché
     */
    public Personne findByNomUtilisateur(String nomUtilisateur) {
        Personne personneRecherche = this.personneRepository.findByNomUtilisateur(nomUtilisateur);
        if (personneRecherche == null) {
            throw new NotFoundException("Aucun utilisateur ne possède ce nom d'utilisateur");
        }
        return personneRecherche;
    }

    public void incrementerNbPoint(Personne personne, int nbPoints) {
        personne.setNbPoints(personne.getNbPoints() + nbPoints);
        this.save(personne);
    }
}
