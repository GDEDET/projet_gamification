package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.exceptions.NotFoundException;
import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.niveaux.NiveauRepository;
import fr.neosoft.todogame.utils.CRUDService;
import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
	@Override
    public Personne creerPersonne(RegisterRequestDto personneDto){
        Personne personne = new Personne();
        personne.setNomUtilisateur(personneDto.getNomUtilisateur());
        personne.setEmail(personneDto.getEmail());
		Role personneRole = roleRepository.findByAuthority("PERSONNE").orElseThrow(
				() -> new NotFoundException("Le role PERSONNE n'a pas été trouvé dans la base")
		);
        personne.setRoles(List.of(personneRole));
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
    @Override
    public Personne findByNomUtilisateur(String nomUtilisateur) {
		Personne personneRecherche = personneRepository.findByNomUtilisateur(nomUtilisateur).orElseThrow(
			() -> new NotFoundException("Aucun utilisateur ne possède ce nom d'utilisateur")
		);

        return personneRecherche;
    }

	/**
	 * Permet de retourner le niveau de la personne ayant l'id passer en paramètre
	 * @param personne
	 * @return le niveau de la personne
	 */
	private Niveau getNiveauPersonne(Personne personne) {
		Integer nbPointsPersonne = personne.getNbPoints();
		Niveau niveauPersonne = this.niveauRepository.findByNbPoints(nbPointsPersonne);

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

	@Override
	public int nbTachesTermine(Personne personne) {
		return this.personneRepository.nbTachesTermine(personne);
	}

	private PersonneNiveauDto personneToPersonneNiveauDto(Personne personne) {
        String nomUtilisateur = personne.getNomUtilisateur();
        Integer nbPoints = personne.getNbPoints();
        Niveau niveauPersonne = this.getNiveauPersonne(personne);

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
