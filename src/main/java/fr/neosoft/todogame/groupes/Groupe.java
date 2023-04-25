package fr.neosoft.todogame.groupes;

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
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Personne chef;

    @OneToMany(mappedBy = "groupe")
    @ToString.Exclude
    private ArrayList<Personne> membres = new ArrayList<>();

    public void addMembre(Personne personne) {
        membres.add(personne);
    }

    public void removeMembre(Personne personne) {
        membres.remove(personne);
    }
}
