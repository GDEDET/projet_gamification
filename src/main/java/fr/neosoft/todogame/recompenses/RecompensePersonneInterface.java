package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.personnes.Personne;

import java.util.List;

public interface RecompensePersonneInterface {

	/**
	 * Retourne la liste des récompenses de la personne authentifiée par type (badge ou récompense classique).
	 * @param personne
	 * @return Liste des récompenses de la personne 
	 */
	List<Recompense> getListeRecompensesPersonne(Personne personne, boolean estBadge);

}
