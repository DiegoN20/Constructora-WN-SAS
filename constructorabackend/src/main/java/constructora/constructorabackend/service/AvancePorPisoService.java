package constructora.constructorabackend.service;

import constructora.constructorabackend.model.AvancePorPisoModel;
import constructora.constructorabackend.repository.IAvancePorPisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvancePorPisoService implements IAvancePorPisoService{

    @Autowired
    private IAvancePorPisoRepository iAvancePorPisoRepository;

    @Override
    public AvancePorPisoModel saveAvancePorPiso(AvancePorPisoModel avancePorPisoModel) {
        return iAvancePorPisoRepository.save(avancePorPisoModel);
    }

    @Override
    public AvancePorPisoModel updateAvancePorPiso(AvancePorPisoModel avancePorPisoModel) {
        return iAvancePorPisoRepository.save(avancePorPisoModel);
    }

    @Override
    public List<AvancePorPisoModel> getAvancePorPisos() {
        return iAvancePorPisoRepository.findAll();
    }

    @Override
    public Optional<AvancePorPisoModel> getAvancePorPisoById(Integer idAvancePorPiso) {
        return iAvancePorPisoRepository.findById(idAvancePorPiso);
    }

    @Override
    public void deleteAvancePorPiso(Integer id) {
        iAvancePorPisoRepository.deleteById(id);
    }

}