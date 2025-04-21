package constructora.constructorabackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import constructora.constructorabackend.controller.AvancePorPisoController;
import constructora.constructorabackend.model.AvancePorPisoModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import constructora.constructorabackend.repository.IProyectRepository;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import constructora.constructorabackend.service.AvancePorPisoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AvancePorPisoController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad
class AvancePorPisoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtilService jwtUtilService;

    @MockBean
    private AvancePorPisoService avancePorPisoService;

    @MockBean
    private IProyectRepository iProyectRepository;

    @MockBean
    private IInsumoRepository iInsumoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AvancePorPisoModel avance;

    @BeforeEach
    void setUp() {
        ProyectModel proyecto = new ProyectModel();
        proyecto.setIdProyectos(1);

        InsumoModel insumo = new InsumoModel();
        insumo.setIdInsumos(1);

        avance = new AvancePorPisoModel();
        avance.setIdAvancePorPiso(1); // Asegúrate de configurar un ID válido
        avance.setProyecto(proyecto);
        avance.setInsumo(insumo);
        avance.setNumeroPiso(2);
        avance.setCantidadComprada(100);
        avance.setCostoInsumos(5000);
        avance.setFechaCompra(LocalDate.of(2023, 1, 1));
        avance.setCantidadUsada(50);
    }


    @Test
    void saveAvancePorPiso_ValidData() throws Exception {
        ProyectModel proyecto = new ProyectModel();
        proyecto.setIdProyectos(1);
        proyecto.setNombreProyecto("Proyecto Test");

        InsumoModel insumo = new InsumoModel();
        insumo.setIdInsumos(1);
        insumo.setNombreInsumo("Insumo Test");

        avance.setProyecto(proyecto);
        avance.setInsumo(insumo);

        // Mock del repositorio y servicio
        when(iProyectRepository.findById(1)).thenReturn(Optional.of(proyecto));
        when(iInsumoRepository.findById(1)).thenReturn(Optional.of(insumo));
        when(avancePorPisoService.saveAvancePorPiso(any(AvancePorPisoModel.class))).thenReturn(avance);

        // JSON Request
        String jsonRequest = objectMapper.writeValueAsString(avance);

        mockMvc.perform(post("/api/v1/AvancePorPisos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(avancePorPisoService, times(1)).saveAvancePorPiso(any(AvancePorPisoModel.class));
    }

    @Test
    void getAllAvancePorPisos() throws Exception {
        List<AvancePorPisoModel> avances = Arrays.asList(avance);
        when(avancePorPisoService.getAvancePorPisos()).thenReturn(avances);

        mockMvc.perform(get("/api/v1/AvancePorPisos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidadComprada").value(100))
                .andExpect(jsonPath("$[0].numeroPiso").value(2));

        verify(avancePorPisoService, times(1)).getAvancePorPisos();
    }

    @Test
    void getAvancePorPisoById_ValidId() throws Exception {
        when(avancePorPisoService.getAvancePorPisoById(1)).thenReturn(Optional.of(avance));

        mockMvc.perform(get("/api/v1/AvancePorPisos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadComprada").value(100))
                .andExpect(jsonPath("$.numeroPiso").value(2));

        verify(avancePorPisoService, times(1)).getAvancePorPisoById(1);
    }

    @Test
    void getAvancePorPisoById_NotFound() throws Exception {
        when(avancePorPisoService.getAvancePorPisoById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/AvancePorPisos/1"))
                .andExpect(status().isNotFound());

        verify(avancePorPisoService, times(1)).getAvancePorPisoById(1);
    }

    @Test
    void getAvancePorPisoByProyecto() throws Exception {
        List<AvancePorPisoModel> avances = Arrays.asList(avance);
        when(avancePorPisoService.getAvancePorPisoByProyecto(1)).thenReturn(avances);

        mockMvc.perform(get("/api/v1/AvancePorPisos/proyecto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidadComprada").value(100))
                .andExpect(jsonPath("$[0].numeroPiso").value(2));

        verify(avancePorPisoService, times(1)).getAvancePorPisoByProyecto(1);
    }

    @Test
    void deleteAvancePorPiso_ValidId() throws Exception {
        // Mock para simular que el registro existe
        when(avancePorPisoService.getAvancePorPisoById(1)).thenReturn(Optional.of(avance));
        doNothing().when(avancePorPisoService).deleteAvancePorPiso(1);

        // Simula la solicitud DELETE
        mockMvc.perform(delete("/api/v1/AvancePorPisos/1"))
                .andExpect(status().isOk());

        // Verifica que los métodos mock fueron llamados con los argumentos correctos
        verify(avancePorPisoService, times(1)).getAvancePorPisoById(1);
        verify(avancePorPisoService, times(1)).deleteAvancePorPiso(1);
    }

    @Test
    void deleteAvancePorPiso_NotFound() throws Exception {
        // Mock para simular que el registro no existe
        when(avancePorPisoService.getAvancePorPisoById(1)).thenReturn(Optional.empty());

        // Simula la solicitud DELETE
        mockMvc.perform(delete("/api/v1/AvancePorPisos/1"))
                .andExpect(status().isNotFound());

        // Verifica que el servicio fue invocado correctamente
        verify(avancePorPisoService, times(1)).getAvancePorPisoById(1);
        verify(avancePorPisoService, never()).deleteAvancePorPiso(anyInt()); // Nunca debe llamar delete
    }
}