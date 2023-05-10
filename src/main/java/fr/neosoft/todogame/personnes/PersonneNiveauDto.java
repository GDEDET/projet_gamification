package fr.neosoft.todogame.personnes;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonneNiveauDto {
	private String nomUtilisateur;
	private int nbPoints;
	private int niveau;
}
