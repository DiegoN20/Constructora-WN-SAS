package constructora.constructorabackend.service;

import constructora.constructorabackend.model.AvancePorPisoModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.model.ProyectModel;

import java.util.List;
import java.util.Optional;

public interface IAvancePorPisoService {
    AvancePorPisoModel saveAvancePorPiso(AvancePorPisoModel avancePorPisoModel);

    AvancePorPisoModel updateAvancePorPiso(AvancePorPisoModel avancePorPisoModel);

    Optional<AvancePorPisoModel> findByProyectoAndInsumo(ProyectModel proyecto, InsumoModel insumo);

    List<AvancePorPisoModel> getAvancePorPisos();

    Optional<AvancePorPisoModel> getAvancePorPisoById(Integer idAvancePorPiso);

    List<AvancePorPisoModel> getAvancePorPisoByProyecto(Integer idProyecto);

    void deleteAvancePorPiso(Integer idAvancePorPiso);
}
