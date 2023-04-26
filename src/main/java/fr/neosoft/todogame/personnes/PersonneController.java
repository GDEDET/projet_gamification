package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("personnes")
@Tag(name = "Personne", description = "L'API des personnes")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class PersonneController {

    private final PersonneInterface personneInterface;

    public PersonneController(
            PersonneInterface personneInterface
    ) {
        this.personneInterface = personneInterface;
    }

    @Operation(summary = "Afficher toutes les personnes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnes trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Personne.class))))
    })
    @GetMapping
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
    @GetMapping("{id}")
    public Personne findById(@Parameter(description = "Id de la personne à afficher") @PathVariable Long id) {
        return personneInterface.findById(id);
    }

    @Operation(summary = "Supprimer une personne via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Personne supprimée"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Personne non trouvée")
    })
    @DeleteMapping("{id}")
    public void deleteById(@Parameter(description = "Id de la personne à supprimer") @PathVariable Long id) {
        personneInterface.deleteById(id);
    }

    @Operation(summary = "Retourner les informations sur le niveau de la personne via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personne trouvée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personne.class))}),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Personne non trouvée")
    })
    @GetMapping("{id}/niveau")
    public PersonneNiveauDto getInfosNiveauPersonne(@Parameter(description = "Id de la personne à afficher") @PathVariable Long id) {
        return personneInterface.infosNiveauPersonne(id);
    }
}
