package constructora.constructorabackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import constructora.constructorabackend.controller.StockController;
import constructora.constructorabackend.model.StockModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import constructora.constructorabackend.repository.IProyectRepository;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import constructora.constructorabackend.service.StockService;
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

@WebMvcTest(controllers = StockController.class)
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtilService jwtUtilService;

    @MockBean
    private StockService stockService;

    @MockBean
    private IProyectRepository iProyectRepository;

    @MockBean
    private IInsumoRepository iInsumoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private StockModel stock;

    @BeforeEach
    void setUp() {
        ProyectModel proyecto = new ProyectModel();
        proyecto.setIdProyectos(1);
        proyecto.setNombreProyecto("Proyecto Test");

        InsumoModel insumo = new InsumoModel();
        insumo.setIdInsumos(1);
        insumo.setNombreInsumo("Insumo Test");

        stock = new StockModel();
        stock.setIdStock(1);
        stock.setProyecto(proyecto);
        stock.setInsumo(insumo);
        stock.setCantidadTotal(100); // Configura el valor correcto aquí
        stock.setCantidadInvertida(50);
        stock.setCantidadRestante(50);
    }

    @Test
    void saveStock_ValidData() throws Exception {
        ProyectModel proyecto = new ProyectModel();
        proyecto.setIdProyectos(1);
        proyecto.setNombreProyecto("Proyecto Test");

        InsumoModel insumo = new InsumoModel();
        insumo.setIdInsumos(1);
        insumo.setNombreInsumo("Insumo Test");

        stock.setProyecto(proyecto);
        stock.setInsumo(insumo);

        when(iProyectRepository.findById(1)).thenReturn(Optional.of(proyecto));
        when(iInsumoRepository.findById(1)).thenReturn(Optional.of(insumo));
        when(stockService.saveStock(any(StockModel.class))).thenReturn(stock);

        String jsonRequest = objectMapper.writeValueAsString(stock);

        mockMvc.perform(post("/api/v1/Stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(stockService, times(1)).saveStock(any(StockModel.class));
    }

    @Test
    void getAllStocks() throws Exception {
        List<StockModel> stocks = Arrays.asList(stock);
        when(stockService.getStocks()).thenReturn(stocks);

        mockMvc.perform(get("/api/v1/Stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidadTotal").value(100)) // Verifica que el valor esperado sea 100
                .andExpect(jsonPath("$[0].cantidadRestante").value(50));

        verify(stockService, times(1)).getStocks();
    }

    @Test
    void getStockById_ValidId() throws Exception {
        when(stockService.getStockById(1)).thenReturn(Optional.of(stock));

        mockMvc.perform(get("/api/v1/Stocks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadTotal").value(100))
                .andExpect(jsonPath("$.cantidadRestante").value(50));

        verify(stockService, times(1)).getStockById(1);
    }

    @Test
    void getStockById_NotFound() throws Exception {
        when(stockService.getStockById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/Stocks/1"))
                .andExpect(status().isNotFound());

        verify(stockService, times(1)).getStockById(1);
    }

    @Test
    void getStockByProyecto() throws Exception {
        List<StockModel> stocks = Arrays.asList(stock);
        when(stockService.getStockByProyecto(1)).thenReturn(stocks);

        mockMvc.perform(get("/api/v1/Stocks/proyecto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidadTotal").value(100))
                .andExpect(jsonPath("$[0].cantidadRestante").value(50));

        verify(stockService, times(1)).getStockByProyecto(1);
    }

    @Test
    void deleteStock_ValidId() throws Exception {
        when(stockService.getStockById(1)).thenReturn(Optional.of(stock));
        doNothing().when(stockService).deleteStock(1);

        mockMvc.perform(delete("/api/v1/Stocks/1"))
                .andExpect(status().isOk());

        verify(stockService, times(1)).deleteStock(1);
    }

    @Test
    void deleteStock_NotFound() throws Exception {
        when(stockService.getStockById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/Stocks/1"))
                .andExpect(status().isNotFound());

        verify(stockService, times(1)).getStockById(1);
        verify(stockService, never()).deleteStock(anyInt());
    }
}