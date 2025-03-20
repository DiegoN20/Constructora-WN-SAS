package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.AvancePorPisoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAvancePorPisoRepository extends JpaRepository<AvancePorPisoModel, Integer> {
}
