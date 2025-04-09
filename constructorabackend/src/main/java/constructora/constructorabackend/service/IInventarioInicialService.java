package constructora.constructorabackend.service;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.model.ProyectModel;

import java.util.List;
import java.util.Optional;

public interface IInventarioInicialService {
    InventarioInicialModel saveInventarioInicial(InventarioInicialModel inventarioInicialModel);

    InventarioInicialModel updateInventarioInicial(InventarioInicialModel inventarioInicialModel);

    Optional<InventarioInicialModel> findByProyectoAndInsumo(ProyectModel proyecto, InsumoModel insumo);

    List<InventarioInicialModel> getInventarioInicials();

    Optional<InventarioInicialModel> getInventarioInicialById(Integer idInventarioInicial);

    List<InventarioInicialModel> getInventarioInicialByProyecto(Integer idProyecto);

    void deleteInventarioInicial(Integer idInventarioInicial);
}

