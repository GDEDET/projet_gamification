package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.exceptions.NotFoundException;
import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneInterface;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompense;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompenseInterface;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompenseRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecompensePersonneService implements RecompensePersonneInterface {

	@Autowired
	RecompenseRepository recompenseRepository;
	PrerequisRecompenseInterface prerequisRecompenseInterface;
	PersonneInterface personneInterface;

	@Override
	public List<Recompense> getListeRecompensesPersonne(@NotNull Personne personne, @NotNull boolean estBadge) {
		List<Recompense> badges = personne.getRecompenses().stream().filter(
				recompense -> recompense.getEstBadge().equals(estBadge)
			).collect(Collectors.toList());

		return badges;
	}

	@Override
	public void majRecompensePersonne(Personne personne) {
		Niveau niveau = personneInterface.infosNiveauPersonne(personne).getNiveau();
		List<PrerequisRecompense> listePrerequisRecompense = prerequisRecompenseInterface.getPrerequisRecompenseByNiveau(niveau);
	}
}
