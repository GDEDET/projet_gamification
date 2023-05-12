package fr.neosoft.todogame.defis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.neosoft.todogame.defis_personnes.DefiPersonne;
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
    @Column(name = "defi_id", nullable = false)
    private Long id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "description", nullable = false, length = 50)
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
    @JsonIgnore
    private List<DefiPersonne> defiPersonnes;
}
