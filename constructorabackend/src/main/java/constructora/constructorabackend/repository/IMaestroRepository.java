package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.MaestroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMaestroRepository extends JpaRepository<MaestroModel, Integer> {

}
