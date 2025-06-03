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
        validateProveedor(proveedorModel);
        return iProveedorRepository.save(proveedorModel);
    }

    @Override
    public ProveedorModel updateProveedor(ProveedorModel proveedorModel) {

        if (!iProveedorRepository.findById(proveedorModel.getIdProveedores()).isPresent()) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }
        validateProveedor(proveedorModel);

        return iProveedorRepository.save(proveedorModel);
    }

    @Override
    public List<ProveedorModel> getProveedors() {
        return iProveedorRepository.findAll();
    }

    @Override
    public Optional<ProveedorModel> getProveedorById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID no válido");
        }
        return iProveedorRepository.findById(id);
    }

    @Override
    public void deleteProveedor(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID no válido");
        }
        iProveedorRepository.deleteById(id);
    }


    private void validateProveedor(ProveedorModel proveedor) {
        if (proveedor.getNombreProveedor() == null || proveedor.getNombreProveedor().isEmpty() ||
                proveedor.getTipoServicio() == null || proveedor.getTipoServicio().isEmpty() ||
                proveedor.getCorreo() == null || !proveedor.getCorreo().contains("@") ||
                proveedor.getTelefono() <= 0) {
            throw new IllegalArgumentException("Datos inválidos");
        }
    }
}
