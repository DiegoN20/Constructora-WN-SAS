package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.ProyectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProyectRepository extends JpaRepository <ProyectModel, Integer> {

}
