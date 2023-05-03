package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecompensePersonneService implements RecompensePersonneInterface {

	@Autowired
	RecompenseRepository recompenseRepository;
	PrerequisRecompenseRepository prerequisRecompenseRepository;

	@Override
	public List<Recompense> getListeRecompensesPersonne(Personne personne, boolean estBadge) {
		List<Recompense> badges = personne.getRecompenses().stream().filter(
				recompense -> recompense.getEstBadge().equals(estBadge)
			).collect(Collectors.toList()).orElseThrow(() -> new NotFoundException("Aucune valeur n'a été trouvé."));

		return badges;
	}
}
