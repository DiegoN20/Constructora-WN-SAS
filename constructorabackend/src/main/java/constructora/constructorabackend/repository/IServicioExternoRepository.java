package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.ServicioExternoModel;
import constructora.constructorabackend.model.ServicioExternoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicioExternoRepository extends JpaRepository<ServicioExternoModel, ServicioExternoId> {
}
