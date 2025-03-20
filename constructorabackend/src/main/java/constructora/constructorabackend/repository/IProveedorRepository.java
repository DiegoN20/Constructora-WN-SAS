package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.ProveedorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorRepository extends JpaRepository<ProveedorModel, Integer> {
    
}
