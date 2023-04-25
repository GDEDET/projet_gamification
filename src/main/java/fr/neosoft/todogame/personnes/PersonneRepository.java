package fr.neosoft.todogame.personnes;


import fr.neosoft.todogame.taches.Statut;
import fr.neosoft.todogame.taches.Tache;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Personne findByEmail(String email);

    List<Personne> findByRoles_Authority(String role);

    List<Personne> findAllByOrderByNbPointsDesc();

    @Query("SELECT p FROM Personne p JOIN p.taches t WHERE t.statut = 'TERMINE'")
    List<Personne> findAllByOrderByTachesRealiseesDesc();

    Personne findByNomUtilisateur(String nomUtilisateur);
}
