package constructora.constructorabackend.service;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsumoService implements IInsumoService{

    @Autowired
    private IInsumoRepository iInsumoRepository;

    @Override
    public InsumoModel saveInsumo(InsumoModel insumoModel) {
        validateInsumo(insumoModel);
        return iInsumoRepository.save(insumoModel);
    }

    @Override
    public InsumoModel updateInsumo(InsumoModel insumoModel) {
        if (!iInsumoRepository.existsById(insumoModel.getIdInsumos())) {
            throw new IllegalArgumentException("Insumo no encontrado");
        }
        validateInsumo(insumoModel);
        return iInsumoRepository.save(insumoModel);
    }

    @Override
    public List<InsumoModel> getInsumos() {
        return iInsumoRepository.findAll();
    }

    @Override
    public Optional<InsumoModel> getInsumoById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID no válido");
        }
        return iInsumoRepository.findById(id);
    }

    @Override
    public void deleteInsumo(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID no válido");
        }
        if (!iInsumoRepository.existsById(id)) {
            throw new IllegalArgumentException("Insumo no encontrado");
        }
        iInsumoRepository.deleteById(id);
    }

    private void validateInsumo(InsumoModel insumoModel) {
        // Validar que el nombre del insumo no sea nulo o vacío
        if (insumoModel.getNombreInsumo() == null || insumoModel.getNombreInsumo().isEmpty()) {
            throw new IllegalArgumentException("El nombre del insumo no puede estar vacío");
        }

        // Validar que el nombre tenga una longitud mínima (por ejemplo, 3 caracteres)
        if (insumoModel.getNombreInsumo().length() < 3) {
            throw new IllegalArgumentException("El nombre del insumo debe tener al menos 3 caracteres");
        }

        // Validar que el nombre no exceda una longitud máxima (opcional si necesario)
        if (insumoModel.getNombreInsumo().length() > 45) {
            throw new IllegalArgumentException("El nombre del insumo no puede tener más de 45 caracteres");
        }

        // Validar que la descripción no exceda el límite de caracteres
        if (insumoModel.getDescripcion() != null && insumoModel.getDescripcion().length() > 45) {
            throw new IllegalArgumentException("La descripción no puede tener más de 45 caracteres");
        }

        // Validar que la descripción no sea extremadamente corta (si aplica)
        if (insumoModel.getDescripcion() != null && insumoModel.getDescripcion().length() < 5) {
            throw new IllegalArgumentException("La descripción debe tener al menos 5 caracteres");
        }

        // Validar que el tipo de insumo no sea nulo
        if (insumoModel.getTipo() == null) {
            throw new IllegalArgumentException("El tipo de insumo no puede ser nulo");
        }
    }
}
