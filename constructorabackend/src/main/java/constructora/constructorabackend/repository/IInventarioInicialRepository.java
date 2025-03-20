package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.InventarioInicialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventarioInicialRepository extends JpaRepository<InventarioInicialModel, Integer> {
}