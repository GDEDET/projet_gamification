package fr.neosoft.todogame.recompenses.prerequisrecompense;

import fr.neosoft.todogame.niveaux.Niveau;

import java.util.List;

public interface PrerequisRecompenseInterface {
	/**
	 * Rechercher les prerequis de récompenses ayant correspondant au niveau de la personne
	 */
	List<PrerequisRecompense> getPrerequisRecompenseByNiveau(Niveau niveau);
}
