package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.model.AsignacionMaestroId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAsignacionMaestroRepository extends JpaRepository<AsignacionMaestroModel, AsignacionMaestroId> {
    // Consulta personalizada para buscar por idProyecto
    @Query("SELECT a FROM AsignacionMaestroModel a WHERE a.proyecto.idProyectos = :idProyecto")
    List<AsignacionMaestroModel> findByProyectoId(@Param("idProyecto") Integer idProyecto);

    // Consulta personalizada para buscar por idMaestro
    @Query("SELECT a FROM AsignacionMaestroModel a WHERE a.maestro.id_maestros_de_obra = :idMaestro")
    List<AsignacionMaestroModel> findByMaestroId(@Param("idMaestro") Integer idMaestro);
}

