package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.auth.JwtTokenUtil;
import fr.neosoft.todogame.auth.roles.Role;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneRepository;
import jakarta.servlet.ServletContext;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TacheControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private PersonneRepository personneRepository;
    @Autowired
    private RoleRepository roleRepository;

    private MockMvc mockMvc;

    private List<Tache> tachesList;

    private Personne personne;

    private Tache tache;

    @BeforeEach
    public void setupMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @DisplayName("Test de Initialisation Contexte")
    public void givenWac_whenServletContext_thenItProvidesTacheController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("tacheController"));
    }

    @Test
    @DisplayName("Test de sécurité - accès interdit")
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/taches"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Trouver toutes les taches de l'application")
    void findAll() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(get("/taches").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").isString())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @DisplayName("Trouver toutes les taches de l'utilisateur connecté")
    void findAllByUser() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(get("/taches/voir-mes-taches").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").value("Tache 1"))
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());

    }

    @Test
    void creerTache() {
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void terminerTache() {
    }
}