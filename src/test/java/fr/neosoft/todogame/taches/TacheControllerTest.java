package fr.neosoft.todogame.taches;

import fr.neosoft.todogame.auth.JwtTokenUtil;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TacheControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JwtTokenUtil tokenUtil;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(springSecurity()).build();
    }



    @Test
    public void givenWac_whenServletContext_thenItProvidesTacheController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("tacheController"));
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/taches"))
                .andExpect(status().isForbidden());
    }

    @Test
    void findAll() throws Exception {
        String token = tokenUtil.generateToken("admin@yopmail.com", Map.of());
        MvcResult mvcResult = this.mockMvc.perform(get("/taches").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Hello World!!!"))
                .andReturn();

        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    void findAllByUser() {
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