package constructora.constructorabackend.service;

import constructora.constructorabackend.model.InventarioInicialModel;

import java.util.List;
import java.util.Optional;

public interface IInventarioInicialService {
    InventarioInicialModel saveInventarioInicial(InventarioInicialModel inventarioInicialModel);

    InventarioInicialModel updateInventarioInicial(InventarioInicialModel inventarioInicialModel);

    List<InventarioInicialModel> getInventarioInicials();

    Optional<InventarioInicialModel> getInventarioInicialById(Integer idInventarioInicial);

    void deleteInventarioInicial(Integer idInventarioInicial);
}

