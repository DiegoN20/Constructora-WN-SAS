package constructora.constructorabackend.unit;

import constructora.constructorabackend.seguridad.model.UserModel;
import constructora.constructorabackend.seguridad.repository.IUserRepository;
import constructora.constructorabackend.seguridad.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserDetailsServiceImplTest {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private UserModel user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un usuario para pruebas
        user = new UserModel();
        user.setId_usuarios(1);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setCorreo("juan.perez@example.com");
        user.setContrasena("password123");
        user.setRol("CONTRATISTA");
    }

    @Test
    void loadUserByUsername_ValidUser() {
        when(userRepository.findByCorreo("juan.perez@example.com")).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("juan.perez@example.com");

        assertNotNull(userDetails);
        assertEquals("juan.perez@example.com", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        verify(userRepository, times(1)).findByCorreo("juan.perez@example.com");
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        when(userRepository.findByCorreo("nonexistent@example.com")).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent@example.com");
        });

        String expectedMessage = "nonexistent@example.com";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository, times(1)).findByCorreo("nonexistent@example.com");
    }
}
