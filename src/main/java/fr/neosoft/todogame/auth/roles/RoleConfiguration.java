package fr.neosoft.todogame.auth.roles;

import fr.neosoft.todogame.auth.AuthService;
import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleConfiguration {

    public static Role MANAGER = null;
    public static Role PERSONNE = null;

    @Autowired
    private void init(RoleRepository roleRepository, AuthService service, PersonneRepository personneRepository){
        if(roleRepository.findByAuthority("MANAGER").isEmpty()){
            MANAGER = roleRepository.save(new Role(1L, "MANAGER"));
            PERSONNE = roleRepository.save(new Role(2L, "PERSONNE"));
            Personne manager = service.register(new RegisterRequestDto("admin", "admin", "admin", "admin"));
            manager.setRoles(List.of(MANAGER));
            personneRepository.save(manager);
        }
    }
}
