package constructora.constructorabackend.service;

import constructora.constructorabackend.model.MaestroModel;
import constructora.constructorabackend.repository.IMaestroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaestroService implements IMaestroService{

    @Autowired
    private IMaestroRepository iMaestroRepository;

    @Override
    public MaestroModel saveMaestro(MaestroModel maestroModel) {
        validateMaestro(maestroModel);
        return iMaestroRepository.save(maestroModel);
    }

    @Override
    public MaestroModel updateMaestro(MaestroModel maestroModel) {
        validateMaestro(maestroModel);
        return iMaestroRepository.save(maestroModel);
    }

    @Override
    public List<MaestroModel> getMaestros() {
        return iMaestroRepository.findAll();
    }

    @Override
    public Optional<MaestroModel> getMaestroById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID no valido");
        }
        return iMaestroRepository.findById(id);
    }

    @Override
    public void deleteMaestro(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID no valido");
        }
        iMaestroRepository.deleteById(id);
    }

    @Override
    public List<MaestroModel> getMaestrosDisponibles() {
        return iMaestroRepository.findByEstadoMaestro(MaestroModel.EstadoMaestro.Disponible); // Utiliza el repositorio para buscar por estado
    }

    private void validateMaestro(MaestroModel maestro) {
        if (maestro.getCedula() <= 0 || maestro.getTelefono() <= 0 || maestro.getSalario() <= 0 ||
                maestro.getNombre() == null || maestro.getNombre().isEmpty() ||
                maestro.getApellido() == null || maestro.getApellido().isEmpty()) {
            throw new IllegalArgumentException("Datos inválidos");
        }
    }
}
