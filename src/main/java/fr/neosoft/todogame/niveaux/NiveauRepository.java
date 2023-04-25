package fr.neosoft.todogame.niveaux;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NiveauRepository extends CrudRepository<Niveau, Long> {
	@Query("FROM Niveau WHERE nbPointsRequis >= ?1 AND nbPointsRequis <= ?1")
	Niveau findByNbPoints(int nbPoints);
}
