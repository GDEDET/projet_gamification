package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.defis_personnes.DefiPersonne;
import fr.neosoft.todogame.defis_personnes.DefiPersonneInterface;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("defis")
@Tag(name = "Défis", description = "L'API des défis")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class DefiController {
    private final DefiInterface defiInterface;

    @Autowired
    private final DefiPersonneInterface defiPersonneInterface;

    public DefiController(DefiPersonneInterface defiPersonneInterface, DefiInterface defiInterface) {
        this.defiPersonneInterface = defiPersonneInterface;
        this.defiInterface = defiInterface;
    }


    @Operation(summary = "Afficher tous les défis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Défis trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Defi.class))))
    })
    @GetMapping
    public Iterable<Defi> findAll() {
        return defiInterface.findAll();
    }

    @Operation(summary = "Afficher tous les défis de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Défis trouvés",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Defi.class))))
    })
    @GetMapping("/voir-mes-defis")
    public List<DefiPersonne> findAllByPersonneConnecte() {
        return defiPersonneInterface.findAllByPersonneConnecte();
    }

    @Operation(summary = "Créer un défi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "défi créé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Defi.class))})
    })
    @PostMapping()
    @Secured("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public Defi creerDefi(@RequestBody Defi defi) {
        return defiInterface.save(defi);
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
        return defiInterface.update(defi);
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
    @GetMapping("{idDefi}")
    public Defi findById(@Parameter(description = "Id du défi à afficher") @PathVariable Long idDefi) {
        return defiInterface.findById(idDefi);
    }

    @Operation(summary = "Supprimer un défi via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "défi supprimé"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "défi non trouvé")
    })
    @DeleteMapping("{idDefi}")
    @Secured("ADMIN")
    public void deleteById(@Parameter(description = "Id du défi à supprimer") @PathVariable Long idDefi) {
        defiInterface.deleteById(idDefi);
    }

    @Operation(summary="Un utilisateur s'ajoute un défi à sa liste de défis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "défi ajouté"),
            @ApiResponse(responseCode = "400", description = "Id fourni invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "défi introuvable")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("{idDefi}/ajouter-defi")
    public List<DefiPersonne> ajouterDefi(@Parameter(description = "Id du défi à ajouter") @PathVariable Long idDefi)
    {
        return defiPersonneInterface.ajouterDefi(idDefi);
    }
}