package constructora.constructorabackend.service;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsumoService implements IInsumoService{

    @Autowired
    private IInsumoRepository iInsumoRepository;

    @Override
    public InsumoModel saveInsumo(InsumoModel insumoModel) {
        return iInsumoRepository.save(insumoModel);
    }

    @Override
    public InsumoModel updateInsumo(InsumoModel insumoModel) {
        return iInsumoRepository.save(insumoModel);
    }

    @Override
    public List<InsumoModel> getInsumos() {
        return iInsumoRepository.findAll();
    }

    @Override
    public Optional<InsumoModel> getInsumoById(Integer id) {
        return iInsumoRepository.findById(id);
    }

    @Override
    public void deleteInsumo(Integer id) {
        iInsumoRepository.deleteById(id);
    }
}
