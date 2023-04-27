package fr.neosoft.todogame.personnes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Personne findByEmail(String email);

    List<Personne> findByRoles_Authority(String role);

    List<Personne> findAllByOrderByNbPointsDesc();

    @Query("SELECT p FROM Personne p JOIN p.taches t WHERE t.statut = fr.neosoft.todogame.taches.Statut.TERMINE")
    List<Personne> findAllByOrderByTachesRealiseesDesc();

    Personne findByNomUtilisateur(String nomUtilisateur);
}
