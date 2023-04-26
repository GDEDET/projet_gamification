package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.defis.GestionDefiInterface;
import fr.neosoft.todogame.defis.defis_personnes.GestionDefiPersonneInterface;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TacheServiceTest {

    @Mock
    private PersonneService personneService;

    @Mock
    private GestionDefiInterface gestionDefiInterface;

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private GestionDefiPersonneInterface gestionDefiPersonneInterface;

    private GestionTacheInterface gestionTacheInterface;

    private List<Tache> tachesList;

    private Personne personne;

    @BeforeEach
    public void setUp() {
        this.gestionTacheInterface = new GestionTacheService(tacheRepository, personneService, gestionDefiInterface, gestionDefiPersonneInterface);

        this.tachesList = Arrays.asList(
                new Tache(1L, "Tache 1", null, null, Priorite.BASSE, Difficulte.FACILE, Statut.A_FAIRE, 1),
                new Tache(2L, "Tache 2", null, null, Priorite.ELEVEE, Difficulte.FACILE, Statut.EN_COURS, 2),
                new Tache(3L, "Tache 3", null, null, Priorite.MOYENNE, Difficulte.MOYENNE, Statut.TERMINE, 3)
        );

        this.personne = new Personne();
        this.personne.setId(1L);
        this.personne.setTaches(tachesList);

        // Configurer le mock PersonneService pour renvoyer la personne créée ci-dessus lors de l'appel de la méthode findById
        when(personneService.findById(personne.getId())).thenReturn(personne);
    }

    @Test
    public void findAllByUser_shouldReturnTacheOfUser()
    {
        // Act
        Personne personne = personneService.findById(1L);
        Iterable<Tache> taches = gestionTacheInterface.findAllByUser(1L);

        //Assert
        assertEquals(personne, personneService.findById(1L));
        assertEquals(personne.getTaches(), taches);
    }
}
