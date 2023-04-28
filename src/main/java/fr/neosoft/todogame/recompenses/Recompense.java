package fr.neosoft.todogame.recompenses;

import fr.neosoft.todogame.recompenses.prerequisrecompense.PrerequisRecompense;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
    private Boolean estBadge;

    @Column(name = "point_gagne", nullable = false)
    private int pointGagne;

    @ManyToOne
    @JoinColumn(name = "prerequis_recompense_id")
    private PrerequisRecompense prerequisRecompense = null;

    public Recompense (String nomRecompense, Boolean estBadge, int pointGagne) {
        this.nomRecompense = nomRecompense;
        this.estBadge = estBadge;
        this.pointGagne = pointGagne;
    }

}
