package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.personnes.Personne;

import java.util.List;

public interface RecompensePersonneInterface {

	/**
	 * Retourne la liste des badges de la personne authentifiée.
	 * @param personne
	 * @return Liste des badges de la personne
	 */
	List<Recompense> getListeRecompensesPersonne(Personne personne, boolean estBadge);

	void majRecompensePersonne(Personne personne);
}
