package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.AvancePorPisoModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.model.ProyectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAvancePorPisoRepository extends JpaRepository<AvancePorPisoModel, Integer> {
    Optional<AvancePorPisoModel> findByProyectoAndInsumo(ProyectModel proyecto, InsumoModel insumo);
    @Query("SELECT a FROM AvancePorPisoModel a WHERE a.proyecto.idProyectos = :idProyecto")
    List<AvancePorPisoModel> findByProyectoId(@Param("idProyecto") Integer idProyecto);
}
