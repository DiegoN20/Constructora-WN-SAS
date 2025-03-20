package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.StockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStockRepository extends JpaRepository<StockModel, Integer> {
}
