package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.MaestroModel;
import constructora.constructorabackend.repository.IMaestroRepository;
import constructora.constructorabackend.service.MaestroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static constructora.constructorabackend.model.MaestroModel.EstadoMaestro.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaestroServiceTest {

    @Mock
    private IMaestroRepository maestroRepository;

    @InjectMocks
    private MaestroService maestroService;

    private MaestroModel maestro1;
    private MaestroModel maestro2;

    @BeforeEach
    void setUp() {
        maestro1 = new MaestroModel();
        maestro1.setId_maestros_de_obra(1);
        maestro1.setCedula(123456789L);
        maestro1.setNombre("Juan");
        maestro1.setApellido("Pérez");
        maestro1.setTelefono(3001234567L);
        maestro1.setEstadoMaestro(Disponible);
        maestro1.setSalario(2000000L);

        maestro2 = new MaestroModel();
        maestro2.setId_maestros_de_obra(2);
        maestro2.setCedula(987654321L);
        maestro2.setNombre("Carlos");
        maestro2.setApellido("Gómez");
        maestro2.setTelefono(3109876543L);
        maestro2.setEstadoMaestro(Asignado_A_Proyecto);
        maestro2.setSalario(2500000L);
    }

    @Test
    void saveMaestro() {
        when(maestroRepository.save(maestro1)).thenReturn(maestro1);

        MaestroModel savedMaestro = maestroService.saveMaestro(maestro1);

        assertNotNull(savedMaestro);
        assertEquals(maestro1.getId_maestros_de_obra(), savedMaestro.getId_maestros_de_obra());
        verify(maestroRepository, times(1)).save(maestro1);
    }

    @Test
    void updateMaestro() {
        when(maestroRepository.save(maestro1)).thenReturn(maestro1);

        MaestroModel updatedMaestro = maestroService.updateMaestro(maestro1);

        assertNotNull(updatedMaestro);
        assertEquals(maestro1.getId_maestros_de_obra(), updatedMaestro.getId_maestros_de_obra());
        verify(maestroRepository, times(1)).save(maestro1);
    }

    @Test
    void getMaestros() {
        when(maestroRepository.findAll()).thenReturn(Arrays.asList(maestro1, maestro2));

        List<MaestroModel> maestros = maestroService.getMaestros();

        assertEquals(2, maestros.size());
        verify(maestroRepository, times(1)).findAll();
    }

    @Test
    void getMaestroById() {
        when(maestroRepository.findById(1)).thenReturn(Optional.of(maestro1));

        Optional<MaestroModel> foundMaestro = maestroService.getMaestroById(1);

        assertTrue(foundMaestro.isPresent());
        assertEquals(maestro1.getId_maestros_de_obra(), foundMaestro.get().getId_maestros_de_obra());
        verify(maestroRepository, times(1)).findById(1);
    }

    @Test
    void getMaestroById_NotFound() {
        when(maestroRepository.findById(99)).thenReturn(Optional.empty());

        Optional<MaestroModel> foundMaestro = maestroService.getMaestroById(99);

        assertTrue(foundMaestro.isEmpty());
        verify(maestroRepository, times(1)).findById(99);
    }

    @Test
    void deleteMaestro() {
        doNothing().when(maestroRepository).deleteById(1);

        maestroService.deleteMaestro(1);

        verify(maestroRepository, times(1)).deleteById(1);
    }

    @Test
    void getMaestrosDisponibles() {
        when(maestroRepository.findByEstadoMaestro(Disponible)).thenReturn(Arrays.asList(maestro1));

        List<MaestroModel> disponibles = maestroService.getMaestrosDisponibles();

        assertEquals(1, disponibles.size());
        assertEquals(Disponible, disponibles.get(0).getEstadoMaestro());
        verify(maestroRepository, times(1)).findByEstadoMaestro(Disponible);
    }

    @Test
    void saveMaestro_InvalidData() {
        MaestroModel invalidMaestro = new MaestroModel();
        invalidMaestro.setId_maestros_de_obra(3);
        invalidMaestro.setCedula(-123456789L); // Cedula inválida
        invalidMaestro.setNombre("");
        invalidMaestro.setApellido("");
        invalidMaestro.setTelefono(-3001234567L); // Teléfono inválido
        invalidMaestro.setEstadoMaestro(MaestroModel.EstadoMaestro.Disponible);
        invalidMaestro.setSalario(-2000000L); // Salario inválido

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            maestroService.saveMaestro(invalidMaestro);
        });

        String expectedMessage = "Datos inválidos";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getMaestroById_InvalidId() {
        int invalidId = -1; // ID inválido

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            maestroService.getMaestroById(invalidId);
        });

        String expectedMessage = "ID no valido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteMaestro_InvalidId() {
        int invalidId = -1; // ID inválido

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            maestroService.deleteMaestro(invalidId);
        });

        String expectedMessage = "ID no valido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveMaestro_NullData() {
        MaestroModel nullMaestro = new MaestroModel();
        nullMaestro.setId_maestros_de_obra(3);
        nullMaestro.setCedula(123456789L);
        nullMaestro.setNombre(null); // Nombre nulo
        nullMaestro.setApellido(null); // Apellido nulo
        nullMaestro.setTelefono(3001234567L);
        nullMaestro.setEstadoMaestro(MaestroModel.EstadoMaestro.Disponible);
        nullMaestro.setSalario(2000000L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            maestroService.saveMaestro(nullMaestro);
        });

        String expectedMessage = "Datos inválidos";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateMaestro_ValidData() {
        MaestroModel updatedMaestro = new MaestroModel();
        updatedMaestro.setId_maestros_de_obra(1);
        updatedMaestro.setCedula(123456789L);
        updatedMaestro.setNombre("Juan");
        updatedMaestro.setApellido("Pérez");
        updatedMaestro.setTelefono(3001234567L);
        updatedMaestro.setEstadoMaestro(MaestroModel.EstadoMaestro.Disponible);
        updatedMaestro.setSalario(2500000L); // Salario actualizado

        when(maestroRepository.save(updatedMaestro)).thenReturn(updatedMaestro);

        MaestroModel result = maestroService.updateMaestro(updatedMaestro);

        assertNotNull(result);
        assertEquals(updatedMaestro.getSalario(), result.getSalario());
        verify(maestroRepository, times(1)).save(updatedMaestro);
    }

    @Test
    void deleteMaestro_ValidId() {
        doNothing().when(maestroRepository).deleteById(1);

        maestroService.deleteMaestro(1);

        verify(maestroRepository, times(1)).deleteById(1);
    }

    @Test
    void saveMaestro_NegativeSalary() {
        MaestroModel invalidMaestro = new MaestroModel();
        invalidMaestro.setId_maestros_de_obra(3);
        invalidMaestro.setCedula(123456789L);
        invalidMaestro.setNombre("Juan");
        invalidMaestro.setApellido("Pérez");
        invalidMaestro.setTelefono(3001234567L);
        invalidMaestro.setEstadoMaestro(MaestroModel.EstadoMaestro.Disponible);
        invalidMaestro.setSalario(-2000000L); // Salario negativo

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            maestroService.saveMaestro(invalidMaestro);
        });

        String expectedMessage = "Datos inválidos";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}