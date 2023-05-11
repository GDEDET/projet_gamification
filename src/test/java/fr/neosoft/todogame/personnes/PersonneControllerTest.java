package fr.neosoft.todogame.personnes;

import fr.neosoft.todogame.TestUtils;
import fr.neosoft.todogame.auth.JwtTokenUtil;
import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class PersonneControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtTokenUtil tokenUtil;

    private MockMvc mockMvc;

    private String token;
    @Autowired
    private PersonneRepository personneRepository;

    @BeforeEach
    public void setupMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
        this.token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
    }

    @Test
    @DisplayName("Trouver toutes les personnes")
    void findAll() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/personnes").header("Authorization", "Bearer " + this.token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].nom").isString())
                .andReturn();

        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @DisplayName("Créer une personne")
    void save() throws Exception {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("test", "test", "test@yopmail.com", "test", "test");
        MvcResult mvcResult = this.mockMvc.perform(post("/personnes").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.token).content(TestUtils.asJsonString(registerRequestDto)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@yopmail.com"))
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    void modifier() {
    }

    @Test
    @DisplayName("Trouver une personne")
    void findById() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/personnes/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").isString())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @DisplayName("Supprimer une personne")
    void deleteById() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/personnes/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
        assertFalse(personneRepository.findById(2L).isPresent());
    }

    @Test
    void getInfosNiveauPersonne() {
    }

    @Test
    void getClassementParPoints() {
    }

    @Test
    void getClassementParNiveau() {
    }

    @Test
    void getClassementParRealisation() {
    }
}