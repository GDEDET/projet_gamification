package fr.neosoft.todogame.defis;

import fr.neosoft.todogame.TestUtils;
import fr.neosoft.todogame.auth.JwtTokenUtil;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class DefiControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DefiRepository defiRepository;

    @Autowired
    private JwtTokenUtil tokenUtil;

    private MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @DisplayName("Test de Initialisation Contexte")
    public void givenWac_whenServletContext_thenItProvidesDefiPersonneController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("defiController"));
    }

    @Test
    @Order(1)
    @DisplayName("Test de sécurité - accès interdit")
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(
                get("/defis"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    @DisplayName("Trouver toutes les défis de l'application")
    void findAll() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(
                get("/defis").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse());
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @Order(3)
    @DisplayName("Trouver tous les défis de l'utilisateur connecté")
    void findAllByUser() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(
                get("/defis/voir-mes-defis").header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse());
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @Order(4)
    @DisplayName("Creer un défi")
    void creerDefi() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());

        MvcResult mvcResult = this.mockMvc.perform(
                post("/defis").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(DefiData.DEFI_1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Description du défi 1"))
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @Order(5)
    @DisplayName("Trouver un défi par son id")
    void findById() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(
                get("/defis/{idDefi}", 1)
                .accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.description").isString())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @Order(6)
    @DisplayName("Ajouter un défi")
    void ajouterDefi() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(
                post("/defis/{id}/ajouter-defi", DefiData.DEFI_1.getId())
                .accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @Order(7)
    @DisplayName("Supprimer un défi via son id")
    void deleteById() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/defis/{id}", DefiData.DEFI_1.getId())
                        .accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
        assertFalse(defiRepository.findById(1L).isPresent());
    }
}
