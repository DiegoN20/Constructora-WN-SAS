package constructora.constructorabackend.service;

import constructora.constructorabackend.model.ProyectModel;

import java.util.List;
import java.util.Optional;

public interface IProyectService {
    ProyectModel saveProyecto(ProyectModel proyectModel);

    ProyectModel updateProyecto(ProyectModel proyectModel);

    List<ProyectModel> getProyectos();

    Optional<ProyectModel> getProyectoById(Integer id);

    void deleteProyecto(Integer id);
}
