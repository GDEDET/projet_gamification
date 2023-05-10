package fr.neosoft.todogame.personnes;


import fr.neosoft.todogame.taches.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Personne findByEmail(String email);

    List<Personne> findByRoles_Authority(String role);

    List<Personne> findAllByOrderByNbPointsDesc();

    @Query("SELECT p FROM Personne p JOIN p.taches t WHERE t.statut = Statut.TERMINE")
    List<Personne> findAllByOrderByTachesRealiseesDesc();

    Optional<Personne> findByNomUtilisateur(String nomUtilisateur);

    @Query("SELECT COUNT(t.id) FROM Personne p JOIN p.taches t WHERE p = ?1 AND t.statut = Statut.TERMINE")
	int nbTachesTermine(Personne personne);
}
