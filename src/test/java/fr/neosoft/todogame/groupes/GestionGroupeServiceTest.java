package fr.neosoft.todogame.groupes;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GestionGroupeServiceTest {

    GestionGroupeService service;
    GroupeRepository groupeRepository;
    PersonneInterface personneInterface;

    Personne membre = new Personne(1L, "test", "test", "test@yopmail.com", "test.test", "123", 0);
    Groupe groupe = new Groupe(1L, "groupe1", new Personne(), new ArrayList<>());

    @BeforeEach
    void setUp() {
        groupeRepository = Mockito.mock(GroupeRepository.class);
        personneInterface = Mockito.mock(PersonneInterface.class);
        Mockito.when(personneInterface.findById(membre.getId())).thenReturn(membre);
        service = new GestionGroupeService(groupeRepository, personneInterface);
        Mockito.when(groupeRepository.findById(groupe.getId())).thenReturn(Optional.ofNullable(groupe));
    }

    @Test
    @DisplayName("Ajouter un membre à un groupe")
    void addMembre() {
        service.addMembre(groupe.getId(), membre.getId());
        assertEquals(1, groupe.getMembres().size());
        assertEquals(membre.getPrenom(), groupe.getMembres().get(0).getPrenom());
    }

    @Test
    @DisplayName("Supprime un membre à un groupe")
    void removeMembre() {
        groupe.getMembres().add(membre);
        service.removeMembre(groupe.getId(), membre.getId());
        assertFalse(groupe.getMembres().contains(membre));
    }
}