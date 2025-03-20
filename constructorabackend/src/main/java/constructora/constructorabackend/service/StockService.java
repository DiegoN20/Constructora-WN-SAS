package constructora.constructorabackend.service;

import constructora.constructorabackend.model.StockModel;
import constructora.constructorabackend.repository.IStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService implements IStockService{

    @Autowired
    private IStockRepository iStockRepository;

    @Override
    public StockModel saveStock(StockModel StockModel) {
        return iStockRepository.save(StockModel);
    }

    @Override
    public StockModel updateStock(StockModel StockModel) {
        return iStockRepository.save(StockModel);
    }

    @Override
    public List<StockModel> getStocks() {
        return iStockRepository.findAll();
    }

    @Override
    public Optional<StockModel> getStockById(Integer idStock) {
        return iStockRepository.findById(idStock);
    }

    @Override
    public void deleteStock(Integer id) {
        iStockRepository.deleteById(id);
    }

}