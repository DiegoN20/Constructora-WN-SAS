package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.AsignacionMaestroDTO;
import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.model.MaestroModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.repository.IMaestroRepository;
import constructora.constructorabackend.repository.IProyectRepository;
import constructora.constructorabackend.service.AsignacionMaestroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/AsignacionMaestros")
public class AsignacionMaestroController {

    @Autowired
    private IProyectRepository iProyectRepository;

    @Autowired
    private IMaestroRepository iMaestroRepository;

    @Autowired
    private AsignacionMaestroService asignacionMaestroService;

    @PostMapping
    public ResponseEntity<?> saveAsignacionMaestro(@RequestBody AsignacionMaestroModel asignacionMaestroModel) {
        try{
            Optional<ProyectModel> optionalProyecto = iProyectRepository.findById(asignacionMaestroModel.getProyecto().getIdProyectos());
            Optional<MaestroModel> optionalMaestro = iMaestroRepository.findById(asignacionMaestroModel.getMaestro().getId_maestros_de_obra());

            if (optionalProyecto.isEmpty()) {
                return new ResponseEntity<>("El proyecto proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }
            if (optionalMaestro.isEmpty()) {
                return new ResponseEntity<>("El maestro proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }
            Optional<AsignacionMaestroModel> existente = asignacionMaestroService.getAsignacionMaestroById(
                    asignacionMaestroModel.getMaestro().getId_maestros_de_obra(),
                    asignacionMaestroModel.getProyecto().getIdProyectos()
            );

            if (existente.isPresent()) {
                return new ResponseEntity<>("La asignación ya existe.", HttpStatus.CONFLICT);
            }
            asignacionMaestroModel.setProyecto(optionalProyecto.get());
            asignacionMaestroModel.setMaestro(optionalMaestro.get());

            AsignacionMaestroModel savedAsignacionMaestro = asignacionMaestroService.saveAsignacionMaestro(asignacionMaestroModel);
            return new ResponseEntity<>(savedAsignacionMaestro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAsignacionMaestro(@RequestBody AsignacionMaestroModel asignacionMaestroModel) {
        try {
            // Validar que asignacionMaestroModel tenga maestro y proyecto válidos

            if (asignacionMaestroModel.getMaestro() == null || asignacionMaestroModel.getProyecto() == null) {
                return new ResponseEntity<>("El maestro y el proyecto son obligatorios.", HttpStatus.BAD_REQUEST);
            }

            // Verificar que los IDs no sean valores predeterminados (por ejemplo, 0)
            int idMaestro = asignacionMaestroModel.getMaestro().getId_maestros_de_obra();
            int idProyecto = asignacionMaestroModel.getProyecto().getIdProyectos();

            if (idMaestro <= 0 || idProyecto <= 0) {
                return new ResponseEntity<>("Los IDs del maestro y del proyecto deben ser válidos (mayores que 0).", HttpStatus.BAD_REQUEST);
            }

            // Verificar si la asignación existe
            Optional<AsignacionMaestroModel> existingAsignacion = asignacionMaestroService.getAsignacionMaestroById(idMaestro, idProyecto);
            if (existingAsignacion.isEmpty()) {
                return new ResponseEntity<>("La asignación especificada no existe.", HttpStatus.NOT_FOUND);
            }

            // Validar que las fechas sean coherentes
            if (asignacionMaestroModel.getFechaAsignacion() != null && asignacionMaestroModel.getFechaFin() != null &&
                    asignacionMaestroModel.getFechaAsignacion().isAfter(asignacionMaestroModel.getFechaFin())) {
                return new ResponseEntity<>("La fecha de asignación no puede ser posterior a la fecha de finalización.", HttpStatus.BAD_REQUEST);
            }

            // Actualizar los datos de la asignación existente
            AsignacionMaestroModel asignacionParaActualizar = existingAsignacion.get();
            asignacionParaActualizar.setFechaAsignacion(asignacionMaestroModel.getFechaAsignacion());
            asignacionParaActualizar.setFechaFin(asignacionMaestroModel.getFechaFin());
            asignacionParaActualizar.setEstadoAsignacion(asignacionMaestroModel.getEstadoAsignacion());

            // Guardar la asignación actualizada
            AsignacionMaestroModel updatedAsignacion = asignacionMaestroService.updateAsignacionMaestro(asignacionParaActualizar);
            return new ResponseEntity<>(updatedAsignacion, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace(); // Usa un logger para mejorar la gestión de errores
            return new ResponseEntity<>("Ocurrió un error al actualizar la asignación.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<AsignacionMaestroDTO>> getAllAsignacionMaestros() {
        List<AsignacionMaestroModel> asignaciones = asignacionMaestroService.getAsignacionMaestros();
        List<AsignacionMaestroDTO> asignacionDTO = asignaciones.stream().map(asignacion -> {
            AsignacionMaestroDTO dto = new AsignacionMaestroDTO();
            dto.setMaestro(asignacion.getMaestro().getId_maestros_de_obra());
            dto.setProyecto(asignacion.getProyecto().getIdProyectos());
            dto.setNombreMaestro(asignacion.getMaestro().getNombre());
            dto.setNombreProyecto(asignacion.getProyecto().getNombreProyecto());
            dto.setFechaAsignacion(asignacion.getFechaAsignacion());
            dto.setFechaFin(asignacion.getFechaFin());
            dto.setEstadoAsignacion(asignacion.getEstadoAsignacion().name());
            return dto;
        }).toList();
        return new ResponseEntity<>(asignacionDTO, HttpStatus.OK);
    }

    @GetMapping("/{idMaestro}/{idProyecto}")
    public ResponseEntity<AsignacionMaestroDTO> getAsignacionMaestroById(
            @PathVariable Integer idMaestro, @PathVariable Integer idProyecto) {
        Optional<AsignacionMaestroModel> asignacion = asignacionMaestroService.getAsignacionMaestroById(idMaestro, idProyecto);
        return asignacion.map(model -> {
            AsignacionMaestroDTO dto = new AsignacionMaestroDTO();
            dto.setMaestro(model.getMaestro().getId_maestros_de_obra());
            dto.setProyecto(model.getProyecto().getIdProyectos());
            dto.setNombreProyecto(model.getProyecto().getNombreProyecto());
            dto.setNombreMaestro(model.getMaestro().getNombre());
            dto.setEstadoAsignacion(model.getEstadoAsignacion().name());
            dto.setFechaAsignacion(model.getFechaAsignacion());
            dto.setFechaFin(model.getFechaFin());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<AsignacionMaestroDTO>> getAsignacionesByProyecto(@PathVariable Integer idProyecto) {
        List<AsignacionMaestroModel> asignaciones = asignacionMaestroService.getAsignacionesByProyecto(idProyecto);
        List<AsignacionMaestroDTO> dtos = asignaciones.stream().map(asignacion -> {
            AsignacionMaestroDTO dto = new AsignacionMaestroDTO();
            dto.setMaestro(asignacion.getMaestro().getId_maestros_de_obra());
            dto.setProyecto(asignacion.getProyecto().getIdProyectos());
            dto.setNombreMaestro(asignacion.getMaestro().getNombre());
            dto.setNombreProyecto(asignacion.getProyecto().getNombreProyecto());
            dto.setEstadoAsignacion(asignacion.getEstadoAsignacion().name());
            dto.setFechaAsignacion(asignacion.getFechaAsignacion());
            dto.setFechaFin(asignacion.getFechaFin());
            return dto;
        }).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/maestro/{idMaestro}")
    public ResponseEntity<List<AsignacionMaestroDTO>> getAsignacionesByMaestro(@PathVariable Integer idMaestro) {
        List<AsignacionMaestroModel> asignaciones = asignacionMaestroService.getAsignacionesByMaestro(idMaestro);
        List<AsignacionMaestroDTO> dtos = asignaciones.stream().map(asignacion -> {
            AsignacionMaestroDTO dto = new AsignacionMaestroDTO();
            dto.setMaestro(asignacion.getMaestro().getId_maestros_de_obra());
            dto.setProyecto(asignacion.getProyecto().getIdProyectos());
            dto.setNombreMaestro(asignacion.getMaestro().getNombre());
            dto.setNombreProyecto(asignacion.getProyecto().getNombreProyecto());
            dto.setEstadoAsignacion(asignacion.getEstadoAsignacion().name());
            dto.setFechaAsignacion(asignacion.getFechaAsignacion());
            dto.setFechaFin(asignacion.getFechaFin());
            return dto;
        }).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping("/{idMaestro}/{idProyecto}")
    public ResponseEntity<Void> deleteAsignacionMaestro(
            @PathVariable Integer idMaestro, @PathVariable Integer idProyecto) {
        Optional<AsignacionMaestroModel> asignacion = asignacionMaestroService.getAsignacionMaestroById(idMaestro, idProyecto);
        if (asignacion.isPresent()) {
            asignacionMaestroService.deleteAsignacionMaestro(idMaestro, idProyecto);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}