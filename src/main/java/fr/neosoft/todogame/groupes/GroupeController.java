package fr.neosoft.todogame.groupes;

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
@RequestMapping("groupes")
@Tag(name = "Groupe", description = "L'API des groupes")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class GroupeController {
    private final GroupeService groupeService;

    public GroupeController(GroupeService service) {
        this.groupeService = service;
    }

    @Operation(summary = "Afficher tous les groupes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "groupes trouvés",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Groupe.class))))
    })
    @GetMapping
    @Secured("ADMIN")
    public Iterable<Groupe> findAll() {
        return groupeService.findAll();
    }

    @Operation(summary = "Créer un groupe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Groupe créé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequestDto.class))})
    })
    @PostMapping
    @Secured("ADMIN")
    public Groupe save(@RequestBody Groupe groupe) {
        return groupeService.save(groupe);
    }

    @Operation(summary = "Met à jour un groupe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Groupe mis à jour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Groupe.class))})
    })
    @PutMapping
    public Groupe update(@RequestBody Groupe groupe) {
        return groupeService.update(groupe);
    }

    @Operation(summary = "Trouver un groupe via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Groupe trouvé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Groupe.class))}),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Groupe non trouvé")
    })
    @GetMapping("{idGroupe}")
    @Secured("ADMIN")
    public Groupe findById(@Parameter(description = "Id du groupe à afficher") @PathVariable Long idGroupe) {
        return groupeService.findById(idGroupe);
    }

    @Operation(summary = "Supprimer un groupe via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Groupe supprimé"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Groupe non trouvé")
    })
    @DeleteMapping("{idGroupe}")
    public void deleteById(@Parameter(description = "Id du groupe à supprimer") @PathVariable Long idGroupe) {
        groupeService.deleteById(idGroupe);
    }

    @Operation(summary = "Ajouter un membre à un groupe avec l'id du groupe et l'id de la personne")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Membre ajouté au groupe",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Groupe.class))}),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Groupe ou Personne non trouvé")
    })
    @PutMapping("/{idGroupe}/ajouter-membre/{idPersonne}")
    public Groupe addMembre(@PathVariable Long idGroupe, @PathVariable Long idPersonne) {
        return groupeService.addMembre(idGroupe, idPersonne);
    }

    @Operation(summary = "Supprimer un membre d'un groupe avec l'id du groupe et l'id de la personne")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Membre supprimé du groupe"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Groupe ou Personne non trouvé")
    })
    @PutMapping("/{idGroupe}/supprimer-membre/{idPersonne}")
    public Groupe removeMembre(@PathVariable Long idGroupe, @PathVariable Long idPersonne) {
        return groupeService.removeMembre(idGroupe, idPersonne);
    }
}
