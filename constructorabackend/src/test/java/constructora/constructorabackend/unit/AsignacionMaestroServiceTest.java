package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.AsignacionMaestroId;
import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.repository.IAsignacionMaestroRepository;
import constructora.constructorabackend.service.AsignacionMaestroService;
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

class AsignacionMaestroServiceTest {

    @Mock
    private IAsignacionMaestroRepository asignacionMaestroRepository;

    @InjectMocks
    private AsignacionMaestroService asignacionMaestroService;

    private AsignacionMaestroModel asignacion;
    private AsignacionMaestroId asignacionId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        asignacionId = new AsignacionMaestroId();
        asignacionId.setIdMaestro(1);
        asignacionId.setIdProyecto(2);

        asignacion = new AsignacionMaestroModel();
        asignacion.setId(asignacionId);
        asignacion.setFechaAsignacion(LocalDate.of(2023, 1, 1));
        asignacion.setFechaFin(LocalDate.of(2023, 12, 31));
        asignacion.setEstadoAsignacion(AsignacionMaestroModel.EstadoAsignacion.Activo);
    }

    @Test
    void saveAsignacionMaestro_ValidData() {
        when(asignacionMaestroRepository.save(asignacion)).thenReturn(asignacion);

        AsignacionMaestroModel savedAsignacion = asignacionMaestroService.saveAsignacionMaestro(asignacion);

        assertNotNull(savedAsignacion);
        assertEquals(asignacion.getId(), savedAsignacion.getId());
        verify(asignacionMaestroRepository, times(1)).save(asignacion);
    }

    @Test
    void getAsignacionMaestroById_ValidId() {
        when(asignacionMaestroRepository.findById(asignacionId)).thenReturn(Optional.of(asignacion));

        Optional<AsignacionMaestroModel> result = asignacionMaestroService.getAsignacionMaestroById(1, 2);

        assertTrue(result.isPresent());
        assertEquals(asignacion, result.get());
        verify(asignacionMaestroRepository, times(1)).findById(asignacionId);
    }

    @Test
    void getAsignacionMaestroById_NotFound() {
        when(asignacionMaestroRepository.findById(asignacionId)).thenReturn(Optional.empty());

        Optional<AsignacionMaestroModel> result = asignacionMaestroService.getAsignacionMaestroById(1, 2);

        assertFalse(result.isPresent());
        verify(asignacionMaestroRepository, times(1)).findById(asignacionId);
    }

    @Test
    void deleteAsignacionMaestro_ValidId() {
        doNothing().when(asignacionMaestroRepository).deleteById(asignacionId);

        asignacionMaestroService.deleteAsignacionMaestro(1, 2);

        verify(asignacionMaestroRepository, times(1)).deleteById(asignacionId);
    }

    @Test
    void getAsignacionesByProyecto() {
        when(asignacionMaestroRepository.findByProyectoId(2)).thenReturn(Arrays.asList(asignacion));

        List<AsignacionMaestroModel> asignaciones = asignacionMaestroService.getAsignacionesByProyecto(2);

        assertEquals(1, asignaciones.size());
        assertEquals(asignacion, asignaciones.get(0));
        verify(asignacionMaestroRepository, times(1)).findByProyectoId(2);
    }

    @Test
    void getAsignacionesByMaestro() {
        when(asignacionMaestroRepository.findByMaestroId(1)).thenReturn(Arrays.asList(asignacion));

        List<AsignacionMaestroModel> asignaciones = asignacionMaestroService.getAsignacionesByMaestro(1);

        assertEquals(1, asignaciones.size());
        assertEquals(asignacion, asignaciones.get(0));
        verify(asignacionMaestroRepository, times(1)).findByMaestroId(1);
    }
}