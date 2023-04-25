package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.utils.CRUDInterface;

public interface PersonneInterface extends CRUDInterface<Personne> {

	/**
	 * Permet de créer une personne en lui affectant le role personne par défaut et en encodant son mot de passe
	 *
	 * @param personneDto : les infos de la personne à creer
	 * @return la personne créée
	 */
	Personne creerPersonne(RegisterRequestDto personneDto);

	/**
	 * Retourne le nom d'utilisateur, le nb de points et le niveau correspondant à l'idPersonne.
	 * @param idPersonne de la personne cible.
	 * @return le nom d'utilisateur, le nb de points et le niveau correspondant.
	 */
	PersonneNiveauDto infosNiveauPersonne(Long idPersonne);
}
