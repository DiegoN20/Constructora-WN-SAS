package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.model.ServicioExternoModel;
import constructora.constructorabackend.model.ServicioExternoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServicioExternoRepository extends JpaRepository<ServicioExternoModel, ServicioExternoId> {
    @Query("SELECT s FROM ServicioExternoModel s WHERE s.proveedor.idProveedores = :idProveedor")
    List<ServicioExternoModel> findByProveedorId(@Param("idProveedor") Integer idProveedor);

    @Query("SELECT s FROM ServicioExternoModel s WHERE s.proyecto.idProyectos = :idProyecto")
    List<ServicioExternoModel> findByProyectoId(@Param("idProyecto") Integer idProyecto);
}
