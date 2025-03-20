package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.PersonaEncargadaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonaEncargadaRepository extends JpaRepository<PersonaEncargadaModel, Integer> {

}
