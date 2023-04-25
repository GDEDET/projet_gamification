package fr.neosoft.todogame.personnes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonneDto {
	private String nomUtilisateur;
	private int nbPoints;
	private int niveau;
}
