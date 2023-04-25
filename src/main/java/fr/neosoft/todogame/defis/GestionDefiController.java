package fr.neosoft.todogame.defis;

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
@RequestMapping("Defi")
@Tag(name = "Défis", description = "L'API des défis")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class GestionDefiController {
    private final GestionDefiInterface gestionDefiInterface;

    public GestionDefiController(GestionDefiInterface gestionDefiInterface) {
        this.gestionDefiInterface = gestionDefiInterface;
    }


    @Operation(summary = "Afficher toutes les défis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Défis trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Defi.class))))
    })
    @GetMapping
    @Secured("ADMIN")
    public Iterable<Defi> findAll() {
        return gestionDefiInterface.findAll();
    }

    @Operation(summary = "Afficher toutes les tâches d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Défis trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Defi.class))))
    })
    @GetMapping("{idUser}")
    public Iterable<Defi> findAllByUser(@Parameter(description = "Id de l'utilisateur") @PathVariable Long idUser) {
        return gestionDefiInterface.findAllByUser(idUser);
    }

    @Operation(summary = "Créer une défi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "défi créée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Defi.class))})
    })
    @PostMapping("{idUser}")
    public Defi creerDefi(@RequestBody Defi defi) {
        return gestionDefiInterface.save(defi);
    }

    @Operation(summary = "Met à jour une défi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "défi mise à jour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Defi.class))})
    })
    @PutMapping
    public Defi update(@RequestBody Defi defi) {
        return gestionDefiInterface.update(defi);
    }

    @Operation(summary = "Trouver une défi via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "défi trouvée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Defi.class))}),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "défi non trouvée")
    })
    @GetMapping("{id}")
    public Defi findById(@Parameter(description = "Id de la défi à afficher") @PathVariable Long id) {
        return gestionDefiInterface.findById(id);
    }

    @Operation(summary = "Supprimer une défi via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "défi supprimée"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "défi non trouvée")
    })
    @DeleteMapping("{id}")
    public void deleteById(@Parameter(description = "Id de la défi à supprimer") @PathVariable Long id) {
        gestionDefiInterface.deleteById(id);
    }
}
