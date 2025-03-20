package constructora.constructorabackend.service;

import constructora.constructorabackend.model.AvancePorPisoModel;

import java.util.List;
import java.util.Optional;

public interface IAvancePorPisoService {
    AvancePorPisoModel saveAvancePorPiso(AvancePorPisoModel avancePorPisoModel);

    AvancePorPisoModel updateAvancePorPiso(AvancePorPisoModel avancePorPisoModel);

    List<AvancePorPisoModel> getAvancePorPisos();

    Optional<AvancePorPisoModel> getAvancePorPisoById(Integer idAvancePorPiso);


    void deleteAvancePorPiso(Integer idAvancePorPiso);
}
