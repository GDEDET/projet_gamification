package fr.neosoft.todogame.recompenses.prerequisrecompense;

import fr.neosoft.todogame.recompenses.Recompense;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "prerequis_recompense")
public class PrerequisRecompense {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "nb_points")
	private int nbPoints;

	@Column(name = "nb_taches")
	private int nbTaches;

	@Column(name = "date_realisation")
	private LocalDate dateRealisation;
}
