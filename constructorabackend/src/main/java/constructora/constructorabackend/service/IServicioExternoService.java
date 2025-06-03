package constructora.constructorabackend.service;

import constructora.constructorabackend.model.ServicioExternoModel;

import java.util.List;
import java.util.Optional;

public interface IServicioExternoService {
    ServicioExternoModel saveServicioExterno(ServicioExternoModel servicioExternoModel);

    ServicioExternoModel updateServicioExterno(ServicioExternoModel servicioExternoModel);

    List<ServicioExternoModel> getServicioExternos();

    Optional<ServicioExternoModel> getServicioExternoById(Integer idProveedor, Integer idProyecto);

    List<ServicioExternoModel> getServicioExternoByProyecto(Integer idProyecto);

    List<ServicioExternoModel> getServicioExternoByProveedor(Integer idProveedor);

    void deleteServicioExterno(Integer idMaestro, Integer idProyecto);
}