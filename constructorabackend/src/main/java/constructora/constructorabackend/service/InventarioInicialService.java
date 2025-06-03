package constructora.constructorabackend.service;

import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.model.ProyectModel;
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
    public Optional<InventarioInicialModel> findByProyectoAndInsumo(ProyectModel proyecto, InsumoModel insumo) {
        return iInventarioInicialRepository.findByProyectoAndInsumo(proyecto, insumo);
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
    public List<InventarioInicialModel> getInventarioInicialByProyecto(Integer idProyecto) {
        return iInventarioInicialRepository.findByProyectoId(idProyecto);
    }

    @Override
    public void deleteInventarioInicial(Integer id) {
        iInventarioInicialRepository.deleteById(id);
    }
}
