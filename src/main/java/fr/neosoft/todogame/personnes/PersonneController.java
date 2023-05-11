package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.utils.GestionPersonneAuthentifieInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("personnes")
@Tag(name = "Personne", description = "L'API des personnes")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class PersonneController {

    private final PersonneInterface personneInterface;
    private final GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;

    public PersonneController(
            PersonneInterface personneInterface,
            GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface
    ) {
        this.personneInterface = personneInterface;
        this.gestionPersonneAuthentifieInterface = gestionPersonneAuthentifieInterface;
    }

    @Operation(summary = "Afficher toutes les personnes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnes trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Personne.class))))
    })
    @GetMapping
    @Secured("ADMIN")
    public Iterable<Personne> findAll() {
        return personneInterface.findAll();
    }

    @Operation(summary = "Créer une personne")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Personne créée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequestDto.class))})
    })
    @PostMapping
    @Secured("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public Personne save(@RequestBody RegisterRequestDto entity) {
        return personneInterface.creerPersonne(entity);
    }

    @Operation(summary = "Met à jour une personne")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Personne mise à jour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personne.class))})
    })
    @PutMapping
    @Secured("ADMIN")
    public Personne modifier(@RequestBody Personne personne) {
        return personneInterface.update(personne);
    }

    @Operation(summary = "Trouver une personne via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personne trouvée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personne.class))}),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Personne non trouvée")
    })
    @GetMapping("{idPersonne}")
    @Secured("ADMIN")
    public Personne findById(@Parameter(description = "Id de la personne à afficher") @PathVariable Long idPersonne) {
        return personneInterface.findById(idPersonne);
    }

    @Operation(summary = "Supprimer une personne via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Personne supprimée"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Personne non trouvée")
    })
    @DeleteMapping("{idPersonne}")
    @Secured("ADMIN")
    public void deleteById(@Parameter(description = "Id de la personne à supprimer") @PathVariable Long idPersonne) {
        personneInterface.deleteById(idPersonne);
    }

    @Operation(summary = "Retourner les informations sur le niveau de la personne authentifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personne trouvée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonneNiveauDto.class))}),
            @ApiResponse(responseCode = "404", description = "Personne non trouvée")
    })
    @GetMapping("/niveau")
    public PersonneNiveauDto getInfosNiveauPersonne() {
        return personneInterface.infosNiveauPersonne(this.gestionPersonneAuthentifieInterface.getPersonneAuthentifie());
    }

    @Operation(summary = "Récupérer toutes les personnes triées par ordre décroissant de leur nombre de points")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnes trouvées et triées dans l'ordre décroissant de leur nombre de points",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Personne.class))))
    })
    @GetMapping("classement/points")
    public Iterable<Personne> getClassementParPoints() {
        return personneInterface.getClassementParPoints();
    }

    @Operation(summary = "Récupérer toutes les personnes triées par ordre décroissant de leur niveau")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnes trouvées et triées dans l'ordre décroissant de leur niveau",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Personne.class))))
    })
    @GetMapping("classement/niveaux")
    public List<PersonneNiveauDto> getClassementParNiveau() {
        return personneInterface.getClassementParNiveaux();
    }

    @Operation(summary = "Récupérer toutes les personnes triées par ordre décroissant de leur nombre de tâches réalisées")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnes trouvées et triées dans l'ordre décroissant de leur nombre de tâches réalisées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Personne.class))))
    })
    @GetMapping("classement/realisations")
    public List<Personne> getClassementParRealisation() {
        return personneInterface.getClassementParRealisations();
    }
}
