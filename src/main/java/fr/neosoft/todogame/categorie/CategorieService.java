package fr.neosoft.todogame.categorie;

import fr.neosoft.todogame.utils.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategorieService extends CRUDService<Categorie> {
    public CategorieService(JpaRepository<Categorie, Long> repository) {
        super(repository);
    }
}
