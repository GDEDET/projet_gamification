package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.defis.defis_personnes.DefiPersonne;
import fr.neosoft.todogame.defis.defis_personnes.GestionDefiPersonneInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Defi")
@Tag(name = "Défis", description = "L'API des défis")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class GestionDefiController {
    private final GestionDefiInterface gestionDefiInterface;

    @Autowired
    private final GestionDefiPersonneInterface gestionDefiPersonneInterface;

    public GestionDefiController(GestionDefiPersonneInterface gestionDefiPersonneInterface, GestionDefiInterface gestionDefiInterface) {
        this.gestionDefiPersonneInterface = gestionDefiPersonneInterface;
        this.gestionDefiInterface = gestionDefiInterface;
    }


    @Operation(summary = "Afficher tous les défis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Défis trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Defi.class))))
    })
    @GetMapping
    public Iterable<Defi> findAll() {
        return gestionDefiInterface.findAll();
    }

    @Operation(summary = "Afficher tous les défis de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Défis trouvés",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Defi.class))))
    })
    @GetMapping("/voir-mes-defis")
    public List<DefiPersonne> findAllByPersonneConnecte() {
        return gestionDefiPersonneInterface.findAllByPersonneConnecte();
    }

    @Operation(summary = "Créer un défi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "défi créé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Defi.class))})
    })
    @PostMapping()
    @Secured("ADMIN")
    public Defi creerDefi(@RequestBody Defi defi) {
        return gestionDefiInterface.save(defi);
    }

    @Operation(summary = "Met à jour un défi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "défi mis à jour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Defi.class))})
    })
    @PutMapping
    @Secured("ADMIN")
    public Defi update(@RequestBody Defi defi) {
        return gestionDefiInterface.update(defi);
    }

    @Operation(summary = "Trouver un défi via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "défi trouvé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Defi.class))}),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "défi non trouvé")
    })
    @GetMapping("{id}")
    public Defi findById(@Parameter(description = "Id du défi à afficher") @PathVariable Long id) {
        return gestionDefiInterface.findById(id);
    }

    @Operation(summary = "Supprimer un défi via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "défi supprimé"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "défi non trouvé")
    })
    @DeleteMapping("{id}")
    @Secured("ADMIN")
    public void deleteById(@Parameter(description = "Id du défi à supprimer") @PathVariable Long id) {
        gestionDefiInterface.deleteById(id);
    }

    @Operation(summary="Un utilisateur s'ajoute un défi à sa liste de défis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "défi ajouté"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "défi introuvable")
    })
    public List<DefiPersonne> ajouterDefi(@PathVariable Long id)
    {
        return gestionDefiPersonneInterface.ajouterDefi(id);
    }
}
