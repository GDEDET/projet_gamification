package fr.neosoft.todogame.niveaux;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="personne")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private int niveau;

    @Column(name = "nb_points", nullable = false)
    private int nbPointsRequis;

}
