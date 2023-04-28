package fr.neosoft.todogame.auth.roles;

import fr.neosoft.todogame.auth.AuthService;
import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneRepository;
import fr.neosoft.todogame.taches.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
public class RoleConfiguration {

    public static Role ADMIN = null;
    public static Role PERSONNE = null;

    @Autowired
    private void init(RoleRepository roleRepository, AuthService service, PersonneRepository personneRepository, TacheRepository tacheRepository){
        if(roleRepository.findByAuthority("ADMIN").isEmpty() && personneRepository.findByRoles_Authority("ADMIN").isEmpty()){
            ADMIN = roleRepository.save(new Role(1L, "ADMIN"));
            PERSONNE = roleRepository.save(new Role(2L, "PERSONNE"));
            Personne manager = service.register(new RegisterRequestDto("admin", "admin", "admin@yopmail.com", "admin", "admin"));
            manager.setRoles(List.of(PERSONNE,ADMIN));
            Tache tache = new Tache();
            tache.setPersonne(manager);
            tache.setStatut(Statut.EN_COURS);
            tache.setPriorite(Priorite.MOYENNE);
            tache.setDifficulte(Difficulte.FACILE);
            tache.setDescription("Tache 1");
            tache.setNbPoints(0);
            manager.getTaches().add(tacheRepository.save(tache));
            personneRepository.save(manager);
        }
    }
}
