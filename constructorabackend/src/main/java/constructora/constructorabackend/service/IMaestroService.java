package constructora.constructorabackend.service;

import constructora.constructorabackend.model.MaestroModel;

import java.util.List;
import java.util.Optional;

public interface IMaestroService {

    MaestroModel saveMaestro(MaestroModel maestroModel);

    MaestroModel updateMaestro(MaestroModel maestroModel);

    List<MaestroModel> getMaestros();

    Optional<MaestroModel> getMaestroById(Integer id);

    void deleteMaestro(Integer id);

    List<MaestroModel> getMaestrosDisponibles();
}
