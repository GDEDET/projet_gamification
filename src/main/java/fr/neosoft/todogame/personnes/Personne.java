package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.roles.Role;
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
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nom", nullable = false, length = 50)
    @NotBlank
    private String nom;
    @Column(name = "prenom", nullable = false, length = 50)
    @NotBlank
    private String prenom;

    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

}
