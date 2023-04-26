package fr.neosoft.todogame.defis.prerequisDefi;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class DefiPrerequis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nb_points_objectif", nullable = true)
    private int nbPointsObjectif;

    @Column(name = "objectif_taches", nullable = true)
    private int nbTachesObjectif;

    @Column(name = "date_echeance", nullable = true)
    private LocalDate dateEcheance;

    @Column(name = "nb_taches_terminees", nullable = false)
    private int nbTachesTerminees = 0;

    @Column(name = "nb_points_gagnes", nullable = false)
    private int nbPointsGagnes = 0;
}
