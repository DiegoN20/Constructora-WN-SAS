package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.model.AsignacionMaestroId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAsignacionMaestroRepository extends JpaRepository<AsignacionMaestroModel, AsignacionMaestroId> {
}
