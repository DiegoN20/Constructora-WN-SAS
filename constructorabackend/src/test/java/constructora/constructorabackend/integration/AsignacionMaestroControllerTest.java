package constructora.constructorabackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import constructora.constructorabackend.controller.AsignacionMaestroController;
import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.model.MaestroModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import constructora.constructorabackend.service.AsignacionMaestroService;
import constructora.constructorabackend.repository.IMaestroRepository;
import constructora.constructorabackend.repository.IProyectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AsignacionMaestroController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad
class AsignacionMaestroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtilService jwtUtilService;

    @MockBean
    private IProyectRepository proyectRepository;

    @MockBean
    private IMaestroRepository maestroRepository;

    @MockBean
    private AsignacionMaestroService asignacionMaestroService;

    @Autowired
    private ObjectMapper objectMapper;

    private AsignacionMaestroModel asignacion;

    @BeforeEach
    void setUp() {
        ProyectModel proyecto = new ProyectModel();
        proyecto.setIdProyectos(2);
        proyecto.setNombreProyecto("Proyecto A");

        MaestroModel maestro = new MaestroModel();
        maestro.setId_maestros_de_obra(1);
        maestro.setNombre("Maestro A");

        asignacion = new AsignacionMaestroModel();
        asignacion.setMaestro(maestro);
        asignacion.setProyecto(proyecto);
        asignacion.setFechaAsignacion(LocalDate.of(2023, 1, 1));
        asignacion.setFechaFin(LocalDate.of(2023, 12, 31));
        asignacion.setEstadoAsignacion(AsignacionMaestroModel.EstadoAsignacion.Activo);

        // Mock para repositorios
        when(proyectRepository.findById(2)).thenReturn(Optional.of(proyecto));
        when(maestroRepository.findById(1)).thenReturn(Optional.of(maestro));
    }

    @Test
    void saveAsignacionMaestro_ValidData() throws Exception {
        when(asignacionMaestroService.getAsignacionMaestroById(1, 2)).thenReturn(Optional.empty());
        when(asignacionMaestroService.saveAsignacionMaestro(any(AsignacionMaestroModel.class))).thenReturn(asignacion);

        String jsonRequest = objectMapper.writeValueAsString(asignacion);

        mockMvc.perform(post("/api/v1/AsignacionMaestros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(asignacionMaestroService, times(1)).saveAsignacionMaestro(any(AsignacionMaestroModel.class));
    }

    @Test
    void saveAsignacionMaestro_AlreadyExists() throws Exception {
        when(asignacionMaestroService.getAsignacionMaestroById(1, 2)).thenReturn(Optional.of(asignacion));

        String jsonRequest = objectMapper.writeValueAsString(asignacion);

        mockMvc.perform(post("/api/v1/AsignacionMaestros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict()); // Verifica respuesta HTTP 409

        verify(asignacionMaestroService, times(1)).getAsignacionMaestroById(1, 2);
        verify(asignacionMaestroService, never()).saveAsignacionMaestro(any(AsignacionMaestroModel.class));
    }

    @Test
    void getAsignacionMaestroById_ValidId() throws Exception {
        when(asignacionMaestroService.getAsignacionMaestroById(1, 2)).thenReturn(Optional.of(asignacion));

        mockMvc.perform(get("/api/v1/AsignacionMaestros/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProyecto").value("Proyecto A"))
                .andExpect(jsonPath("$.nombreMaestro").value("Maestro A"));

        verify(asignacionMaestroService, times(1)).getAsignacionMaestroById(1, 2);
    }

    @Test
    void getAsignacionMaestroById_NotFound() throws Exception {
        when(asignacionMaestroService.getAsignacionMaestroById(1, 2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/AsignacionMaestros/1/2"))
                .andExpect(status().isNotFound());

        verify(asignacionMaestroService, times(1)).getAsignacionMaestroById(1, 2);
    }

    @Test
    void deleteAsignacionMaestro_ValidId() throws Exception {
        when(asignacionMaestroService.getAsignacionMaestroById(1, 2)).thenReturn(Optional.of(asignacion));
        doNothing().when(asignacionMaestroService).deleteAsignacionMaestro(1, 2);

        mockMvc.perform(delete("/api/v1/AsignacionMaestros/1/2"))
                .andExpect(status().isOk());

        verify(asignacionMaestroService, times(1)).deleteAsignacionMaestro(1, 2);
    }

    @Test
    void deleteAsignacionMaestro_NotFound() throws Exception {
        when(asignacionMaestroService.getAsignacionMaestroById(1, 2)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/AsignacionMaestros/1/2"))
                .andExpect(status().isNotFound());

        verify(asignacionMaestroService, times(1)).getAsignacionMaestroById(1, 2);
    }
}