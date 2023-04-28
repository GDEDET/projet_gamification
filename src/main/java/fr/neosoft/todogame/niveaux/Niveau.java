package fr.neosoft.todogame.niveaux;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="niveau")
@Getter
@NoArgsConstructor
public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "niveau_atteint", nullable = false)
    private int niveauAtteint;

    @Column(name = "nb_points", nullable = false)
    private int nbPointsRequis;

    public Niveau(int niveauAtteint, int nbPointsRequis) {
        this.niveauAtteint = niveauAtteint;
        this.nbPointsRequis = nbPointsRequis;
    }

}
