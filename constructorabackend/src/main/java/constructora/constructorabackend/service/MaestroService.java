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
    public MaestroModel saveMaestro(MaestroModel MaestroModel) {
        return iMaestroRepository.save(MaestroModel);
    }

    @Override
    public MaestroModel updateMaestro(MaestroModel MaestroModel) {
        return iMaestroRepository.save(MaestroModel);
    }

    @Override
    public List<MaestroModel> getMaestros() {
        return iMaestroRepository.findAll();
    }

    @Override
    public Optional<MaestroModel> getMaestroById(Integer id) {
        return iMaestroRepository.findById(id);
    }

    @Override
    public void deleteMaestro(Integer id) {
        iMaestroRepository.deleteById(id);
    }
}
