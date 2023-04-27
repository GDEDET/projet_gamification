package fr.neosoft.todogame.taches;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private GestionTacheService gestionTacheService;

    @DisplayName("Test de la méthode save")
    @Test
    void save_shouldReturnSavedTache() {
        // Arrange
        Tache tache = TacheData.addOneTache(TacheData.DUPOND_MICHEL);
        when(tacheRepository.save(tache)).thenReturn(tache);

        // Act
        Tache savedTache = gestionTacheService.save(tache);

        // Assert
        verify(tacheRepository, times(1)).save(tache);
        assertEquals(tache, savedTache);
    }

    @DisplayName("Test de la méthode update")
    @Test
    void update_shouldReturnUpdatedTache() {
        // Arrange
        Tache tacheToUpdate = TacheData.addOneTache(TacheData.DUPOND_MICHEL);
        when(tacheRepository.save(tacheToUpdate)).thenReturn(tacheToUpdate);

        // Act
        Tache updatedTache = gestionTacheService.update(tacheToUpdate);

        // Assert
        verify(tacheRepository, times(1)).save(tacheToUpdate);
        assertEquals(tacheToUpdate, updatedTache);
    }

    @DisplayName("Test de la méthode findById")
    @Test
    void findById_shouldReturnTache() {
        // Arrange
        Tache tache = TacheData.addOneTache(TacheData.DUPOND_MICHEL);
        when(tacheRepository.findById(tache.getId())).thenReturn(Optional.of(tache));

        // Act
        Tache foundTache = gestionTacheService.findById(tache.getId());

        // Assert
        verify(tacheRepository, times(1)).findById(tache.getId());
        assertEquals(tache, foundTache);
    }

    @DisplayName("Test de la méthode findById avec une tache non trouvée")
    @Test
    void findById_shouldThrowExceptionWhenTacheNotFound() {
        // Arrange
        when(tacheRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> gestionTacheService.findById(1L));
        verify(tacheRepository, times(1)).findById(1L);
    }

    @DisplayName("Test de la méthode findAll")
    @Test
    void findAll_shouldReturnAllTaches() {
        // Arrange
        List<Tache> taches = TacheData.addListTaches(TacheData.DUPOND_MICHEL);
        when(tacheRepository.findAll()).thenReturn(taches);

        // Act & Assert
        assertIterableEquals(taches, gestionTacheService.findAll());
        verify(tacheRepository, times(1)).findAll();

        assertEquals(3, taches.size());
    }

    @DisplayName("Test de la méthode deleteById")
    @Test
    void deleteById_shouldReturnVoid() {
        // Arrange
        doNothing().when(tacheRepository).deleteById(1L);

        // Act
        gestionTacheService.deleteById(1L);

        // Assert
        verify(tacheRepository, times(1)).deleteById(1L);
    }
}