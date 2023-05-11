package fr.neosoft.todogame.auth;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.exceptions.NotFoundException;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * Permet d'enregistrer un nouvel utilisateur dans le système en lui attribuant un role PERSONNE par défaut
     *
     * @param dto : les informations de la personne à enregistrer
     * @return l'utilisateur qui a été créée
     */
    public Personne register(RegisterRequestDto dto){
        Personne utilisateur = new Personne();
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        Optional<Role> personneRole = roleRepository.findByAuthority("PERSONNE");
        if(personneRole.isEmpty()){
            throw new NotFoundException("Le role PERSONNE n'a pas été trouvé dans la base");
        }
        utilisateur.setRoles(List.of(personneRole.get()));
        String password = passwordEncoder.encode(dto.getMotDePasse());
        utilisateur.setMotDePasse(password);
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        return this.repository.save(utilisateur);
    }

    /**
     * Permet de retourner un utilisateur à partir de son identifiant (ici le mail)
     * @param email : l'identifiant de l'utilisateur
     * @return l'utilisateur chargé à partir des données de la personne associée
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Personne utilisateur = repository.findByEmail(email);
        if (utilisateur == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Aucun utilisateur ne possède le mail "+email);
        }
        return new User(utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse(), utilisateur.getRoles());
    }

}
