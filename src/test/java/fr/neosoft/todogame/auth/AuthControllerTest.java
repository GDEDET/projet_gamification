package fr.neosoft.todogame.auth;

import fr.neosoft.todogame.TestUtils;
import fr.neosoft.todogame.auth.dto.RegisterRequestDto;
import fr.neosoft.todogame.auth.dto.RequestLoginDto;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @BeforeEach
    public void setupMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @DisplayName("Enregistrement d'un nouvel utilisateur")
    void enregistrer() throws Exception {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("test", "test", "test@yopmail.com", "test", "test");
        MvcResult mvcResult = this.mockMvc.perform(post("/auth/enregistrer").contentType(MediaType.APPLICATION_JSON).content(TestUtils.asJsonString(registerRequestDto)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@yopmail.com"))
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    @DisplayName("Connexion avec utilisateur admin")
    void connexion() throws Exception {
        RequestLoginDto requestLoginDto = new RequestLoginDto("admin@yopmail.com", "admin");
        MvcResult mvcResult = this.mockMvc.perform(post("/auth/connexion").contentType(MediaType.APPLICATION_JSON).content(TestUtils.asJsonString(requestLoginDto)))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
        assertEquals("text/plain;charset=UTF-8", mvcResult.getResponse().getContentType());
    }

    @Test
    @DisplayName("Connexion avec utilisateur inconnu")
    void connexionFailed() throws Exception {
        RequestLoginDto requestLoginDto = new RequestLoginDto("anonymous@yopmail.com", "admin");
        MvcResult mvcResult = this.mockMvc.perform(post("/auth/connexion").contentType(MediaType.APPLICATION_JSON).content(TestUtils.asJsonString(requestLoginDto)))
                .andDo(print()).andExpect(status().isUnauthorized())
                .andReturn();
    }
}