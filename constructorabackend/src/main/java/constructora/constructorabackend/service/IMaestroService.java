package constructora.constructorabackend.service;

import constructora.constructorabackend.model.MaestroModel;

import java.util.List;
import java.util.Optional;

public interface IMaestroService {
    MaestroModel saveMaestro(MaestroModel MaestroModel);

    MaestroModel updateMaestro(MaestroModel MaestroModel);

    List<MaestroModel> getMaestros();

    Optional<MaestroModel> getMaestroById(Integer id);

    void deleteMaestro(Integer id);
}
