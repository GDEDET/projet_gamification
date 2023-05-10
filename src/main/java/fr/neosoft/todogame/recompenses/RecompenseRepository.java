package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.niveaux.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecompenseRepository extends JpaRepository<Recompense, Long> {
	@Query("FROM Recompense WHERE niveau = ?1")
	List<Recompense> findRecompenseByNiveau(Niveau niveau);
}
