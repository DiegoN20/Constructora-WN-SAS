package constructora.constructorabackend.service;

import constructora.constructorabackend.model.ProveedorModel;
import constructora.constructorabackend.repository.IProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements IProveedorService{

    @Autowired
    private IProveedorRepository iProveedorRepository;

    @Override
    public ProveedorModel saveProveedor(ProveedorModel proveedorModel) {
        return iProveedorRepository.save(proveedorModel);
    }

    @Override
    public ProveedorModel updateProveedor(ProveedorModel proveedorModel) {
        return iProveedorRepository.save(proveedorModel);
    }

    @Override
    public List<ProveedorModel> getProveedors() {
        return iProveedorRepository.findAll();
    }

    @Override
    public Optional<ProveedorModel> getProveedorById(Integer id) {
        return iProveedorRepository.findById(id);
    }

    @Override
    public void deleteProveedor(Integer id) {
        iProveedorRepository.deleteById(id);
    }
}
