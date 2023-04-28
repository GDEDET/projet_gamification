package fr.neosoft.todogame.recompenses.prerequisrecompense;

import fr.neosoft.todogame.recompenses.Recompense;
import fr.neosoft.todogame.taches.Tache;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	// Correspond au jalon d'une tâche
	@Column(name = "jalon_respecte")
	private boolean jalonRespecte;

	@OneToMany
	@ToString.Exclude
	private List<Recompense> recompenses = new ArrayList<>();
}
