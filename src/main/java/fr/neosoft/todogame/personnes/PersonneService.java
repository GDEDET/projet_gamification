package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.exceptions.NotFoundException;
import fr.neosoft.todogame.niveaux.NiveauRepository;
import fr.neosoft.todogame.utils.CRUDService;
import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonneService extends CRUDService<Personne> implements PersonneInterface{

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
		if(personneRole.isEmpty()){
			throw new NotFoundException("Le role PERSONNE n'a pas été trouvé dans la base");
		}
        personne.setRoles(List.of(personneRole.get()));
        String password = passwordEncoder.encode(personneDto.getMotDePasse());
        personne.setMotDePasse(password);
        personne.setNom(personneDto.getNom());
        personne.setPrenom(personneDto.getPrenom());
        personne.setNbPoints(0);
        return this.personneRepository.save(personne);
    }


    @Override
    public Personne findByNomUtilisateur(String nomUtilisateur) {
        Personne personneRecherche = this.personneRepository.findByNomUtilisateur(nomUtilisateur);
        if (personneRecherche == null) {
            throw new NotFoundException("Aucun utilisateur ne possède ce nom d'utilisateur");
        }
        return personneRecherche;
    }

	/**
	 * Permet de retourner le niveau de la personne ayant l'id passer en paramètre
	 * @param personne
	 * @return le niveau de la personne
	 */
	private Integer getNiveauPersonne(Personne personne) {
		Integer nbPointsPersonne = personne.getNbPoints();
		Integer niveauPersonne = this.niveauRepository.findByNbPoints(nbPointsPersonne);

		return niveauPersonne;
	}


    @Override
    public Iterable<Personne> getClassementParPoints(){
        return this.personneRepository.findAllByOrderByNbPointsDesc();
    }

    @Override
    public ArrayList<PersonneNiveauDto> getClassementParNiveaux() {
        Iterable<Personne> listPersonnesParPointsDesc = this.personneRepository.findAllByOrderByNbPointsDesc();
        ArrayList<PersonneNiveauDto> listPersonnesDto = new ArrayList<>();
        for (Personne personne : listPersonnesParPointsDesc) {
            listPersonnesDto.add(new PersonneNiveauDto(
                    personne.getNomUtilisateur(),
                    personne.getNbPoints(),
                    this.getNiveauPersonne(personne)
            ));
        }

        return listPersonnesDto;
    }


    @Override
    public List<Personne> getClassementParRealisations() {
        return this.personneRepository.findAllByOrderByTachesRealiseesDesc();
    }

    private PersonneNiveauDto personneToPersonneNiveauDto(Personne personne) {
        String nomUtilisateur = personne.getNomUtilisateur();
        Integer nbPoints = personne.getNbPoints();
        Integer niveauPersonne = this.getNiveauPersonne(personne);

        return new PersonneNiveauDto(nomUtilisateur, nbPoints, niveauPersonne);
    }

    @Override
    public PersonneNiveauDto infosNiveauPersonne(Personne personne) {
        return this.personneToPersonneNiveauDto(personne);
    }

    @Override
    public void incrementerNbPoint(Personne personne, int nbPoints) {
        personne.setNbPoints(personne.getNbPoints() + nbPoints);
        this.save(personne);
    }
}
