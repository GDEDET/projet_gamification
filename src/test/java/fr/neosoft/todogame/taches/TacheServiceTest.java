package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.defis.DefiInterface;
import fr.neosoft.todogame.defis_personnes.DefiPersonneInterface;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TacheServiceTest {

    @Mock
    private PersonneService personneService;

    @Mock
    private DefiInterface defiInterface;

    @Mock
    private DefiPersonneInterface defiPersonneInterface;

    @Mock
    private GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;

    @Mock
    private TacheRepository tacheRepository;

    @InjectMocks
    private GestionTacheService gestionTacheService;

    private List<Tache> tachesList;

    private Personne personne;

    @BeforeEach
    public void setUp() {
        this.personne = TacheData.DUPOND_MICHEL;
        this.tachesList = TacheData.addListTaches(personne);
        this.personne.setTaches(tachesList);
    }

    @DisplayName("Test de la méthode findAllByUser")
    @Test
    public void findAllByUser_shouldReturnTacheOfUser()
    {
        // Arrange
        when(gestionPersonneAuthentifieInterface.getPersonneAuthentifie()).thenReturn(personne);

        // Act
        Iterable<Tache> taches = gestionTacheService.findAllByUserConnected();

        //Assert
        assertEquals(this.tachesList, taches);
        assertEquals(personne.getTaches(), taches);
    }

    @DisplayName("Test de la méthode terminerTache")
    @Test
    public void terminerTache_shouldReturnTacheTerminer()
    {
        // Arrange
        when(tacheRepository.findById(this.tachesList.get(0).getId())).thenReturn(Optional.ofNullable(this.tachesList.get(0)));

        // Act
        gestionTacheService.terminerTache(this.tachesList.get(0).getId());
        assertEquals(Statut.TERMINE, this.tachesList.get(0).getStatut());
        assertNotNull(this.tachesList.get(0).getDateRealisation());
    }

    @DisplayName("Test de la méthode creerTache")
    @Test
    public void creerTache_shouldReturnTache()
    {
        // Arrange
        TacheDto tacheDTO = new TacheDto("tache 1", new Date(), Priorite.MOYENNE, Difficulte.FACILE);

        // Act
        Tache tache = gestionTacheService.creerTache(this.personne, tacheDTO);

        //Assert tache
        assertEquals(tacheDTO.getDescription(), tache.getDescription());
        assertEquals(tacheDTO.getDateEcheance(), tache.getDateEcheance());
        assertEquals(tacheDTO.getPriorite(), tache.getPriorite());
        assertEquals(tacheDTO.getDifficulte(), tache.getDifficulte());
        assertEquals(Statut.EN_COURS, tache.getStatut());
        assertEquals(tacheDTO.getDifficulte().getNbPoint() + tacheDTO.getPriorite().getNbPoint(), tache.getNbPoints());

        //Assert personne
        assertEquals(this.personne.getTaches().get(this.personne.getTaches().size() - 1), tache);
        verify(personneService, times(1)).save(this.personne);
        assertEquals(Tache.class, tache.getClass());
    }
}