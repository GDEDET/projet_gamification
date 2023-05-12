package fr.neosoft.todogame.recompenses;

import java.time.LocalDate;
import java.util.List;

import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompense;

public class RecompenseData {
	public static final Recompense RECOMPENSE_NOT_BADGE = getNewRecompense(1L, "Recompense non badge", false, 200, null);
	public static final Recompense RECOMPENSE_BADGE = getNewRecompense(2L, "Recompense badge", true, 100, null);

	public static final PrerequisRecompense PREREQUIS_RECOMPENSE = getPrerequisRecompense(1L, 0, 2, null);
	public static final PrerequisRecompense PREREQUIS_RECOMPENSE_ECHEANCE = getPrerequisRecompense(2L, 3, 0, LocalDate.now());
	public static final Recompense RECOMPENSE_WITH_PREREQUIS = getNewRecompense(1L, "Recompense avec Prerequis", false, 100, PREREQUIS_RECOMPENSE);
	public static final Recompense RECOMPENSE_WITH_PREREQUIS_ECEHANCE = getNewRecompense(2L, "Recompense avec prerequis date echeance", false, 100, PREREQUIS_RECOMPENSE_ECHEANCE);

	public static Recompense getNewRecompense(
			Long id,
			String nom,
			Boolean estBadge,
			int pointGagne,
			PrerequisRecompense prerequisRecompense
	) {
		Recompense recompense = new Recompense();
		recompense.setId(id);
		recompense.setNomRecompense(nom);
		recompense.setEstBadge(estBadge);
		recompense.setPointGagne(pointGagne);
		if (prerequisRecompense!= null) {
			recompense.setPrerequisRecompenses(List.of(prerequisRecompense));
		}
		return recompense;
	}

	public static PrerequisRecompense getPrerequisRecompense(
		Long id,
		int nbPoints,
		int nbTaches,
		LocalDate dateEcheance
	) {
		PrerequisRecompense prerequisRecompense = new PrerequisRecompense();
		prerequisRecompense.setId(id);
		prerequisRecompense.setNbPoints(nbPoints);
		prerequisRecompense.setNbTaches(nbTaches);
		prerequisRecompense.setDateEcheance(dateEcheance);
		return prerequisRecompense;
	}
}
