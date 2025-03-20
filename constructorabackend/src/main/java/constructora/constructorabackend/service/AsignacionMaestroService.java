package constructora.constructorabackend.service;

import constructora.constructorabackend.model.AsignacionMaestroId;
import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.repository.IAsignacionMaestroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionMaestroService implements IAsignacionMaestroService{

    @Autowired
    private IAsignacionMaestroRepository iAsignacionMaestroRepository;

    @Override
    public AsignacionMaestroModel saveAsignacionMaestro(AsignacionMaestroModel asignacionMaestroModel) {
        return iAsignacionMaestroRepository.save(asignacionMaestroModel);
    }

    @Override
    public AsignacionMaestroModel updateAsignacionMaestro(AsignacionMaestroModel asignacionMaestroModel) {
        return iAsignacionMaestroRepository.save(asignacionMaestroModel);
    }

    @Override
    public List<AsignacionMaestroModel> getAsignacionMaestros() {
        return iAsignacionMaestroRepository.findAll();
    }

    @Override
    public Optional<AsignacionMaestroModel> getAsignacionMaestroById(Integer idMaestro, Integer idProyecto) {
        AsignacionMaestroId id = new AsignacionMaestroId();
        id.setIdMaestro(idMaestro);
        id.setIdProyecto(idProyecto);

        return iAsignacionMaestroRepository.findById(id);
    }

    @Override
    public void deleteAsignacionMaestro(Integer idMaestro, Integer idProyecto) {
        AsignacionMaestroId id = new AsignacionMaestroId();
        id.setIdMaestro(idMaestro);
        id.setIdProyecto(idProyecto);

        iAsignacionMaestroRepository.deleteById(id);
    }

}
