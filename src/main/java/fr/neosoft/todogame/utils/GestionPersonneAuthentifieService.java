package fr.neosoft.todogame.utils;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GestionPersonneAuthentifieService implements GestionPersonneAuthentifieInterface{

    @Autowired
    PersonneService personneService;

    @Override
    public Personne getPersonneAuthentifie() {
        Authentication utilisateurAuthentifie = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) utilisateurAuthentifie.getPrincipal();
        return this.personneService.findByNomUtilisateur(userDetails.getUsername());
    }
}
