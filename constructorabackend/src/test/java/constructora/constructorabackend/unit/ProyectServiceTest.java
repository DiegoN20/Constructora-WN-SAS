package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.seguridad.model.UserModel;
import constructora.constructorabackend.repository.IProyectRepository;
import constructora.constructorabackend.service.ProyectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProyectServiceTest {

    @Mock
    private IProyectRepository proyectRepository;

    @InjectMocks
    private ProyectService proyectService;

    private ProyectModel proyect1;
    private ProyectModel proyect2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UserModel user = new UserModel();
        user.setId_usuarios(1);

        proyect1 = new ProyectModel();
        proyect1.setIdProyectos(1);
        proyect1.setNombreProyecto("Proyecto A");
        proyect1.setUsuario(user);

        proyect2 = new ProyectModel();
        proyect2.setIdProyectos(2);
        proyect2.setNombreProyecto("Proyecto B");
        proyect2.setUsuario(user);
    }

    @Test
    void saveProyecto_ValidData() {
        when(proyectRepository.save(proyect1)).thenReturn(proyect1);

        ProyectModel savedProyect = proyectService.saveProyecto(proyect1);

        assertNotNull(savedProyect);
        assertEquals(proyect1.getIdProyectos(), savedProyect.getIdProyectos());
        verify(proyectRepository, times(1)).save(proyect1);
    }

    @Test
    void saveProyecto_InvalidUser() {
        ProyectModel invalidProyect = new ProyectModel();
        invalidProyect.setUsuario(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            proyectService.saveProyecto(invalidProyect);
        });

        String expectedMessage = "El usuario asociado no puede ser nulo.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void getProyectos() {
        when(proyectRepository.findAll()).thenReturn(Arrays.asList(proyect1, proyect2));

        List<ProyectModel> proyectos = proyectService.getProyectos();

        assertEquals(2, proyectos.size());
        verify(proyectRepository, times(1)).findAll();
    }

    @Test
    void getProyectoById_ValidId() {
        when(proyectRepository.findById(1)).thenReturn(Optional.of(proyect1));

        Optional<ProyectModel> proyecto = proyectService.getProyectoById(1);

        assertTrue(proyecto.isPresent());
        assertEquals(proyect1.getIdProyectos(), proyecto.get().getIdProyectos());
        verify(proyectRepository, times(1)).findById(1);
    }

    @Test
    void getProyectoById_NotFound() {
        when(proyectRepository.findById(99)).thenReturn(Optional.empty());

        Optional<ProyectModel> proyecto = proyectService.getProyectoById(99);

        assertTrue(proyecto.isEmpty());
        verify(proyectRepository, times(1)).findById(99);
    }

    @Test
    void deleteProyecto_ValidId() {
        doNothing().when(proyectRepository).deleteById(1);

        proyectService.deleteProyecto(1);

        verify(proyectRepository, times(1)).deleteById(1);
    }
}
