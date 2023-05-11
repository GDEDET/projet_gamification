package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneData;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompense;
import fr.neosoft.todogame.taches.GestionTacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecompensePersonneServiceTest {


	private PrerequisRecompense prerequisRecompense1 = new PrerequisRecompense();
	private Recompense recompenseNotBadge = RecompenseData.RECOMPENSE_NOT_BADGE;
	private Recompense recompenseBadge = RecompenseData.RECOMPENSE_BADGE;
	private List<Recompense> addRecompense1 = List.of(recompenseNotBadge, recompenseBadge);

	private Personne personne1 = PersonneData.setRecompensePersonne(PersonneData.BON_JEAN,addRecompense1);

	final Recompense RECOMPENSE_ENTITY = Mockito.mock(Recompense.class);

	@Mock
	RecompenseRepository recompenseRepository;

	@Mock
	PersonneService personneService;

	@InjectMocks
	private RecompensePersonneService recompensePersonneService;


	@BeforeEach
	void setUp() {

	}

	@Test
	void listeRecompenseBadges() {
		boolean estBadge = true;
		Mockito.when(RECOMPENSE_ENTITY.getEstBadge()).thenReturn(estBadge);
		List<Recompense> listeRecompenseBadges = recompensePersonneService.getListeRecompensesPersonne(personne1, estBadge);
		assertEquals(listeRecompenseBadges, List.of(recompenseBadge) );
	}

	@Test
	void listeRecompensePasBadges() {
		boolean estBadge = false;
		Mockito.when(RECOMPENSE_ENTITY.getEstBadge()).thenReturn(false);
		List<Recompense> listeRecompenseBadges = recompensePersonneService.getListeRecompensesPersonne(personne1, false);
		assertEquals(listeRecompenseBadges, List.of(recompenseNotBadge) );
	}

	@Test
	void majRecompensePersonne() {
	}
}