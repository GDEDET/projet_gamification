package fr.neosoft.todogame.auth;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private PersonneRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    public Personne register(RegisterRequestDto dto){
        Personne utilisateur = new Personne();
        utilisateur.setUsername(dto.getUsername());
        Optional<Role> personneRole = roleRepository.findByAuthority("PERSONNE");
        utilisateur.setRoles(List.of(personneRole.get()));
        String password = passwordEncoder.encode(dto.getPassword());
        utilisateur.setPassword(password);
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        return this.repository.save(utilisateur);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personne utilisateur = repository.findByUsername(username);
        if (utilisateur == null){
            throw new UsernameNotFoundException("Aucun utilisateur ne possède le Username "+username);
        }
        return new User(utilisateur.getUsername(), utilisateur.getPassword(), utilisateur.getRoles());
    }

}
