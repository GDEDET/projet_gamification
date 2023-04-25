package fr.neosoft.todogame.recompenses.prerequisrecompense;

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
@RequestMapping("recompenses/prerequis")
@Tag(name = "PrerequisRecompense", description = "L'API des prérequis récompense")
@SecurityRequirement(name = "Bearer Authentication")
@Secured("ADMIN")
public class PrerequisRecompenseController {

	private final PrerequisRecompenseService prequisRecompenseService;

	public PrerequisRecompenseController(PrerequisRecompenseService prequisRecompenseService) {
		this.prequisRecompenseService = prequisRecompenseService;
	}

	@Operation(summary = "Afficher tous les prérequis récompense")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Prérequis récompense trouvées",
					content = @Content( array = @ArraySchema(schema = @Schema(implementation = PrerequisRecompense.class))))
	})
	@GetMapping
	public Iterable<PrerequisRecompense> findAll() {
		return prequisRecompenseService.findAll();
	}

	@Operation(summary = "Créer un prérequis récompense")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Prérequis récompense créée",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = PrerequisRecompense.class))})
	})
	@PostMapping
	public PrerequisRecompense save(@RequestBody PrerequisRecompense prerequisRecompense) {
		return prequisRecompenseService.save(prerequisRecompense);
	}

	@Operation(summary = "Met à jour un prérequis récompense")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Prérequis récompense mise à jour",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = PrerequisRecompense.class))})
	})
	@PutMapping
	public PrerequisRecompense modifier(@RequestBody PrerequisRecompense prerequisRecompense) {
		return prequisRecompenseService.update(prerequisRecompense);
	}

	@Operation(summary = "Trouver un prérequis récompense via son Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Prérequis récompense trouvée",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = PrerequisRecompense.class))}),
			@ApiResponse(responseCode = "400", description = "Id fourni invalide",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Prérequis récompense non trouvée")
	})
	@GetMapping("{id}")
	public PrerequisRecompense findById(@Parameter(description = "Id du prérequis récompense à afficher") @PathVariable Long id) {
		return prequisRecompenseService.findById(id);
	}

	@Operation(summary = "Supprimer une récompense via son Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Prérequis récompense supprimée"),
			@ApiResponse(responseCode = "400", description = "Id fourni invalide",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Prérequis récompense non trouvée")
	})
	@DeleteMapping("{id}")
	public void deleteById(@Parameter(description = "Id du prérequis récompense à supprimer") @PathVariable Long id) {
		prequisRecompenseService.deleteById(id);
	}
}
