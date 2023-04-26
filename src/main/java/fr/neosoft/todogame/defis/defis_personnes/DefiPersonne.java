package fr.neosoft.todogame.defis.defis_personnes;

import fr.neosoft.todogame.defis.Defi;
import fr.neosoft.todogame.personnes.Personne;
import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(PKDefiPersonne.class)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DefiPersonne {
    @ManyToOne
    @JoinColumn(name = "personne_id")
    @Id
    private Personne personne;

    @ManyToOne
    @JoinColumn(name = "defi_id")
    @Id
    private Defi defi;

    @Column(name = "nb_taches_terminees", nullable = false)
    private int nbTachesTerminees = 0;

    @Column(name = "nb_points_gagnes", nullable = false)
    private int nbPointsGagnes = 0;

    public DefiPersonne(Personne personne, Defi defi) {
        this.personne = personne;
        this.defi = defi;
    }
}
