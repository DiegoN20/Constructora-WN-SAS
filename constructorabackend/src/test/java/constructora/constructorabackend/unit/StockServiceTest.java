package constructora.constructorabackend.unit;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.model.StockModel;
import constructora.constructorabackend.repository.IStockRepository;
import constructora.constructorabackend.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private IStockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    private StockModel stock;
    private ProyectModel proyecto;
    private InsumoModel insumo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        proyecto = new ProyectModel();
        proyecto.setIdProyectos(1);
        proyecto.setNombreProyecto("Proyecto A");

        insumo = new InsumoModel();
        insumo.setIdInsumos(1);
        insumo.setNombreInsumo("Insumo A");

        stock = new StockModel();
        stock.setIdStock(1);
        stock.setProyecto(proyecto);
        stock.setInsumo(insumo);
        stock.setCantidadTotal(100);
        stock.setCantidadInvertida(50);
        stock.setCantidadRestante(50);
    }

    @Test
    void saveStock_ValidData() {
        when(stockRepository.save(stock)).thenReturn(stock);

        StockModel savedStock = stockService.saveStock(stock);

        assertNotNull(savedStock);
        assertEquals(stock.getIdStock(), savedStock.getIdStock());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void findByProyectoAndInsumo_ValidData() {
        when(stockRepository.findByProyectoAndInsumo(proyecto, insumo)).thenReturn(Optional.of(stock));

        Optional<StockModel> result = stockService.findByProyectoAndInsumo(proyecto, insumo);

        assertTrue(result.isPresent());
        assertEquals(stock, result.get());
        verify(stockRepository, times(1)).findByProyectoAndInsumo(proyecto, insumo);
    }

    @Test
    void getStockById_ValidId() {
        when(stockRepository.findById(1)).thenReturn(Optional.of(stock));

        Optional<StockModel> result = stockService.getStockById(1);

        assertTrue(result.isPresent());
        assertEquals(stock, result.get());
        verify(stockRepository, times(1)).findById(1);
    }

    @Test
    void getStockByProyecto_ValidId() {
        when(stockRepository.findByProyectoId(1)).thenReturn(Arrays.asList(stock));

        List<StockModel> stocks = stockService.getStockByProyecto(1);

        assertEquals(1, stocks.size());
        assertEquals(stock, stocks.get(0));
        verify(stockRepository, times(1)).findByProyectoId(1);
    }

    @Test
    void deleteStock_ValidId() {
        doNothing().when(stockRepository).deleteById(1);

        stockService.deleteStock(1);

        verify(stockRepository, times(1)).deleteById(1);
    }
}