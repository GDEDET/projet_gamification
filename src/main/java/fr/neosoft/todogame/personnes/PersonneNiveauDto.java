package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.niveaux.Niveau;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonneNiveauDto {
	private String nomUtilisateur;
	private int nbPoints;
	private Niveau niveau;
}
