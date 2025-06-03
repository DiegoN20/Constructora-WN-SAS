package constructora.constructorabackend.service;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.model.StockModel;

import java.util.List;
import java.util.Optional;

public interface IStockService {
    StockModel saveStock(StockModel StockModel);

    StockModel updateStock(StockModel StockModel);

    Optional<StockModel> findByProyectoAndInsumo(ProyectModel proyect, InsumoModel insumo);

    List<StockModel> getStocks();

    Optional<StockModel> getStockById(Integer idStock);

    List<StockModel> getStockByProyecto(Integer idProyecto);

    void deleteStock(Integer idStock);
}
