package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.repository.IInventarioInicialRepository;
import constructora.constructorabackend.service.InventarioInicialService;
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

class InventarioInicialServiceTest {

    @Mock
    private IInventarioInicialRepository inventarioInicialRepository;

    @InjectMocks
    private InventarioInicialService inventarioInicialService;

    private InventarioInicialModel inventario;
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

        inventario = new InventarioInicialModel();
        inventario.setIdInventarioInicial(1);
        inventario.setProyecto(proyecto);
        inventario.setInsumo(insumo);
        inventario.setCantidad(100);
        inventario.setPrecio(50);
        inventario.setUnidad("Unidad A");
    }

    @Test
    void saveInventarioInicial_ValidData() {
        when(inventarioInicialRepository.save(inventario)).thenReturn(inventario);

        InventarioInicialModel savedInventario = inventarioInicialService.saveInventarioInicial(inventario);

        assertNotNull(savedInventario);
        assertEquals(inventario.getIdInventarioInicial(), savedInventario.getIdInventarioInicial());
        verify(inventarioInicialRepository, times(1)).save(inventario);
    }

    @Test
    void findByProyectoAndInsumo_ValidData() {
        when(inventarioInicialRepository.findByProyectoAndInsumo(proyecto, insumo)).thenReturn(Optional.of(inventario));

        Optional<InventarioInicialModel> result = inventarioInicialService.findByProyectoAndInsumo(proyecto, insumo);

        assertTrue(result.isPresent());
        assertEquals(inventario, result.get());
        verify(inventarioInicialRepository, times(1)).findByProyectoAndInsumo(proyecto, insumo);
    }

    @Test
    void getInventarioInicialById_ValidId() {
        when(inventarioInicialRepository.findById(1)).thenReturn(Optional.of(inventario));

        Optional<InventarioInicialModel> result = inventarioInicialService.getInventarioInicialById(1);

        assertTrue(result.isPresent());
        assertEquals(inventario, result.get());
        verify(inventarioInicialRepository, times(1)).findById(1);
    }

    @Test
    void getInventarioInicialByProyecto_ValidId() {
        when(inventarioInicialRepository.findByProyectoId(1)).thenReturn(Arrays.asList(inventario));

        List<InventarioInicialModel> inventarios = inventarioInicialService.getInventarioInicialByProyecto(1);

        assertEquals(1, inventarios.size());
        assertEquals(inventario, inventarios.get(0));
        verify(inventarioInicialRepository, times(1)).findByProyectoId(1);
    }

    @Test
    void deleteInventarioInicial_ValidId() {
        doNothing().when(inventarioInicialRepository).deleteById(1);

        inventarioInicialService.deleteInventarioInicial(1);

        verify(inventarioInicialRepository, times(1)).deleteById(1);
    }
}