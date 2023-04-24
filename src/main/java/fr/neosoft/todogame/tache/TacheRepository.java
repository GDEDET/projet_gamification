package fr.neosoft.todogame.tache;

import fr.neosoft.todogame.tache.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheRepository extends JpaRepository<Tache, Long> {
}
