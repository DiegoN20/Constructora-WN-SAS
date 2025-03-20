package constructora.constructorabackend.service;

import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.repository.IInventarioInicialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioInicialService implements IInventarioInicialService{
    @Autowired
    private IInventarioInicialRepository iInventarioInicialRepository;

    @Override
    public InventarioInicialModel saveInventarioInicial(InventarioInicialModel inventarioInicialModel) {
        return iInventarioInicialRepository.save(inventarioInicialModel);
    }

    @Override
    public InventarioInicialModel updateInventarioInicial(InventarioInicialModel inventarioInicialModel) {
        return iInventarioInicialRepository.save(inventarioInicialModel);
    }

    @Override
    public List<InventarioInicialModel> getInventarioInicials() {
        return iInventarioInicialRepository.findAll();
    }

    @Override
    public Optional<InventarioInicialModel> getInventarioInicialById(Integer id) {
        return iInventarioInicialRepository.findById(id);
    }

    @Override
    public void deleteInventarioInicial(Integer id) {
        iInventarioInicialRepository.deleteById(id);
    }
}
