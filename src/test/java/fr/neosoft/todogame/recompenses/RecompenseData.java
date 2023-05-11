package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompense;

import java.util.List;

public class RecompenseData {
	public static final Recompense RECOMPENSE_NOT_BADGE = getNewRecompense(1L, "Recompense non badge", false, 200);
	public static final Recompense RECOMPENSE_BADGE = getNewRecompense(1L, "Recompense badge", true, 100);

	public static Recompense getNewRecompense(
			Long id,
			String nom,
			Boolean estBadge,
			int pointGagne
	) {
		Recompense recompense = new Recompense();
		recompense.setId(id);
		recompense.setNomRecompense(nom);
		recompense.setEstBadge(estBadge);
		recompense.setPointGagne(pointGagne);
		return recompense;
	}
}
