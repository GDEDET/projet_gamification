package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneService;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompense;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecompensePersonneService implements RecompensePersonneInterface {

	@Autowired
	RecompenseRepository recompenseRepository;
	PersonneService personneService;

	@Override
	public List<Recompense> getListeRecompensesPersonne(@NotNull Personne personne, @NotNull boolean estBadge) {
		List<Recompense> recompenses = personne.getRecompenses().stream().filter(
				recompense -> recompense.getEstBadge().equals(estBadge)
			).collect(Collectors.toList());

		return recompenses;
	}

	@Override
	public void majRecompensePersonne(Personne personne) {
		Niveau niveau = personneService.infosNiveauPersonne(personne).getNiveau();
		int nbTachesTermine = personneService.nbTachesTermine(personne);
		List<Recompense> listeRecompenses = recompenseRepository.findRecompenseByNiveau(niveau);

		for(Recompense recompense : listeRecompenses) {
			List<PrerequisRecompense> listePrerequisRecompense = recompense.getPrerequisRecompenses();
			boolean prerequisEstOk = false;

			for (PrerequisRecompense prerequisRecompense : listePrerequisRecompense) {
				boolean testPrerequisRecompenseHorsDate = (
						prerequisRecompense.getNbTaches() <= nbTachesTermine
								&& prerequisRecompense.getNbPoints() <= personne.getNbPoints()
				);
				boolean estNullPrerequisRecompenseDateEcheance = prerequisRecompense.getDateEcheance().equals(null);

				if ((estNullPrerequisRecompenseDateEcheance && testPrerequisRecompenseHorsDate) ||
						(!estNullPrerequisRecompenseDateEcheance && prerequisRecompense.getDateEcheance().isAfter(LocalDate.now())
								&& testPrerequisRecompenseHorsDate)
				) {
					prerequisEstOk = true;
				} else {
					prerequisEstOk = false;
					break;
				}
			}

			if (prerequisEstOk) {
				personne.setNbPoints(recompense.getPointGagne());
				personne.getRecompenses().add(recompense);
				personneService.save(personne);
			}
		}
	}
}
