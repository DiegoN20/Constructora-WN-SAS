package constructora.constructorabackend.service;

import constructora.constructorabackend.model.InsumoModel;

import java.util.List;
import java.util.Optional;

public interface IInsumoService {
    InsumoModel saveInsumo(InsumoModel insumoModel);

    InsumoModel updateInsumo(InsumoModel insumoModel);

    List<InsumoModel> getInsumos();

    Optional<InsumoModel> getInsumoById(Integer id);

    void deleteInsumo(Integer id);
}