package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.defis.defis_personnes.DefiPersonne;
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
public class Defi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nom;

    private String description;

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

    @OneToMany(mappedBy = "defi")
    private List<DefiPersonne> defiPersonnes;
}
