package fr.neosoft.todogame.personnes;


import org.springframework.data.repository.CrudRepository;

public interface PersonneRepository extends CrudRepository<Personne, Long> {

    Personne findByEmail(String email);

}
