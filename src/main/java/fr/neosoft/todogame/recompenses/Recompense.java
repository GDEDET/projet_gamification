package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.niveaux.Niveau;
import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompense;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Recompense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom_recompense", nullable = false, length = 50)
    @NotBlank
    private String nomRecompense;

    @Column(name = "est_badge", nullable = false)
    @NotNull
    private Boolean estBadge;

    @Column(name = "point_gagne", nullable = false)
    private int pointGagne;

    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;

    @OneToMany
    @ToString.Exclude
    private List<PrerequisRecompense> prerequisRecompenses = new ArrayList<>();

    public Recompense (String nomRecompense, Boolean estBadge, int pointGagne) {
        this.nomRecompense = nomRecompense;
        this.estBadge = estBadge;
        this.pointGagne = pointGagne;
    }

}
