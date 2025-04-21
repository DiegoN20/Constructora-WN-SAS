package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.AvancePorPisoModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.repository.IAvancePorPisoRepository;
import constructora.constructorabackend.service.AvancePorPisoService;
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

class AvancePorPisoServiceTest {

    @Mock
    private IAvancePorPisoRepository avancePorPisoRepository;

    @InjectMocks
    private AvancePorPisoService avancePorPisoService;

    private AvancePorPisoModel avance;
    private ProyectModel proyecto;
    private InsumoModel insumo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        proyecto = new ProyectModel();
        proyecto.setIdProyectos(1);
        proyecto.setNombreProyecto("Proyecto A");

        insumo = new InsumoModel();
        insumo.setIdInsumos(1);
        insumo.setNombreInsumo("Insumo A");

        avance = new AvancePorPisoModel();
        avance.setIdAvancePorPiso(1);
        avance.setProyecto(proyecto);
        avance.setInsumo(insumo);
        avance.setNumeroPiso(2);
        avance.setCantidadComprada(100);
        avance.setCostoInsumos(5000);
        avance.setFechaCompra(LocalDate.of(2023, 1, 1));
        avance.setCantidadUsada(50);
    }

    @Test
    void saveAvancePorPiso_ValidData() {
        when(avancePorPisoRepository.save(avance)).thenReturn(avance);

        AvancePorPisoModel savedAvance = avancePorPisoService.saveAvancePorPiso(avance);

        assertNotNull(savedAvance);
        assertEquals(avance.getIdAvancePorPiso(), savedAvance.getIdAvancePorPiso());
        verify(avancePorPisoRepository, times(1)).save(avance);
    }

    @Test
    void findByProyectoAndInsumo_ValidData() {
        when(avancePorPisoRepository.findByProyectoAndInsumo(proyecto, insumo)).thenReturn(Optional.of(avance));

        Optional<AvancePorPisoModel> result = avancePorPisoService.findByProyectoAndInsumo(proyecto, insumo);

        assertTrue(result.isPresent());
        assertEquals(avance, result.get());
        verify(avancePorPisoRepository, times(1)).findByProyectoAndInsumo(proyecto, insumo);
    }

    @Test
    void getAvancePorPisoById_ValidId() {
        when(avancePorPisoRepository.findById(1)).thenReturn(Optional.of(avance));

        Optional<AvancePorPisoModel> result = avancePorPisoService.getAvancePorPisoById(1);

        assertTrue(result.isPresent());
        assertEquals(avance, result.get());
        verify(avancePorPisoRepository, times(1)).findById(1);
    }

    @Test
    void getAvancePorPisoByProyecto_ValidId() {
        when(avancePorPisoRepository.findByProyectoId(1)).thenReturn(Arrays.asList(avance));

        List<AvancePorPisoModel> avances = avancePorPisoService.getAvancePorPisoByProyecto(1);

        assertEquals(1, avances.size());
        assertEquals(avance, avances.get(0));
        verify(avancePorPisoRepository, times(1)).findByProyectoId(1);
    }

    @Test
    void deleteAvancePorPiso_ValidId() {
        doNothing().when(avancePorPisoRepository).deleteById(1);

        avancePorPisoService.deleteAvancePorPiso(1);

        verify(avancePorPisoRepository, times(1)).deleteById(1);
    }
}