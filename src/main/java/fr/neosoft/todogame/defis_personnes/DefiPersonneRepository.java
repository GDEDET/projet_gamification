package fr.neosoft.todogame.defis_personnes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefiPersonneRepository extends JpaRepository<DefiPersonne, PKDefiPersonne> {
}
