package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.ServicioExternoId;
import constructora.constructorabackend.model.ServicioExternoModel;
import constructora.constructorabackend.repository.IServicioExternoRepository;
import constructora.constructorabackend.service.ServicioExternoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioExternoServiceTest {

    @Mock
    private IServicioExternoRepository servicioExternoRepository;

    @InjectMocks
    private ServicioExternoService servicioExternoService;

    private ServicioExternoModel servicio;
    private ServicioExternoId servicioId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        servicioId = new ServicioExternoId();
        servicioId.setProveedoresIdProveedores(1);
        servicioId.setProyectosIdProyectos(2);

        servicio = new ServicioExternoModel();
        servicio.setId(servicioId);
        servicio.setDescripcionServicio("Descripción de prueba");
        servicio.setCosto(5000);
        servicio.setFechaInicio(LocalDate.of(2023, 1, 1));
        servicio.setFechaFin(LocalDate.of(2023, 12, 31));
        servicio.setPersonaEncargada("Persona Encargada");
        servicio.setTelefono(123456789);
    }

    @Test
    void saveServicioExterno_ValidData() {
        when(servicioExternoRepository.save(servicio)).thenReturn(servicio);

        ServicioExternoModel savedServicio = servicioExternoService.saveServicioExterno(servicio);

        assertNotNull(savedServicio);
        assertEquals(servicio.getId(), savedServicio.getId());
        verify(servicioExternoRepository, times(1)).save(servicio);
    }

    @Test
    void getServicioExternoById_ValidId() {
        when(servicioExternoRepository.findById(servicioId)).thenReturn(Optional.of(servicio));

        Optional<ServicioExternoModel> result = servicioExternoService.getServicioExternoById(1, 2);

        assertTrue(result.isPresent());
        assertEquals(servicio, result.get());
        verify(servicioExternoRepository, times(1)).findById(servicioId);
    }

    @Test
    void getServicioExternoById_NotFound() {
        when(servicioExternoRepository.findById(servicioId)).thenReturn(Optional.empty());

        Optional<ServicioExternoModel> result = servicioExternoService.getServicioExternoById(1, 2);

        assertFalse(result.isPresent());
        verify(servicioExternoRepository, times(1)).findById(servicioId);
    }

    @Test
    void deleteServicioExterno_ValidId() {
        doNothing().when(servicioExternoRepository).deleteById(servicioId);

        servicioExternoService.deleteServicioExterno(1, 2);

        verify(servicioExternoRepository, times(1)).deleteById(servicioId);
    }

    @Test
    void getServicioExternoByProyecto() {
        when(servicioExternoRepository.findByProyectoId(2)).thenReturn(Arrays.asList(servicio));

        List<ServicioExternoModel> servicios = servicioExternoService.getServicioExternoByProyecto(2);

        assertEquals(1, servicios.size());
        assertEquals(servicio, servicios.get(0));
        verify(servicioExternoRepository, times(1)).findByProyectoId(2);
    }

    @Test
    void getServicioExternoByProveedor() {
        when(servicioExternoRepository.findByProveedorId(1)).thenReturn(Arrays.asList(servicio));

        List<ServicioExternoModel> servicios = servicioExternoService.getServicioExternoByProveedor(1);

        assertEquals(1, servicios.size());
        assertEquals(servicio, servicios.get(0));
        verify(servicioExternoRepository, times(1)).findByProveedorId(1);
    }
}