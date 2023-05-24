package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.utils.CRUDInterface;

import java.util.ArrayList;
import java.util.List;

public interface PersonneInterface extends CRUDInterface<Personne> {

	/**
	 * Permet de créer une personne en lui affectant le role personne par défaut et en encodant son mot de passe
	 * @param personneDto : les infos de la personne à creer
	 * @return la personne créée
	 */
	Personne creerPersonne(RegisterRequestDto personneDto);

	/**
	 * Retourne le nom d'utilisateur, le nb de points et le niveau correspondant à la personne.
	 * @param personne personne authentifié.
	 * @return le nom d'utilisateur, le nb de points et le niveau correspondant.
	 */
	PersonneNiveauDto infosNiveauPersonne(Personne personne);

	/**
	 * Permet d'ajouter des points à une personne
	 * @param personne personne ciblé
	 * @param nbPoints nombre de points à ajouter
	 */
	void incrementerNbPoint(Personne personne, int nbPoints);

	/**
	 * Méthode qui permet de trouver une personne à partir de son nomUtilisateur
	 * @param nomUtilisateur : le nom d'utilisateur de la personne
	 * @return la personne recherchée
	 */
	Personne findByNomUtilisateur(String nomUtilisateur);

	/**
	 * Permet de récupérer le classement des personnes par leur nombre de points décroissant
	 * @return la liste des personnes avec leur nom d'utilisateur et nombre de points (PersonneDTO)
	 */
	Iterable<Personne> getClassementParPoints();

	/**
	 * Permet de récupérer le classement des personnes par niveau
	 * Récupère la liste des personnes par points décroissants
	 * Pour chaque personne, on récupère son niveau
	 *
	 * @return la liste des personnes avec leur nom d'utilisateur, nombre de points et niveau (PersonneDTO)
	 */
	ArrayList<PersonneNiveauDto> getClassementParNiveaux();

	/**
	 * Permet de récupérer le classement des personnes par nombre de tâches réalisées (statut : TERMINE)
	 *
	 * @return La liste des personnes par nombre décroissant de tâches terminées
	 */
	List<Personne> getClassementParRealisations();

	/**
	 * Renvoie le nombre de tâche terminées de la personne passée en paramètre
	 * @param personne : personne dont on veut connaitre le nombre de tâche terminées
	 * @return le nombre de tâche terminées
	 */
	int nbTachesTermine(Personne personne);
}
