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
    @NotBlank
    private Boolean estBadge;

    @Column(name = "point_gagne", nullable = false)
    @NotBlank
    private int pointGagne;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    private PrerequisRecompense prerequisRecompense;

}
