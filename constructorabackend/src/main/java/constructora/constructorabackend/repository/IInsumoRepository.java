package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.InsumoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInsumoRepository extends JpaRepository<InsumoModel, Integer> {

}
