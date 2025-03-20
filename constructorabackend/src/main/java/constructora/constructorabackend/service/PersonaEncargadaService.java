package constructora.constructorabackend.service;

import constructora.constructorabackend.model.PersonaEncargadaModel;
import constructora.constructorabackend.repository.IPersonaEncargadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaEncargadaService implements IPersonaEncargadaService{

    @Autowired
    private IPersonaEncargadaRepository iPersonaEncargadaRepository;

    @Override
    public PersonaEncargadaModel savePersonaEncargada(PersonaEncargadaModel personaEncargadaModel) {
        return iPersonaEncargadaRepository.save(personaEncargadaModel);
    }

    @Override
    public PersonaEncargadaModel updatePersonaEncargada(PersonaEncargadaModel personaEncargadaModel) {
        return iPersonaEncargadaRepository.save(personaEncargadaModel);
    }

    @Override
    public List<PersonaEncargadaModel> getPersonaEncargadas() {
        return iPersonaEncargadaRepository.findAll();
    }

    @Override
    public Optional<PersonaEncargadaModel> getPersonaEncargadaById(Integer id) {
        return iPersonaEncargadaRepository.findById(id);
    }

    @Override
    public void deletePersonaEncargada(Integer id) {
        iPersonaEncargadaRepository.deleteById(id);
    }
}
