package fr.neosoft.todogame.recompenses;

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
@RequestMapping("recompenses")
@Tag(name = "Recompense", description = "L'API des récompenses")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("PERSONNE")
public class RecompenseController {

	private final RecompenseService recompenseService;

	public RecompenseController(RecompenseService recompenseService) {
		this.recompenseService = recompenseService;
	}

	@Operation(summary = "Afficher toutes les récompenses")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Récompenses trouvées",
					content = @Content( array = @ArraySchema(schema = @Schema(implementation = Recompense.class))))
	})
	@GetMapping
	public Iterable<Recompense> findAll() {
		return recompenseService.findAll();
	}

	@Operation(summary = "Créer une récompense")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Récompense créée",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = Recompense.class))})
	})
	@PostMapping
	@Secured("ADMIN")
	public Recompense save(@RequestBody Recompense recompense) {
		return recompenseService.save(recompense);
	}

	@Operation(summary = "Met à jour une récompense")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Récompense mise à jour",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = Recompense.class))})
	})
	@PutMapping
	@Secured("ADMIN")
	public Recompense modifier(@RequestBody Recompense recompense) {
		return recompenseService.update(recompense);
	}

	@Operation(summary = "Trouver une récompense via son Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Récompense trouvée",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = Recompense.class))}),
			@ApiResponse(responseCode = "400", description = "Id fourni invalide",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Récompense non trouvée")
	})
	@GetMapping("{idRecompense}")
	public Recompense findById(@Parameter(description = "Id de la récompense à afficher") @PathVariable Long idRecompense) {
		return recompenseService.findById(idRecompense);
	}

	@Operation(summary = "Supprimer une récompense via son Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Récompense supprimée"),
			@ApiResponse(responseCode = "400", description = "Id fourni invalide",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Récompense non trouvée")
	})
	@DeleteMapping("{idRecompense}")
	@Secured("ADMIN")
	public void deleteById(@Parameter(description = "Id de la récompense à supprimer") @PathVariable Long idRecompense) {
		recompenseService.deleteById(idRecompense);
	}
}
