package constructora.constructorabackend.service;

import constructora.constructorabackend.model.AsignacionMaestroModel;

import java.util.List;
import java.util.Optional;

public interface IAsignacionMaestroService {
    AsignacionMaestroModel saveAsignacionMaestro(AsignacionMaestroModel asignacionMaestroModel);

    AsignacionMaestroModel updateAsignacionMaestro(AsignacionMaestroModel asignacionMaestroModel);

    List<AsignacionMaestroModel> getAsignacionMaestros();

    Optional<AsignacionMaestroModel> getAsignacionMaestroById(Integer idMaestro, Integer idProyecto);

    List<AsignacionMaestroModel> getAsignacionesByProyecto(Integer idProyecto);

    List<AsignacionMaestroModel> getAsignacionesByMaestro(Integer idMaestro);

    void deleteAsignacionMaestro(Integer idMaestro, Integer idProyecto);
}