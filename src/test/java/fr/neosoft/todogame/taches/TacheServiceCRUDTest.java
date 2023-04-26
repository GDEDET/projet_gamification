package fr.neosoft.todogame.taches;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TacheServiceCRUDTest {

    @Mock
    private TacheRepository tacheRepository;

    @InjectMocks
    private GestionTacheInterface gestionTacheInterface;

//    @Test
//    void save_shouldReturnSavedTache() {
//        // Arrange
//        Tache tache = new Tache(1L, "Tache 1", new Date(), null, Priorite.ELEVEE, Difficulte.FACILE, Statut.A_FAIRE, 10);
//        when(tacheRepository.save(tache)).thenReturn(tache);
//
//        // Act
//        Tache savedTache = gestionTacheInterface.save(tache);
//
//        // Assert
//        verify(tacheRepository, times(1)).save(tache);
//        assertEquals(tache, savedTache);
//    }
//
//    @Test
//    void update_shouldReturnUpdatedTache() {
//        // Arrange
//        Tache tacheToUpdate = new Tache(1L, "Tache 1", new Date(), null, Priorite.ELEVEE, Difficulte.FACILE, Statut.A_FAIRE, 10);
//        when(tacheRepository.save(tacheToUpdate)).thenReturn(tacheToUpdate);
//
//        // Act
//        Tache updatedTache = gestionTacheInterface.update(tacheToUpdate);
//
//        // Assert
//        verify(tacheRepository, times(1)).save(tacheToUpdate);
//        assertEquals(tacheToUpdate, updatedTache);
//    }
//
//    @Test
//    void findById_shouldReturnTache() {
//        // Arrange
//        Tache tache = new Tache(1L, "Tache 1", new Date(), null, Priorite.ELEVEE, Difficulte.FACILE, Statut.A_FAIRE, 10);
//        when(tacheRepository.findById(1L)).thenReturn(Optional.of(tache));
//
//        // Act
//        Tache foundTache = gestionTacheInterface.findById(1L);
//
//        // Assert
//        verify(tacheRepository, times(1)).findById(1L);
//        assertEquals(tache, foundTache);
//    }
//
//    @Test
//    void findById_shouldThrowExceptionWhenTacheNotFound() {
//        // Arrange
//        when(tacheRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        Assertions.assertThrows(RuntimeException.class, () -> gestionTacheInterface.findById(1L));
//        verify(tacheRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void findAll_shouldReturnAllTaches() {
//        // Arrange
//        List<Tache> taches = new ArrayList<>();
//        taches.add(new Tache(1L, "Tache 1", new Date(), null, Priorite.ELEVEE, Difficulte.FACILE, Statut.A_FAIRE, 10));
//        taches.add(new Tache(2L, "Tache 2", new Date(), null, Priorite.MOYENNE, Difficulte.MOYENNE, Statut.A_FAIRE, 20));
//        when(tacheRepository.findAll()).thenReturn(taches);
//
//        // Act & Assert
//        assertIterableEquals(taches, gestionTacheInterface.findAll());
//        verify(tacheRepository, times(1)).findAll();
//
//        assertEquals(2, taches.size());
//    }
//
//    @Test
//    void deleteById_shouldReturnVoid() {
//        // Arrange
//        doNothing().when(tacheRepository).deleteById(1L);
//
//        // Act
//        gestionTacheInterface.deleteById(1L);
//
//        // Assert
//        verify(tacheRepository, times(1)).deleteById(1L);
//    }
}