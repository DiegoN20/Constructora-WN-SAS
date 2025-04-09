package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.MaestroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMaestroRepository extends JpaRepository<MaestroModel, Integer> {
    List<MaestroModel> findByEstadoMaestro(MaestroModel.EstadoMaestro estadoMaestro);
}
