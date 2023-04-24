package fr.neosoft.todogame.categorie;

import fr.neosoft.todogame.categorie.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
