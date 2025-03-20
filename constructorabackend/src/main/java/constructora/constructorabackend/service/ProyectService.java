package constructora.constructorabackend.service;

import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.repository.IProyectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectService implements IProyectService {
    @Autowired
    private IProyectRepository iProyectRepository;

    @Override
    public ProyectModel saveProyecto(ProyectModel proyectModel) {
        return iProyectRepository.save(proyectModel);
    }

    @Override
    public ProyectModel updateProyecto(ProyectModel proyectModel) {
        return iProyectRepository.save(proyectModel);
    }

    @Override
    public List<ProyectModel> getProyectos() {
        return iProyectRepository.findAll();
    }

    @Override
    public Optional<ProyectModel> getProyectoById(Integer id) {
        return iProyectRepository.findById(id);
    }

    @Override
    public void deleteProyecto(Integer id) {
        iProyectRepository.deleteById(id);
    }
}
