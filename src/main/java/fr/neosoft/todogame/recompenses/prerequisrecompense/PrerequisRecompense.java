package fr.neosoft.todogame.recompenses.prerequisrecompense;

import fr.neosoft.todogame.recompenses.Recompense;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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
	private LocalDate dateEcheance;

	@ManyToOne
	@JoinColumn(name = "recompense_id")
	private Recompense recompense = null;
}
