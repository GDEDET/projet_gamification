package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.personnes.Personne;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("taches")
@Tag(name = "Tâches", description = "L'API des tâches")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class TacheController {

    private final GestionTacheInterface gestionTacheInterface;

    private final GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface;

    public TacheController(GestionTacheInterface gestionTacheInterface, GestionPersonneAuthentifieInterface gestionPersonneAuthentifieInterface) {
        this.gestionTacheInterface = gestionTacheInterface;
        this.gestionPersonneAuthentifieInterface = gestionPersonneAuthentifieInterface;
    }

    @Operation(summary = "Afficher toutes les tâches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tâches trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Tache.class))))
    })
    @GetMapping
    @Secured("ADMIN")
    public Iterable<Tache> findAll(Authentication authentication) {
        return gestionTacheInterface.findAll();
    }

    @Operation(summary = "Afficher toutes les tâches d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tâches trouvées",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = Tache.class))))
    })
    @GetMapping("{idUser}")
    public Iterable<Tache> findAllByUser(@Parameter(description = "Id de l'utilisateur") @PathVariable Long idUser) {
        return gestionTacheInterface.findAllByUser(idUser);
    }

    @Operation(summary = "Créer une tâche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tâche créée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tache.class))})
    })
    @PostMapping()
    public Tache creerTache(@RequestBody TacheDto tacheDto) {
        return gestionTacheInterface.creerTache(this.gestionPersonneAuthentifieInterface.getPersonneAuthentifie(), tacheDto);
    }

    @Operation(summary = "Met à jour une tâche")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tâche mise à jour",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Tache.class))})
    })
    @PutMapping
    public Tache update(@RequestBody Tache tache) {
        return gestionTacheInterface.update(tache);
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
        return gestionTacheInterface.findById(id);
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
        gestionTacheInterface.deleteById(id);
    }
}
