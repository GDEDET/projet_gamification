package fr.neosoft.todogame.personnes;


import fr.neosoft.todogame.auth.roles.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonneRepository extends CrudRepository<Personne, Long> {

    Personne findByEmail(String email);

    List<Personne> findByRoles_Authority(String role);

    Personne findByNomUtilisateur(String nomUtilisateur);
}
