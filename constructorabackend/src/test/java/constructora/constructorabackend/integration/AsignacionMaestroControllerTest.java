package constructora.constructorabackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import constructora.constructorabackend.controller.AsignacionMaestroController;
import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.service.AsignacionMaestroService;
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
@AutoConfigureMockMvc(addFilters = false) // Desactiva seguridad
class AsignacionMaestroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsignacionMaestroService asignacionMaestroService;

    @Autowired
    private ObjectMapper objectMapper;

    private AsignacionMaestroModel asignacion;

    @BeforeEach
    void setUp() {
        asignacion = new AsignacionMaestroModel();
        asignacion.setFechaAsignacion(LocalDate.of(2023, 1, 1));
        asignacion.setFechaFin(LocalDate.of(2023, 12, 31));
        asignacion.setEstadoAsignacion(AsignacionMaestroModel.EstadoAsignacion.Activo);
    }

    @Test
    void saveAsignacionMaestro_ValidData() throws Exception {
        when(asignacionMaestroService.saveAsignacionMaestro(any(AsignacionMaestroModel.class))).thenReturn(asignacion);

        String jsonRequest = objectMapper.writeValueAsString(asignacion);

        mockMvc.perform(post("/api/v1/asignaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());

        verify(asignacionMaestroService, times(1)).saveAsignacionMaestro(any(AsignacionMaestroModel.class));
    }

    @Test
    void getAsignacionMaestroById_ValidId() throws Exception {
        when(asignacionMaestroService.getAsignacionMaestroById(1, 2)).thenReturn(Optional.of(asignacion));

        mockMvc.perform(get("/api/v1/asignaciones/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAsignacionMaestro_ValidId() throws Exception {
        doNothing().when(asignacionMaestroService).deleteAsignacionMaestro(1, 2);

        mockMvc.perform(delete("/api/v1/asignaciones/1/2"))
                .andExpect(status().isOk());

        verify(asignacionMaestroService, times(1)).deleteAsignacionMaestro(1, 2);
    }
}