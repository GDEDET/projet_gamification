package fr.neosoft.todogame.recompenses.prerequisrecompense;

import fr.neosoft.todogame.niveaux.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrerequisRecompenseRepository extends JpaRepository<PrerequisRecompense, Long> {
	@Query("FROM PrerequisRecompense WHERE niveau = ?1")
	List<PrerequisRecompense> getPrerequisRecompenseByNiveau(Niveau niveau);
}
