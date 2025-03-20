package constructora.constructorabackend.service;

import constructora.constructorabackend.model.PersonaEncargadaModel;

import java.util.List;
import java.util.Optional;

public interface IPersonaEncargadaService {
    PersonaEncargadaModel savePersonaEncargada(PersonaEncargadaModel personaEncargadaModel);

    PersonaEncargadaModel updatePersonaEncargada(PersonaEncargadaModel personaEncargadaModel);

    List<PersonaEncargadaModel> getPersonaEncargadas();

    Optional<PersonaEncargadaModel> getPersonaEncargadaById(Integer id);

    void deletePersonaEncargada(Integer id);
}
