package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.personnes.Personne;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Defi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nom;

    private String description;

    @ManyToOne
    @ToString.Exclude
    private Personne personneRealisantLeDefi;

    @Column(name = "type_defi", nullable = false)
    private TypeDefi typeDefi;

    @Column(name = "nb_points_recompense", nullable = false)
    private int nbPointsRecompense;

    @Column(name = "nb_points_objectif", nullable = false)
    private int nbPointsObjectif = 0;

    @Column(name = "nb_taches_objectif", nullable = false)
    private int nbTachesObjectif = 0;

    @Column(name = "date_echeance", nullable = true)
    private LocalDate dateEcheance;

    @Column(name = "nb_taches_terminees", nullable = false)
    private int nbTachesTerminees = 0;

    @Column(name = "nb_points_gagnes", nullable = false)
    private int nbPointsGagnes = 0;
}
