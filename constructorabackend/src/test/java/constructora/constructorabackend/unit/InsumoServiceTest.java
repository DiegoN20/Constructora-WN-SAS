package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import constructora.constructorabackend.service.InsumoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static constructora.constructorabackend.model.InsumoModel.Tipo.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsumoServiceTest {

    @Mock
    private IInsumoRepository insumoRepository;

    @InjectMocks
    private InsumoService insumoService;

    private InsumoModel insumo1;
    private InsumoModel insumo2;

    @BeforeEach
    void setUp() {
        insumo1 = new InsumoModel();
        insumo1.setIdInsumos(1);
        insumo1.setNombreInsumo("Martillo");
        insumo1.setDescripcion("Martillo de acero 16 oz");
        insumo1.setTipo(Herramienta);

        insumo2 = new InsumoModel();
        insumo2.setIdInsumos(2);
        insumo2.setNombreInsumo("Cemento");
        insumo2.setDescripcion("Bolsa de cemento 50 kg");
        insumo2.setTipo(Material);
    }

    @Test
    void saveInsumo_ValidData() {
        when(insumoRepository.save(insumo1)).thenReturn(insumo1);

        InsumoModel savedInsumo = insumoService.saveInsumo(insumo1);

        assertNotNull(savedInsumo);
        assertEquals(insumo1.getIdInsumos(), savedInsumo.getIdInsumos());
        assertEquals("Martillo", savedInsumo.getNombreInsumo());
        verify(insumoRepository, times(1)).save(insumo1);
    }

    @Test
    void updateInsumo_ValidData() {
        when(insumoRepository.existsById(insumo1.getIdInsumos())).thenReturn(true);
        when(insumoRepository.save(insumo1)).thenReturn(insumo1);

        InsumoModel updatedInsumo = insumoService.updateInsumo(insumo1);

        assertNotNull(updatedInsumo);
        assertEquals(insumo1.getIdInsumos(), updatedInsumo.getIdInsumos());
        verify(insumoRepository, times(1)).save(insumo1);
    }

    @Test
    void getInsumos() {
        when(insumoRepository.findAll()).thenReturn(Arrays.asList(insumo1, insumo2));

        List<InsumoModel> insumos = insumoService.getInsumos();

        assertEquals(2, insumos.size());
        verify(insumoRepository, times(1)).findAll();
    }

    @Test
    void getInsumoById_ValidId() {
        when(insumoRepository.findById(1)).thenReturn(Optional.of(insumo1));

        Optional<InsumoModel> foundInsumo = insumoService.getInsumoById(1);

        assertTrue(foundInsumo.isPresent());
        assertEquals(insumo1.getIdInsumos(), foundInsumo.get().getIdInsumos());
        verify(insumoRepository, times(1)).findById(1);
    }

    @Test
    void getInsumoById_NotFound() {
        when(insumoRepository.findById(99)).thenReturn(Optional.empty());

        Optional<InsumoModel> foundInsumo = insumoService.getInsumoById(99);

        assertTrue(foundInsumo.isEmpty());
        verify(insumoRepository, times(1)).findById(99);
    }

    @Test
    void deleteInsumo_ValidId() {
        when(insumoRepository.existsById(1)).thenReturn(true);
        doNothing().when(insumoRepository).deleteById(1);

        insumoService.deleteInsumo(1);

        verify(insumoRepository, times(1)).deleteById(1);
    }

    @Test
    void saveInsumo_InvalidData() {
        InsumoModel invalidInsumo = new InsumoModel();
        invalidInsumo.setNombreInsumo(""); // Nombre vacío

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            insumoService.saveInsumo(invalidInsumo);
        });

        String expectedMessage = "El nombre del insumo no puede estar vacío";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void getInsumoById_InvalidId() {
        int invalidId = -1;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            insumoService.getInsumoById(invalidId);
        });

        String expectedMessage = "ID no válido";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void deleteInsumo_InvalidId() {
        int invalidId = -1;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            insumoService.deleteInsumo(invalidId);
        });

        String expectedMessage = "ID no válido";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void updateInsumo_NotFound() {
        InsumoModel nonExistingInsumo = new InsumoModel();
        nonExistingInsumo.setIdInsumos(99);
        when(insumoRepository.existsById(99)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            insumoService.updateInsumo(nonExistingInsumo);
        });

        String expectedMessage = "Insumo no encontrado";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}