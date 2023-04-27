package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.defis_personnes.DefiPersonne;
import fr.neosoft.todogame.defis_personnes.DefiPersonneRepository;
import fr.neosoft.todogame.defis_personnes.GestionDefiPersonneService;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneData;
import fr.neosoft.todogame.personnes.PersonneInterface;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GestionDefisPersonneTest {

    @Mock
    DefiPersonneRepository repository;

    @Mock
    DefiRepository defiRepository;

    @Mock
    GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;

    @Mock
    GestionDefiInterface gestionDefiInterface;

    @Mock
    PersonneInterface personneInterface;

    @InjectMocks
    GestionDefiPersonneService gestionDefiPersonneService;

    Personne personne;

    @BeforeEach
    public void setUp()
    {
        this.personne = PersonneData.getNewPersonne();
        DefiData.addListDefiPersonneToPersonne(personne);
    }

    @DisplayName("findAllByPersonneConnecte retourne liste de DefiPersonne")
    @Test
    public void findAllByPersonneConnecte_shouldReturnDefiPersonneList()
    {
        // Arrange
        when(gestionPersonneAuthentifieInterface.getPersonneAuthentifie()).thenReturn(personne);
        List<DefiPersonne> listDefiPersonne = personne.getDefisARealiser();

        // Act
        List<DefiPersonne> listDefiPersonneReturned = gestionDefiPersonneService.findAllByPersonneConnecte();

        // Assert
        assertEquals(listDefiPersonneReturned, listDefiPersonne);
    }

    @DisplayName("incrementerNbTachesTerminees si défi AVEC un objectif de taches")
    @Test
    public void incrementerNbTachesTerminees_shouldIncrementNbTachesTerminees()
    {
        // Arrange
        List<DefiPersonne> listDefiPersonne = personne.getDefisARealiser();

        // Act
        gestionDefiPersonneService.incrementerNbTachesTerminees(personne);

        // Assert
        for (DefiPersonne defiPersonne : listDefiPersonne) {
            if (defiPersonne.getDefi().getNbTachesObjectif() > 0) {
                assertEquals(defiPersonne.getNbTachesTerminees(), 1);
            }
        }

        gestionDefiPersonneService.incrementerNbTachesTerminees(personne);

        for (DefiPersonne defiPersonne : listDefiPersonne) {
            if (defiPersonne.getDefi().getNbTachesObjectif() > 0) {
                assertEquals(defiPersonne.getNbTachesTerminees(), 2);
            }
        }
    }

    @DisplayName("incrementerNbTachesTerminees si défi SANS objectif de taches")
    @Test
    public void incrementerNbTachesTerminees_should_NOT_IncrementNbTachesTerminees()
    {
        // Arrange
        List<DefiPersonne> listDefiPersonne = personne.getDefisARealiser();

        // Act
        gestionDefiPersonneService.incrementerNbTachesTerminees(personne);

        // Assert
        for (DefiPersonne defiPersonne : listDefiPersonne) {
            if (defiPersonne.getDefi().getNbTachesObjectif() == 0) {
                assertEquals(defiPersonne.getNbTachesTerminees(), 0);
            }
        }
    }

    @DisplayName("incrementerNbPointsGagnes si défi AVEC objectif de points")
    @Test
    public void incrementerNbPointsGagnes_shouldIncrementNbPointsGagnes()
    {
        // Arrange
        List<DefiPersonne> listDefiPersonne = personne.getDefisARealiser();

        // Act
        gestionDefiPersonneService.incrementerNbPointsGagnes(personne, 100);

        // Assert
        for (DefiPersonne defiPersonne : listDefiPersonne) {
            if (defiPersonne.getDefi().getNbPointsObjectif() > 0) {
                assertEquals(defiPersonne.getNbPointsGagnes(), 100);
            }
        }

        gestionDefiPersonneService.incrementerNbPointsGagnes(personne, 200);

        for (DefiPersonne defiPersonne : listDefiPersonne) {
            if (defiPersonne.getDefi().getNbPointsObjectif() > 0) {
                assertEquals(defiPersonne.getNbPointsGagnes(), 300);
            }
        }
    }

    @DisplayName("incrementerNbPointsGagnes si défi SANS objectif de points")
    @Test
    public void incrementerNbPointsGagnes_should_NOT_IncrementNbPointsGagnes()
    {
        // Arrange
        List<DefiPersonne> listDefiPersonne = personne.getDefisARealiser();

        // Act
        gestionDefiPersonneService.incrementerNbPointsGagnes(personne, 100);

        // Assert
        for (DefiPersonne defiPersonne : listDefiPersonne) {
            if (defiPersonne.getDefi().getNbPointsObjectif() == 0) {
                assertEquals(defiPersonne.getNbPointsGagnes(), 0);
            }
        }
    }

    @DisplayName("ajouterDefi à une personne")
    @Test
    public void ajouterDefi_shouldAddDefiToPersonne()
    {
        // Arrange
        personne.setDefisARealiser(new ArrayList<>());
        Defi defi = DefiData.DEFI_2;
        when(gestionDefiInterface.findById(defi.getId())).thenReturn(defi);
        when(personneInterface.save(personne)).thenReturn(personne);
        when(gestionPersonneAuthentifieInterface.getPersonneAuthentifie()).thenReturn(personne);

        // Act
        List<DefiPersonne> defiARealiser = gestionDefiPersonneService.ajouterDefi(defi.getId());

        // Assert
        assertEquals(defiARealiser.size(), 1);
        assertEquals(defiARealiser.get(0).getDefi(), defi);
        assertEquals(defiARealiser.get(0).getPersonne(), personne);
    }
}
