package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.ProveedorModel;
import constructora.constructorabackend.repository.IProveedorRepository;
import constructora.constructorabackend.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private IProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private ProveedorModel proveedor1;
    private ProveedorModel proveedor2;

    @BeforeEach
    void setUp() {
        proveedor1 = new ProveedorModel();
        proveedor1.setIdProveedores(1);
        proveedor1.setNombreProveedor("Arquitectura Construcción S.A.");
        proveedor1.setTipoServicio("Arquitectura materiales");
        proveedor1.setCorreo("contacto@arqconstruccion.com");
        proveedor1.setTelefono(3101234567L);

        proveedor2 = new ProveedorModel();
        proveedor2.setIdProveedores(2);
        proveedor2.setNombreProveedor("Electricidad Total");
        proveedor2.setTipoServicio("Servicios eléctricos");
        proveedor2.setCorreo("info@electricidadtotal.com");
        proveedor2.setTelefono(3209876543L);
    }

    @Test
    void saveProveedor_ValidData() {
        when(proveedorRepository.save(proveedor1)).thenReturn(proveedor1);

        ProveedorModel savedProveedor = proveedorService.saveProveedor(proveedor1);

        assertNotNull(savedProveedor);
        assertEquals(proveedor1.getIdProveedores(), savedProveedor.getIdProveedores());
        assertEquals("Arquitectura Construcción S.A.", savedProveedor.getNombreProveedor());
        verify(proveedorRepository, times(1)).save(proveedor1);
    }

    @Test
    void getProveedors() {
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor1, proveedor2));

        List<ProveedorModel> proveedores = proveedorService.getProveedors();

        assertEquals(2, proveedores.size());
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    void getProveedorById_ValidId() {
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedor1));

        Optional<ProveedorModel> foundProveedor = proveedorService.getProveedorById(1);

        assertTrue(foundProveedor.isPresent());
        assertEquals(proveedor1.getIdProveedores(), foundProveedor.get().getIdProveedores());
        verify(proveedorRepository, times(1)).findById(1);
    }

    @Test
    void getProveedorById_NotFound() {
        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());

        Optional<ProveedorModel> foundProveedor = proveedorService.getProveedorById(99);

        assertTrue(foundProveedor.isEmpty());
        verify(proveedorRepository, times(1)).findById(99);
    }

    @Test
    void deleteProveedor_ValidId() {
        doNothing().when(proveedorRepository).deleteById(1);

        proveedorService.deleteProveedor(1);

        verify(proveedorRepository, times(1)).deleteById(1);
    }

    @Test
    void saveProveedor_InvalidData() {
        ProveedorModel invalidProveedor = new ProveedorModel();
        invalidProveedor.setIdProveedores(3);
        invalidProveedor.setNombreProveedor(""); // Nombre vacío
        invalidProveedor.setTipoServicio(""); // Tipo servicio vacío
        invalidProveedor.setCorreo("correo-invalido"); // Correo inválido
        invalidProveedor.setTelefono(-1234567L); // Teléfono inválido

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            proveedorService.saveProveedor(invalidProveedor);
        });

        String expectedMessage = "Datos inválidos";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getProveedorById_InvalidId() {
        int invalidId = -1; // ID inválido

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            proveedorService.getProveedorById(invalidId);
        });

        String expectedMessage = "ID no válido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteProveedor_InvalidId() {
        int invalidId = -1; // ID inválido

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            proveedorService.deleteProveedor(invalidId);
        });

        String expectedMessage = "ID no válido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveProveedor_InvalidEmail() {
        ProveedorModel invalidProveedor = new ProveedorModel();
        invalidProveedor.setIdProveedores(3);
        invalidProveedor.setNombreProveedor("Proveedor Válido");
        invalidProveedor.setTipoServicio("Servicio válido");
        invalidProveedor.setCorreo("correo-invalido"); // Correo inválido
        invalidProveedor.setTelefono(3101234567L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            proveedorService.saveProveedor(invalidProveedor);
        });

        String expectedMessage = "Datos inválidos";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateProveedor_NotFound() {
        ProveedorModel nonExistingProveedor = new ProveedorModel();
        nonExistingProveedor.setIdProveedores(99); // ID que no existe
        nonExistingProveedor.setNombreProveedor("No existe");

        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            proveedorService.updateProveedor(nonExistingProveedor);
        });

        String expectedMessage = "Proveedor no encontrado";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
