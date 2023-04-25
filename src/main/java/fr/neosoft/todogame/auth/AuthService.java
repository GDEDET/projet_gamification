package fr.neosoft.todogame.auth;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.exceptions.NotFoundException;
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
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        Optional<Role> personneRole = roleRepository.findByAuthority("PERSONNE");
        utilisateur.setRoles(List.of(personneRole.get()));
        String password = passwordEncoder.encode(dto.getMotDePasse());
        utilisateur.setMotDePasse(password);
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        return this.repository.save(utilisateur);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Personne utilisateur = repository.findByEmail(email);
        if (utilisateur == null){
            throw new NotFoundException("Aucun utilisateur ne possède le mail "+email);
        }
        return new User(utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse(), utilisateur.getRoles());
    }

}
