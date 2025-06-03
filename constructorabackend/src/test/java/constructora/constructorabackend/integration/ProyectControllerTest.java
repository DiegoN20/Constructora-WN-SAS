package constructora.constructorabackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import constructora.constructorabackend.controller.ProyectController;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.seguridad.model.UserModel;
import constructora.constructorabackend.seguridad.repository.IUserRepository;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import constructora.constructorabackend.service.ProyectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProyectController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad
class ProyectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtilService jwtUtilService;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private ProyectService proyectService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    private ProyectModel proyect;

    @BeforeEach
    void setUp() {
        // Crea un usuario para pruebas
        UserModel user = new UserModel();
        user.setId_usuarios(1);
        user.setNombre("Juan");
        user.setCorreo("juan@example.com");

        // Configura el mock para findById que devuelve directamente UserModel
        when(userRepository.findById(1)).thenReturn(user);

        // Configura un proyecto de prueba
        proyect = new ProyectModel();
        proyect.setIdProyectos(1);
        proyect.setNombreProyecto("Proyecto Test");
        proyect.setDireccion("Calle 123");
        proyect.setDescripcion("Descripción de prueba");
        proyect.setUsuario(user);
        proyect.setPresupuestoPrevisto(100000);
        proyect.setPresupuestoActual(50000);
        proyect.setPresupuestoRestante(50000);
    }

    @Test
    void getProyectoById_ValidId() throws Exception {
        when(proyectService.getProyectoById(1)).thenReturn(java.util.Optional.of(proyect));

        mockMvc.perform(get("/api/v1/Proyectos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProyecto").value("Proyecto Test"))
                .andExpect(jsonPath("$.direccion").value("Calle 123"))
                .andExpect(jsonPath("$.descripcion").value("Descripción de prueba"));

        verify(proyectService, times(1)).getProyectoById(1);
    }

    @Test
    void getProyectoById_NotFound() throws Exception {
        when(proyectService.getProyectoById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/v1/Proyectos/99"))
                .andExpect(status().isNotFound());

        verify(proyectService, times(1)).getProyectoById(99);
    }

    @Test
    void saveProyecto_ValidData() throws Exception {
        when(proyectService.saveProyecto(any(ProyectModel.class))).thenReturn(proyect);

        String jsonRequest = objectMapper.writeValueAsString(proyect);

        mockMvc.perform(post("/api/v1/Proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreProyecto").value("Proyecto Test"));

        verify(proyectService, times(1)).saveProyecto(any(ProyectModel.class));
    }

    @Test
    void saveProyecto_InvalidData() throws Exception {
        String invalidRequest = "{\"nombreProyecto\": \"\", \"direccion\": \"\", \"descripcion\": \"\", \"usuario\": null, \"presupuestoPrevisto\": 0}";

        mockMvc.perform(post("/api/v1/Proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteProyecto_ValidId() throws Exception {
        when(proyectService.getProyectoById(1)).thenReturn(java.util.Optional.of(proyect));
        doNothing().when(proyectService).deleteProyecto(1);

        mockMvc.perform(delete("/api/v1/Proyectos/1"))
                .andExpect(status().isOk());

        verify(proyectService, times(1)).deleteProyecto(1);
    }

    @Test
    void deleteProyecto_NotFound() throws Exception {
        when(proyectService.getProyectoById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(delete("/api/v1/Proyectos/99"))
                .andExpect(status().isNotFound());

        verify(proyectService, times(1)).getProyectoById(99);
    }
}