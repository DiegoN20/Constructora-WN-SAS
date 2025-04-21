package constructora.constructorabackend.unit;

import constructora.constructorabackend.seguridad.dto.AuthRequestDto;
import constructora.constructorabackend.seguridad.dto.AuthResponseDto;
import constructora.constructorabackend.seguridad.model.UserModel;
import constructora.constructorabackend.seguridad.repository.UserRepository;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtilService jwtUtilService;

    @Mock
    private UserRepository userRepository;

    private UserModel user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new UserModel();
        user.setId_usuarios(1);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setCorreo("juan.perez@example.com");
        user.setContrasena("password123");
        user.setRol("Contratista");
    }

    @Test
    void login_ValidCredentials() throws Exception {
        AuthRequestDto authRequest = new AuthRequestDto();
        authRequest.setUser("juan.perez@example.com");
        authRequest.setPassword("password123");

        when(userRepository.findByCorreo("juan.perez@example.com")).thenReturn(user);
        when(jwtUtilService.generateToken(any(UserDetails.class), any(String.class))).thenReturn("mockToken");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType("application/json")
                        .content("{ \"user\": \"juan.perez@example.com\", \"password\": \"password123\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void login_InvalidCredentials() throws Exception {
        AuthRequestDto authRequest = new AuthRequestDto();
        authRequest.setUser("juan.perez@example.com");
        authRequest.setPassword("wrongPassword");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType("application/json")
                        .content("{ \"user\": \"juan.perez@example.com\", \"password\": \"wrongPassword\" }"))
                .andExpect(status().isUnauthorized());
    }
}