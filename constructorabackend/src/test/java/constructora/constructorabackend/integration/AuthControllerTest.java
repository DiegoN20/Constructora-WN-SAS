package constructora.constructorabackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import constructora.constructorabackend.seguridad.controller.AuthController;
import constructora.constructorabackend.seguridad.dto.AuthRequestDto;
import constructora.constructorabackend.seguridad.model.UserModel;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtilService jwtUtilService;

    @MockBean
    private constructora.constructorabackend.seguridad.repository.UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setId_usuarios(1); // Configura el ID correctamente
        userModel.setCorreo("testuser@example.com");
        userModel.setNombre("Test");
        userModel.setApellido("User");
        userModel.setRol("USER");
        userModel.setContrasena("password123");
    }

    @Test
    void login_ValidCredentials_ReturnsAuthResponseDto() throws Exception {
        AuthRequestDto requestDto = new AuthRequestDto();
        requestDto.setUser("testuser@example.com");
        requestDto.setPassword("password123");

        UserDetails userDetails = Mockito.mock(UserDetails.class);

        // Configuración correcta de los mocks
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userDetailsService.loadUserByUsername("testuser@example.com")).thenReturn(userDetails);
        when(userRepository.findByCorreo("testuser@example.com")).thenReturn(userModel); // Mock devuelve un objeto completo
        when(jwtUtilService.generateToken(any(), any())).thenReturn("jwtToken123");
        when(jwtUtilService.generateRefreshToken(any(), any())).thenReturn("refreshToken123");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwtToken123"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken123"))
                .andExpect(jsonPath("$.nombre").value("Test"))
                .andExpect(jsonPath("$.rol").value("USER"));

        // Verificación de los métodos llamados
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userDetailsService, times(1)).loadUserByUsername("testuser@example.com");
        verify(jwtUtilService, times(1)).generateToken(any(), any());
        verify(jwtUtilService, times(1)).generateRefreshToken(any(), any());
    }

    @Test
    void login_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        AuthRequestDto requestDto = new AuthRequestDto();
        requestDto.setUser("testuser@example.com");
        requestDto.setPassword("wrongpassword");

        doThrow(new RuntimeException("Bad credentials")).when(authenticationManager).authenticate(any());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Error Authetication:::Bad credentials"));

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(jwtUtilService, never()).generateToken(any(), any());
    }

    @Test
    void refresh_ValidRefreshToken_ReturnsNewTokens() throws Exception {
        Map<String, String> request = Map.of("refreshToken", "validRefreshToken123");

        UserDetails userDetails = Mockito.mock(UserDetails.class);

        when(jwtUtilService.extractUsername("validRefreshToken123")).thenReturn("testuser@example.com");
        when(userDetailsService.loadUserByUsername("testuser@example.com")).thenReturn(userDetails);
        when(userRepository.findByCorreo("testuser@example.com")).thenReturn(userModel);
        when(jwtUtilService.validateToken("validRefreshToken123", userDetails)).thenReturn(true);
        when(jwtUtilService.generateToken(any(), any())).thenReturn("newJwtToken");
        when(jwtUtilService.generateRefreshToken(any(), any())).thenReturn("newRefreshToken");

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("newJwtToken"))
                .andExpect(jsonPath("$.refreshToken").value("newRefreshToken"));

        verify(jwtUtilService, times(1)).extractUsername("validRefreshToken123");
        verify(jwtUtilService, times(1)).validateToken("validRefreshToken123", userDetails);
        verify(jwtUtilService, times(1)).generateToken(any(), any());
        verify(jwtUtilService, times(1)).generateRefreshToken(any(), any());
    }

    @Test
    void refresh_InvalidRefreshToken_ReturnsUnauthorized() throws Exception {
        Map<String, String> request = Map.of("refreshToken", "invalidRefreshToken");

        when(jwtUtilService.extractUsername("invalidRefreshToken"))
                .thenThrow(new IllegalArgumentException("Invalid token"));

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Error refresh token:::Invalid token"));

        verify(jwtUtilService, times(1)).extractUsername("invalidRefreshToken");
        verify(jwtUtilService, never()).validateToken(any(), any());
        verify(jwtUtilService, never()).generateToken(any(), any());
    }
}