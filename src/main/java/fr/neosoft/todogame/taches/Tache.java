package fr.neosoft.todogame.taches;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.neosoft.todogame.personnes.Personne;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date_echeance", nullable = true)
    private Date dateEcheance;

    @Column(name = "date_realisation", nullable = true)
    private Date dateRealisation;

    @Column(name = "priorite", nullable = false)
    private Priorite priorite;

    @Column(name = "difficulte", nullable = false)
    private Difficulte difficulte;

    @Column(name = "statut", nullable = false)
    private Statut statut;

    @Column(name = "nb_points", nullable = false)
    private int nbPoints;

    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    @JsonIgnore
    private Personne personne;
}
