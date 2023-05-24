package fr.neosoft.todogame.recompenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneData;
import fr.neosoft.todogame.personnes.PersonneInterface;
import fr.neosoft.todogame.personnes.PersonneNiveauDto;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompense;

@ExtendWith(MockitoExtension.class)
class RecompensePersonneServiceTest {


	private PrerequisRecompense prerequisRecompense1;
	private Recompense recompenseNotBadge;
	private Recompense recompenseBadge;
	private Recompense recompenseWithPrerequis;
	private Recompense recompenseWithPrerequisDateEcheance;
	private List<Recompense> addRecompense1;
	private List<Recompense> addRecompense2;
	private List<Recompense> addRecompense3;
	private Niveau niveau1;

	private Personne personne1;
	private Personne personne2;
	private PersonneNiveauDto personneNiveauDtoPersonne1;
	private PersonneNiveauDto personneNiveauDtoPersonne2;


	final Recompense RECOMPENSE_ENTITY = mock(Recompense.class);

	@Mock
	RecompenseRepository recompenseRepository;

	@Mock
	PersonneService personneService;

	@Mock
	PersonneInterface personneInterface;

	@InjectMocks
	private RecompensePersonneService recompensePersonneService;

	@BeforeEach
	void setUp() {
		prerequisRecompense1 = RecompenseData.PREREQUIS_RECOMPENSE;
		recompenseNotBadge = RecompenseData.RECOMPENSE_NOT_BADGE;
		recompenseBadge = RecompenseData.RECOMPENSE_BADGE;
		recompenseWithPrerequis = RecompenseData.RECOMPENSE_WITH_PREREQUIS;
		recompenseWithPrerequisDateEcheance = RecompenseData.RECOMPENSE_WITH_PREREQUIS_ECEHANCE;
		addRecompense1 = List.of(recompenseNotBadge, recompenseBadge);
		addRecompense2 = List.of(recompenseNotBadge, recompenseBadge, recompenseWithPrerequis, recompenseWithPrerequisDateEcheance);
		addRecompense3 = List.of(recompenseNotBadge, recompenseBadge, recompenseWithPrerequisDateEcheance);
		niveau1 = new Niveau(1, 400);
		personne1 = PersonneData.setRecompensePersonne(PersonneData.BON_JEAN,addRecompense1);
		personne2 = PersonneData.DUPOND_MICHEL;
		personneNiveauDtoPersonne1 = PersonneData.getPersonneNiveauDto(personne1, niveau1);
		personneNiveauDtoPersonne2 = PersonneData.getPersonneNiveauDto(personne2, niveau1);
	}

	@Test
	void listeRecompenseBadges() {
		boolean estBadge = true;
		when(RECOMPENSE_ENTITY.getEstBadge()).thenReturn(estBadge);
		List<Recompense> listeRecompenseBadges = recompensePersonneService.getListeRecompensesPersonne(personne1, estBadge);
		assertEquals(listeRecompenseBadges, List.of(recompenseBadge) );
	}

	@Test
	void listeRecompensePasBadges() {
		boolean estBadge = false;
		when(RECOMPENSE_ENTITY.getEstBadge()).thenReturn(estBadge);
		List<Recompense> listeRecompenseBadges = recompensePersonneService.getListeRecompensesPersonne(personne1, estBadge);
		assertEquals(listeRecompenseBadges, List.of(recompenseNotBadge) );
	}

	@DisplayName("Test l'ajout d'une récompense sans prérequis à une personne")
	@Test
	void majRecompenseSansPrerequisPersonne() {
		when(personneInterface.infosNiveauPersonne(personne2)).thenReturn(personneNiveauDtoPersonne2);
		when(personneInterface.nbTachesTermine(personne2)).thenReturn(5);
		when(recompenseRepository.findRecompenseByNiveau(personneNiveauDtoPersonne2.getNiveau())).thenReturn(new ArrayList<>(addRecompense1));

		recompensePersonneService.majRecompensePersonne(personne2);

		assertEquals(300, personne2.getNbPoints());
        assertEquals(addRecompense1, personne2.getRecompenses());
	}

	@DisplayName("Test l'ajout à une personne d'une récompense avec prérequis sans date d'echeance")
	@Test
	void majRecompenseAvecPrerequisSansEcheancePersonne() {
		when(personneInterface.infosNiveauPersonne(personne1)).thenReturn(personneNiveauDtoPersonne1);
		when(personneInterface.nbTachesTermine(personne1)).thenReturn(5);
		when(recompenseRepository.findRecompenseByNiveau(personneNiveauDtoPersonne1.getNiveau())).thenReturn(new ArrayList<>(addRecompense2));

		recompensePersonneService.majRecompensePersonne(personne1);
		assertEquals(300, personne1.getNbPoints());
        assertEquals(List.of(recompenseNotBadge, recompenseBadge, recompenseWithPrerequis), personne1.getRecompenses());
	}

	@DisplayName("Test ne pas ajouter à une personne une récompense avec prérequis date d'echeance passée")
	@Test
	void majRecompenseAvecPrerequisAvecEcheancePersonne() {
		when(personneInterface.infosNiveauPersonne(personne1)).thenReturn(personneNiveauDtoPersonne1);
		when(personneInterface.nbTachesTermine(personne1)).thenReturn(5);
		when(recompenseRepository.findRecompenseByNiveau(personneNiveauDtoPersonne1.getNiveau())).thenReturn(new ArrayList<>(addRecompense3));

		recompensePersonneService.majRecompensePersonne(personne1);
		assertEquals(200, personne1.getNbPoints());
        assertEquals(addRecompense1, personne1.getRecompenses());
	}
}