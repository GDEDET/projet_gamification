package fr.neosoft.todogame.auth;

import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.auth.dto.RequestLoginDto;
import fr.neosoft.todogame.personnes.Personne;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@Tag(name = "Authentification", description = "L'API d'authentification")
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "S'enregistrer à l'application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Enregistrement effectué",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequestDto.class))})
    })
    @PostMapping("enregistrer")
    @ResponseStatus(HttpStatus.CREATED)
    public Personne enregistrer(@RequestBody RegisterRequestDto dto){
        return service.register(dto);
    }

    @Operation(summary = "Se connecter à l'application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Connexion effectuée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestLoginDto.class))})
    })
    @PostMapping("connexion")
    public String connexion(@RequestBody RequestLoginDto loginDto){
        try{
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getMotDePasse()
                    )
            );
        } catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return jwtTokenUtil.generateToken(loginDto.getEmail(), Map.of());
    }
}
