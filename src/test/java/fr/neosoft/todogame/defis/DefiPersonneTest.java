package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.defis_personnes.DefiPersonne;
import fr.neosoft.todogame.defis_personnes.DefiPersonneRepository;
import fr.neosoft.todogame.defis_personnes.DefiPersonneService;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneData;
import fr.neosoft.todogame.personnes.PersonneInterface;
import fr.neosoft.todogame.recompenses.RecompensePersonneInterface;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;

import static org.junit.Assert.assertEquals;
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

@ExtendWith(MockitoExtension.class)
public class DefiPersonneTest {

    @Mock
    DefiPersonneRepository repository;

    @Mock
    DefiRepository defiRepository;

    @Mock
    DefiPersonneRepository defiPersonneRepository;

    @Mock
    GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;

    @Mock
    DefiInterface defiInterface;

    @Mock
    PersonneInterface personneInterface;

    @Mock
    RecompensePersonneInterface recompensePersonneInterface;

    @InjectMocks
    DefiPersonneService DefiPersonneService;

    Personne personne;

    @BeforeEach
    public void setUp()
    {
        this.personne = PersonneData.DUPOND_MICHEL;
        DefiData.addListDefiPersonneToPersonne(this.personne);
    }

    @DisplayName("findAllByPersonneConnecte retourne liste de DefiPersonne")
    @Test
    public void findAllByPersonneConnecte_shouldReturnDefiPersonneList()
    {
        // Arrange
        when(gestionPersonneAuthentifieInterface.getPersonneAuthentifie()).thenReturn(personne);
        List<DefiPersonne> listDefiPersonne = personne.getDefisARealiser();

        // Act
        List<DefiPersonne> listDefiPersonneReturned = DefiPersonneService.findAllByPersonneConnecte();

        // Assert
        assertEquals(listDefiPersonneReturned, listDefiPersonne);
    }

    @DisplayName("incrementerNbTachesTerminees si défi avec objectif de taches")
    @Test
    public void incrementerNbTachesTerminees_shouldIncrementSeulementSiObjectifTaches()
    {
        // Act
        DefiPersonneService.incrementerNbTachesTerminees(personne);
        DefiPersonneService.incrementerNbTachesTerminees(personne);

        // Assert
        assertEquals(2, personne.getDefisARealiser().get(0).getNbTachesTerminees());
        assertEquals(0, personne.getDefisARealiser().get(1).getNbTachesTerminees());
        assertEquals(2, personne.getDefisARealiser().get(2).getNbTachesTerminees());
        assertEquals(2, personne.getDefisARealiser().get(3).getNbTachesTerminees());
    }

    @DisplayName("incrementerNbTachesTerminees doit terminer le défi si objectif atteint")
    @Test
    public void incrementerNbTachesTerminees_shouldTerminerDefiSiObjectifAtteint()
    {
        // Act
        DefiPersonneService.incrementerNbTachesTerminees(personne);
        DefiPersonneService.incrementerNbTachesTerminees(personne);
        DefiPersonneService.incrementerNbTachesTerminees(personne);

        // Assert
        assertEquals(0, personne.getDefisARealiser().get(0).getNbTachesTerminees());
        assertEquals(3, personne.getDefisARealiser().get(1).getNbTachesTerminees());
        assertEquals(3, personne.getDefisARealiser().get(2).getNbTachesTerminees());

        assertEquals(3, personne.getDefisARealiser().size());
    }

    @DisplayName("incrementerNbPointsGagnes si défi avec objectif de point")
    @Test
    void incrementerNbPointsGagnes_shouldIncrementSeulementSiObjectifPoint() {
        // Act
        DefiPersonneService.incrementerNbPointsGagnes(personne, 150);

        // Assert
        // DEFI 1 : pas de points car par d'objectif de points
        assertEquals(0, personne.getDefisARealiser().get(0).getNbPointsGagnes());
        assertEquals(150, personne.getDefisARealiser().get(1).getNbPointsGagnes());
        assertEquals(150, personne.getDefisARealiser().get(2).getNbPointsGagnes());
        // DEFI 4 : pas de points car par d'objectif de points
        assertEquals(0, personne.getDefisARealiser().get(3).getNbPointsGagnes());
    }

    @DisplayName("incrementerNbPointsGagnes doit terminer le défi si objectif atteint")
    @Test
    void incrementerNbPointsGagnes_shouldTerminerDefiSiObjectifAtteint() {
        // Act 1
        DefiPersonneService.incrementerNbPointsGagnes(personne, 150);

        // Assert 1
        assertEquals(0, personne.getDefisARealiser().get(0).getNbPointsGagnes());
        assertEquals(150, personne.getDefisARealiser().get(1).getNbPointsGagnes());
        assertEquals(150, personne.getDefisARealiser().get(2).getNbPointsGagnes());
        assertEquals(0, personne.getDefisARealiser().get(3).getNbPointsGagnes());

        // Toujours 4 défis
        assertEquals(4, personne.getDefisARealiser().size());

        // Act 2
        DefiPersonneService.incrementerNbPointsGagnes(personne, 250);

        // Assert 2 le DEFI 2 doit être terminé
        // DEFI 1 : pas de points car par d'objectif de points
        assertEquals(0, personne.getDefisARealiser().get(0).getNbPointsGagnes());
        // DEFI 3 | 500 points, car 150 + 250 = 400 points et le DEFI 2 avait une récompense de 100 points
        assertEquals(500, personne.getDefisARealiser().get(1).getNbPointsGagnes());
        // DEFI 4 : pas de points car par d'objectif de points
        assertEquals(0, personne.getDefisARealiser().get(2).getNbPointsGagnes());

        assertEquals(3, personne.getDefisARealiser().size());
    }

    @DisplayName("ajouterDefi à une personne")
    @Test
    public void ajouterDefi_shouldAddDefiToPersonne()
    {
        // Arrange
        personne.setDefisARealiser(new ArrayList<>());
        Defi defi = DefiData.DEFI_2;
        when(defiInterface.findById(defi.getId())).thenReturn(defi);
        when(personneInterface.save(personne)).thenReturn(personne);
        when(gestionPersonneAuthentifieInterface.getPersonneAuthentifie()).thenReturn(personne);

        // Act
        List<DefiPersonne> defiARealiser = DefiPersonneService.ajouterDefi(defi.getId());
        System.out.println(defiARealiser);
        // Assert
        assertEquals(defiARealiser.size(), 1);
        assertEquals(defiARealiser.get(0).getDefi(), defi);
        assertEquals(defiARealiser.get(0).getPersonne(), personne);
    }
}
