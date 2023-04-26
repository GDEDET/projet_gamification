package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.defis.prerequisDefi.DefiPrerequis;
import fr.neosoft.todogame.personnes.Personne;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

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

    @Column(name = "defi_prerequis", nullable = false)
    private DefiPrerequis defiPrerequis;

    @Column(name = "nb_points_recompense", nullable = false)
    private int nbPointsRecompense;
}
