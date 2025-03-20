package constructora.constructorabackend.service;

import constructora.constructorabackend.model.StockModel;

import java.util.List;
import java.util.Optional;

public interface IStockService {
    StockModel saveStock(StockModel StockModel);

    StockModel updateStock(StockModel StockModel);

    List<StockModel> getStocks();

    Optional<StockModel> getStockById(Integer idStock);


    void deleteStock(Integer idStock);
}
