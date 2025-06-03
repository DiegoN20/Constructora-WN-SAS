package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.model.StockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IStockRepository extends JpaRepository<StockModel, Integer> {
    Optional<StockModel> findByProyectoAndInsumo(ProyectModel proyect, InsumoModel insumo);
    @Query("SELECT a FROM StockModel a WHERE a.proyecto.idProyectos = :idProyecto")
    List<StockModel> findByProyectoId(@Param("idProyecto") Integer idProyecto);
}
