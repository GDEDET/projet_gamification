package fr.neosoft.todogame.defis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefiRepository extends JpaRepository<Defi, Long> {
}
