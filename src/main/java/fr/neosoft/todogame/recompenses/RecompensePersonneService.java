package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecompensePersonneService implements RecompensePersonneInterface {

	@Autowired
	RecompenseRepository recompenseRepository;
	PrerequisRecompenseRepository prerequisRecompenseRepository;

	@Override
	public List<Recompense> getListeBadgePersonneAuthentifie(Personne personne) {
		List<Recompense> badges = personne.getRecompenses().stream().filter(recompense -> recompense.getEstBadge().equals(true)).collect(Collectors.toList());;

		return badges;
	}
}
