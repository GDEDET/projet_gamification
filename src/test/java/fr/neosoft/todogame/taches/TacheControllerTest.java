package fr.neosoft.todogame.taches;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.neosoft.todogame.auth.JwtTokenUtil;
import fr.neosoft.todogame.auth.roles.RoleRepository;
import fr.neosoft.todogame.personnes.Personne;
import fr.neosoft.todogame.personnes.PersonneRepository;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
        System.out.println(mvcResult.getResponse());
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
        System.out.println(mvcResult.getResponse());
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());

    }

    @Test
    @DisplayName("Creer une tache")
    void creerTache() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        TacheDto tacheDto = new TacheDto("Test", null, Priorite.MOYENNE, Difficulte.FACILE);

        MvcResult mvcResult = this.mockMvc.perform(post("/taches").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(tacheDto)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test"))
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    void update() throws Exception {
//        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
//        Tache tache = this.tacheRepository.findAllByDescription("Test").get(0);
//        tache.setDescription("Test modifie");
//        System.out.println(tache.toString());
//
//        MvcResult mvcResult = this.mockMvc.perform(put("/taches").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(tache)))
//                .andDo(print()).andExpect(status().isCreated())
//                .andExpect(jsonPath("$.description").value("Test modifie"))
//                .andReturn();
//        assertEquals("application/json",
//                mvcResult.getResponse().getContentType());
    }

    @Test
    void findById() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/taches/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").isString())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    void deleteById() {
    }

    @Test
    void terminerTache() {
    }
}