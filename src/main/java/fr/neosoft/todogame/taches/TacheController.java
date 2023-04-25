package fr.neosoft.todogame.taches;

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
@RequestMapping("taches")
@Tag(name = "Tâches", description = "L'API des tâches")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class TacheController {

    private final TacheService tacheService;

    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    @Operation(summary = "Afficher toutes les tâches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tâches trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Tache.class))))
    })
    @GetMapping
    public Iterable<Tache> findAll() {
        return tacheService.findAll();
    }

    @Operation(summary = "Créer une tâche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tâche créée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tache.class))})
    })
    @PostMapping
    public Tache save(@RequestBody Tache tache) {
        return tacheService.save(tache);
    }

    @Operation(summary = "Met à jour une tâche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tâche mise à jour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tache.class))})
    })
    @PutMapping
    public Tache update(@RequestBody Tache tache) {
        return tacheService.update(tache);
    }

    @Operation(summary = "Trouver une tâche via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tâche trouvée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tache.class))}),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    @GetMapping("{id}")
    public Tache findById(@Parameter(description = "Id de la tâche à afficher") @PathVariable Long id) {
        return tacheService.findById(id);
    }

    @Operation(summary = "Supprimer une tâche via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tâche supprimée"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    @DeleteMapping("{id}")
    public void deleteById(@Parameter(description = "Id de la tâche à supprimer") @PathVariable Long id) {
        tacheService.deleteById(id);
    }
}
