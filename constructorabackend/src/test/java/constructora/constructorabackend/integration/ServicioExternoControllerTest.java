package constructora.constructorabackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import constructora.constructorabackend.controller.ServicioExternoController;
import constructora.constructorabackend.dto.ServicioExternoDTO;
import constructora.constructorabackend.model.ProveedorModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.model.ServicioExternoModel;
import constructora.constructorabackend.repository.IMaestroRepository;
import constructora.constructorabackend.repository.IProveedorRepository;
import constructora.constructorabackend.repository.IProyectRepository;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import constructora.constructorabackend.service.ServicioExternoService;
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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ServicioExternoController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad
class ServicioExternoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtilService jwtUtilService;

    @MockBean
    private IProyectRepository proyectRepository;

    @MockBean
    private IProveedorRepository proveedorRepository;

    @MockBean
    private ServicioExternoService servicioExternoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ServicioExternoModel servicio;

    @BeforeEach
    void setUp() {
        ProveedorModel proveedor = new ProveedorModel();
        proveedor.setIdProveedores(1);
        proveedor.setNombreProveedor("Proveedor A");

        ProyectModel proyecto = new ProyectModel();
        proyecto.setIdProyectos(2);
        proyecto.setNombreProyecto("Proyecto A");

        servicio = new ServicioExternoModel();
        servicio.setProveedor(proveedor); // Configurar correctamente el proveedor
        servicio.setProyecto(proyecto);  // Configurar correctamente el proyecto
        servicio.setDescripcionServicio("Descripción de prueba");
        servicio.setCosto(5000);
        servicio.setFechaInicio(LocalDate.of(2023, 1, 1));
        servicio.setFechaFin(LocalDate.of(2023, 12, 31));
        servicio.setPersonaEncargada("Persona Encargada");
        servicio.setTelefono(123456789);
    }

    @Test
    void saveServicioExterno_ValidData() throws Exception {
        when(servicioExternoService.saveServicioExterno(any(ServicioExternoModel.class))).thenReturn(servicio);

        String jsonRequest = objectMapper.writeValueAsString(servicio);

        mockMvc.perform(post("/api/v1/ServicioExternos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());

        verify(servicioExternoService, times(1)).saveServicioExterno(any(ServicioExternoModel.class));
        verify(proveedorRepository, times(1)).findById(1);
    }

    @Test
    void getServicioExternoById_ValidId() throws Exception {
        when(servicioExternoService.getServicioExternoById(1, 2)).thenReturn(Optional.of(servicio));

        mockMvc.perform(get("/api/v1/ServicioExternos/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Descripción de prueba"))
                .andExpect(jsonPath("$.costo").value(5000))
                .andExpect(jsonPath("$.personaEncargada").value("Persona Encargada"));

        verify(servicioExternoService, times(1)).getServicioExternoById(1, 2);
    }

    @Test
    void getServicioExternoById_NotFound() throws Exception {
        when(servicioExternoService.getServicioExternoById(1, 2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/ServicioExternos/1/2"))
                .andExpect(status().isNotFound());

        verify(servicioExternoService, times(1)).getServicioExternoById(1, 2);
    }

    @Test
    void getServiciosByProyecto() throws Exception {
        when(servicioExternoService.getServicioExternoByProyecto(2)).thenReturn(Arrays.asList(servicio));

        mockMvc.perform(get("/api/v1/ServicioExternos/proyecto/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Descripción de prueba"))
                .andExpect(jsonPath("$[0].personaEncargada").value("Persona Encargada"));

        verify(servicioExternoService, times(1)).getServicioExternoByProyecto(2);
    }

    @Test
    void getServiciosByProveedor() throws Exception {
        when(servicioExternoService.getServicioExternoByProveedor(1)).thenReturn(Arrays.asList(servicio));

        mockMvc.perform(get("/api/v1/ServicioExternos/proveedor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Descripción de prueba"))
                .andExpect(jsonPath("$[0].telefono").value(123456789));

        verify(servicioExternoService, times(1)).getServicioExternoByProveedor(1);
    }

    @Test
    void deleteServicioExterno_ValidId() throws Exception {
        when(servicioExternoService.getServicioExternoById(1, 2)).thenReturn(Optional.of(servicio));
        doNothing().when(servicioExternoService).deleteServicioExterno(1, 2);

        mockMvc.perform(delete("/api/v1/ServicioExternos/1/2"))
                .andExpect(status().isOk());

        verify(servicioExternoService, times(1)).deleteServicioExterno(1, 2);
    }

    @Test
    void deleteServicioExterno_NotFound() throws Exception {
        when(servicioExternoService.getServicioExternoById(1, 2)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/ServicioExternos/1/2"))
                .andExpect(status().isNotFound());

        verify(servicioExternoService, times(1)).getServicioExternoById(1, 2);
    }
}