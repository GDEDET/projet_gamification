package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.niveaux.NiveauRepository;
import fr.neosoft.todogame.utils.CRUDService;
import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonneService extends CRUDService<Personne> implements PersonneInterface {

    private final PersonneRepository personneRepository;

    private final RoleRepository roleRepository;

	private final NiveauRepository niveauRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PersonneService(
			PersonneRepository personneRepository,
			RoleRepository roleRepository,
			NiveauRepository niveauRepository
	) {
        super(personneRepository);
        this.personneRepository = personneRepository;
        this.roleRepository = roleRepository;
		this.niveauRepository = niveauRepository;
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
        return this.personneRepository.save(personne);
    }
	/**
	 * Permet de retourner le niveau de la personne ayant l'id passer en paramètre
	 * @param personne
	 * @return le niveau de la personne
	 */
	private Integer niveauPersonne(Personne personne) {
		Integer nbPointsPersonne = personne.getNbPoints();
		Integer niveauPersonne = this.niveauRepository.findByNbPoints(nbPointsPersonne);

		return niveauPersonne;
	}


	private PersonneNiveauDto personneToPersonneNiveauDto(Personne personne) {
		String nomUtilisateur = personne.getNomUtilisateur();
		Integer nbPoints = personne.getNbPoints();
		Integer niveauPersonne = this.niveauPersonne(personne);

		return new PersonneNiveauDto(nomUtilisateur, nbPoints, niveauPersonne);
	}

	@Override
	public PersonneNiveauDto infosNiveauPersonne(Long idPersonne) {
		Personne personne = this.findById(idPersonne);
		return this.personneToPersonneNiveauDto(personne);
	}
}
