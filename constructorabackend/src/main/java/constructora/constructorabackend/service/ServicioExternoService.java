package constructora.constructorabackend.service;

import constructora.constructorabackend.model.ServicioExternoId;
import constructora.constructorabackend.model.ServicioExternoModel;
import constructora.constructorabackend.repository.IServicioExternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioExternoService implements IServicioExternoService{

    @Autowired
    private IServicioExternoRepository iServicioExternoRepository;

    @Override
    public ServicioExternoModel saveServicioExterno(ServicioExternoModel servicioExternoModel) {
        return iServicioExternoRepository.save(servicioExternoModel);
    }

    @Override
    public ServicioExternoModel updateServicioExterno(ServicioExternoModel servicioExternoModel) {
        return iServicioExternoRepository.save(servicioExternoModel);
    }

    @Override
    public List<ServicioExternoModel> getServicioExternos() {
        return iServicioExternoRepository.findAll();
    }

    @Override
    public Optional<ServicioExternoModel> getServicioExternoById(Integer idProveedor, Integer idProyecto) {
        ServicioExternoId id = new ServicioExternoId();
        id.setProveedoresIdProveedores(idProveedor);
        id.setProyectosIdProyectos(idProyecto);

        return iServicioExternoRepository.findById(id);
    }

    @Override
    public List<ServicioExternoModel> getServicioExternoByProyecto(Integer idProyecto) {
        return iServicioExternoRepository.findByProyectoId(idProyecto);
    }

    @Override
    public List<ServicioExternoModel> getServicioExternoByProveedor(Integer idProveedor) {
        return iServicioExternoRepository.findByProveedorId(idProveedor);
    }

    @Override
    public void deleteServicioExterno(Integer idProveedor, Integer idProyecto) {
        ServicioExternoId id = new ServicioExternoId();
        id.setProveedoresIdProveedores(idProveedor);
        id.setProyectosIdProyectos(idProyecto);

        iServicioExternoRepository.deleteById(id);
    }

}
