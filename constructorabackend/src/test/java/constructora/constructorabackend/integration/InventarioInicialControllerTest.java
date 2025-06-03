package constructora.constructorabackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import constructora.constructorabackend.controller.InventarioInicialController;
import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import constructora.constructorabackend.repository.IProyectRepository;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import constructora.constructorabackend.service.InventarioInicialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InventarioInicialController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad
class InventarioInicialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtilService jwtUtilService;

    @MockBean
    private InventarioInicialService inventarioInicialService;

    @MockBean
    private IProyectRepository iProyectRepository;

    @MockBean
    private IInsumoRepository iInsumoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private InventarioInicialModel inventario;

    @BeforeEach
    void setUp() {
        ProyectModel proyecto = new ProyectModel();
        proyecto.setIdProyectos(1);

        InsumoModel insumo = new InsumoModel();
        insumo.setIdInsumos(1);

        inventario = new InventarioInicialModel();
        inventario.setIdInventarioInicial(1); // Configura un ID válido
        inventario.setProyecto(proyecto);
        inventario.setInsumo(insumo);
        inventario.setCantidad(100);
        inventario.setPrecio(50);
        inventario.setUnidad("Unidad A");
    }

    @Test
    void saveInventarioInicial_ValidData() throws Exception {
        ProyectModel proyecto = new ProyectModel();
        proyecto.setIdProyectos(1);
        proyecto.setNombreProyecto("Proyecto Test");

        InsumoModel insumo = new InsumoModel();
        insumo.setIdInsumos(1);
        insumo.setNombreInsumo("Insumo Test");

        inventario.setProyecto(proyecto);
        inventario.setInsumo(insumo);

        // Mock del servicio
        when(iProyectRepository.findById(1)).thenReturn(Optional.of(proyecto));
        when(iInsumoRepository.findById(1)).thenReturn(Optional.of(insumo));
        when(inventarioInicialService.saveInventarioInicial(any(InventarioInicialModel.class))).thenReturn(inventario);

        // JSON Request
        String jsonRequest = objectMapper.writeValueAsString(inventario);

        mockMvc.perform(post("/api/v1/InventarioInicial")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(inventarioInicialService, times(1)).saveInventarioInicial(any(InventarioInicialModel.class));
    }

    @Test
    void getInventarioInicialById_ValidId() throws Exception {
        when(inventarioInicialService.getInventarioInicialById(1)).thenReturn(Optional.of(inventario));

        mockMvc.perform(get("/api/v1/InventarioInicial/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(100))
                .andExpect(jsonPath("$.precio").value(50));

        verify(inventarioInicialService, times(1)).getInventarioInicialById(1);
    }

    @Test
    void getInventarioInicialById_NotFound() throws Exception {
        when(inventarioInicialService.getInventarioInicialById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/InventarioInicial/1"))
                .andExpect(status().isNotFound());

        verify(inventarioInicialService, times(1)).getInventarioInicialById(1);
    }

    @Test
    void getAllInventarioInicials() throws Exception {
        List<InventarioInicialModel> inventarios = Arrays.asList(inventario);
        when(inventarioInicialService.getInventarioInicials()).thenReturn(inventarios);

        mockMvc.perform(get("/api/v1/InventarioInicial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidad").value(100))
                .andExpect(jsonPath("$[0].precio").value(50));

        verify(inventarioInicialService, times(1)).getInventarioInicials();
    }

    @Test
    void getInventarioInicialByProyecto() throws Exception {
        List<InventarioInicialModel> inventarios = Arrays.asList(inventario);
        when(inventarioInicialService.getInventarioInicialByProyecto(1)).thenReturn(inventarios);

        mockMvc.perform(get("/api/v1/InventarioInicial/proyecto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidad").value(100))
                .andExpect(jsonPath("$[0].unidad").value("Unidad A"));

        verify(inventarioInicialService, times(1)).getInventarioInicialByProyecto(1);
    }

    @Test
    void deleteInventarioInicial_ValidId() throws Exception {
        // Mock para devolver un inventario válido con ID 1
        when(inventarioInicialService.getInventarioInicialById(1)).thenReturn(Optional.of(inventario));
        doNothing().when(inventarioInicialService).deleteInventarioInicial(1);

        // Realiza la solicitud DELETE con ID 1
        mockMvc.perform(delete("/api/v1/InventarioInicial/1"))
                .andExpect(status().isOk());

        // Verifica que los métodos mock fueron llamados con los argumentos correctos
        verify(inventarioInicialService, times(1)).getInventarioInicialById(1);
        verify(inventarioInicialService, times(1)).deleteInventarioInicial(1);
    }

    @Test
    void deleteInventarioInicial_NotFound() throws Exception {
        when(inventarioInicialService.getInventarioInicialById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/InventarioInicial/1"))
                .andExpect(status().isNotFound());

        verify(inventarioInicialService, times(1)).getInventarioInicialById(1);
    }
}