package constructora.constructorabackend.service;

import constructora.constructorabackend.model.ProveedorModel;

import java.util.List;
import java.util.Optional;

public interface IProveedorService {
    ProveedorModel saveProveedor(ProveedorModel proveedorModel);

    ProveedorModel updateProveedor(ProveedorModel proveedorModel);

    List<ProveedorModel> getProveedors();

    Optional<ProveedorModel> getProveedorById(Integer id);

    void deleteProveedor(Integer id);
}