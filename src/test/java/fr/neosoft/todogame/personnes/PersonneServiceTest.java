package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.exceptions.NotFoundException;
import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.niveaux.NiveauRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonneServiceTest {
	@Mock
	private PersonneRepository personneRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private NiveauRepository niveauRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	private PersonneInterface personneInterface;

	private PersonneService personneService;

	private Personne personne;

	private Role rolePersonne;
	private Personne personne1;
	private Personne personne2;
	private Personne personne3;
	private RegisterRequestDto registerRequestDto;
	private Niveau niveau1;


	@BeforeEach
	public void setUp() {
		this.personneService = new PersonneService(personneRepository, roleRepository, niveauRepository);
		this.personneInterface = this.personneService;
		rolePersonne = new Role(2L, "PERSONNE");
		personne1 = PersonneData.DUPOND_MICHEL;
		personne3 = PersonneData.BON_JEAN;
		personne2 = new Personne(6L,"Levant","Rose","roseLevant@yopmail.fr","roseLevant","$2a$10$aY/p62psOguGGn3HrIYGg.Ey/Eaaud.35mtbiwTi/m62Ct6XepdW.",1500);
		registerRequestDto = new RegisterRequestDto("jeanBon", "jeanBon", "jeanBon@yopmail.fr", "Bon", "Jean");
		niveau1 = new Niveau(1, 400);
	}
//
//	@Test
//	@DisplayName("Créer une personne")
//	void creerPersonne() {
//		when(roleRepository.findByAuthority("PERSONNE")).thenReturn(Optional.ofNullable(rolePersonne));
//		when(passwordEncoder.encode("jeanBon")).thenReturn("$2a$10$sEebmn5b2WV1jNXJtrTdOO0r.s1PCzdhuIN2K5jxqzSj25Kah1x/S");
//		assertEquals(personne1, this.personneService.creerPersonne(registerRequestDto));
//	}

	@Test
	@DisplayName("Retourne la personne ayant le nom passé en paramètre")
	void findByNomUtilisateurExist() {
		String nomUtilisateur = "jeanBon";
		when(personneRepository.findByNomUtilisateur(nomUtilisateur)).thenReturn(Optional.ofNullable(personne1));
		assertEquals(personne1, this.personneService.findByNomUtilisateur(nomUtilisateur));
	}

	@Test
	@DisplayName("Retourne une erreur si le nom passé en paramètre n'existe pas")
	void findByNomUtilisateurWhenPersonneNotFound() {
		String nomUtilisateur = "nope";
		when(personneRepository.findByNomUtilisateur(nomUtilisateur)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> this.personneService.findByNomUtilisateur(nomUtilisateur));
		verify(personneRepository, times(1)).findByNomUtilisateur(nomUtilisateur);
	}

	@Test
	@DisplayName("Retourne un PersonneDto")
	void infosNiveauPersonne() {
		when(niveauRepository.findByNbPoints(0)).thenReturn(niveau1);
		PersonneNiveauDto personneNiveauDto = new PersonneNiveauDto("jeanBon", 0, niveau1);
		assertEquals(personneNiveauDto, this.personneService.infosNiveauPersonne(personne1));
	}

}
