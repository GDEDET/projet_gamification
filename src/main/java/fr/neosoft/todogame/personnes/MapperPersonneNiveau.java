package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.niveaux.Niveau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperPersonneNiveau {

	@Autowired
	private PersonneService personneService;

	public PersonneDto toDto(Personne personne, Niveau niveau) {
		String nomUtilisateur = personne.getNomUtilisateur();
		int nbPoints = personne.getNbPoints();
		int niveauPersonne = personneService.niveauPersonne(personne.getId()).getNiveau();

		return new PersonneDto(nomUtilisateur, nbPoints, niveauPersonne);
	}
}
