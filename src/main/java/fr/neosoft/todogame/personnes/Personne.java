package fr.neosoft.todogame.personnes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.defis_personnes.DefiPersonne;
import fr.neosoft.todogame.groupes.Groupe;
import fr.neosoft.todogame.taches.Tache;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="personne")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "personne_id", nullable = false)
    private Long id;

    @Column(name = "nom", nullable = false, length = 50)
    @NotBlank
    private String nom;

    @Column(name = "prenom", nullable = false, length = 50)
    @NotBlank
    private String prenom;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "nomUtilisateur", unique = true, nullable = false, length = 20)
    private String nomUtilisateur;

    @Column(name = "password", nullable = false)
    private String motDePasse;

    @Column(name = "nb_points", nullable = false)
    private int nbPoints = 0;

    @ManyToMany
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "groupe_id")
    @ToString.Exclude
    private Groupe groupe;

    @OneToMany
    @ToString.Exclude
    private List<Tache> taches = new ArrayList<>();

    @OneToMany(mappedBy = "personne")
    @ToString.Exclude
    @JsonIgnore
    private List<DefiPersonne> defisARealiser = new ArrayList<>();

    public Personne (
            String nom,
            String prenom,
            String email,
            String nomUtilisateur,
            String motDePasse,
            int nbPoints
    ){
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.nbPoints = nbPoints;
    }

}
