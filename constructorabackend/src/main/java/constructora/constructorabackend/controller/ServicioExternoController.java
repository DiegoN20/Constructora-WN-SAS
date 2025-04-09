package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.ServicioExternoDTO;
import constructora.constructorabackend.model.*;
import constructora.constructorabackend.repository.IProveedorRepository;
import constructora.constructorabackend.repository.IProyectRepository;
import constructora.constructorabackend.service.ServicioExternoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/ServicioExternos")
public class ServicioExternoController {

    @Autowired
    private IProveedorRepository iProveedorRepository;

    @Autowired
    private IProyectRepository iProyectRepository;

    @Autowired
    private ServicioExternoService servicioExternoService;

    @PostMapping
    public ResponseEntity<?> saveServicioExterno(@RequestBody ServicioExternoModel servicioExternoModel) {
        try {
            // Validar campos obligatorios
            if (servicioExternoModel.getProveedor() == null || servicioExternoModel.getProyecto() == null) {
                return new ResponseEntity<>("El proveedor y el proyecto son obligatorios.", HttpStatus.BAD_REQUEST);
            }

            // Verificar IDs válidos
            int idProveedor = servicioExternoModel.getProveedor().getIdProveedores();
            int idProyecto = servicioExternoModel.getProyecto().getIdProyectos();

            if (idProveedor <= 0 || idProyecto <= 0) {
                return new ResponseEntity<>("Los IDs del proveedor y del proyecto deben ser válidos.", HttpStatus.BAD_REQUEST);
            }

            // Verificar existencia de proveedor y proyecto
            Optional<ProveedorModel> proveedor = iProveedorRepository.findById(idProveedor);
            Optional<ProyectModel> proyecto = iProyectRepository.findById(idProyecto);

            if (proveedor.isEmpty() || proyecto.isEmpty()) {
                String mensaje = proveedor.isEmpty() ? "El proveedor no existe." : "El proyecto no existe.";
                return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
            }

            // Validar fechas
            if (servicioExternoModel.getFechaInicio() != null && servicioExternoModel.getFechaFin() != null &&
                    servicioExternoModel.getFechaInicio().isAfter(servicioExternoModel.getFechaFin())) {
                return new ResponseEntity<>("La fecha de inicio no puede ser posterior a la fecha fin.", HttpStatus.BAD_REQUEST);
            }

            // Verificar si ya existe
            Optional<ServicioExternoModel> existente = servicioExternoService.getServicioExternoById(idProveedor, idProyecto);
            if (existente.isPresent()) {
                return new ResponseEntity<>("Ya existe un servicio externo para este proveedor y proyecto.", HttpStatus.CONFLICT);
            }

            // Asignar relaciones
            servicioExternoModel.setProveedor(proveedor.get());
            servicioExternoModel.setProyecto(proyecto.get());

            // Crear ID compuesto
            ServicioExternoId id = new ServicioExternoId();
            id.setProveedoresIdProveedores(idProveedor);
            id.setProyectosIdProyectos(idProyecto);
            servicioExternoModel.setId(id);

            // Guardar
            ServicioExternoModel saved = servicioExternoService.saveServicioExterno(servicioExternoModel);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al crear el servicio externo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateServicioExterno(@RequestBody ServicioExternoModel servicioExternoModel) {
        try {
            // Validar campos obligatorios
            if (servicioExternoModel.getProveedor() == null || servicioExternoModel.getProyecto() == null) {
                return new ResponseEntity<>("El proveedor y el proyecto son obligatorios.", HttpStatus.BAD_REQUEST);
            }

            // Obtener IDs
            int idProveedor = servicioExternoModel.getProveedor().getIdProveedores();
            int idProyecto = servicioExternoModel.getProyecto().getIdProyectos();

            if (idProveedor <= 0 || idProyecto <= 0) {
                return new ResponseEntity<>("Los IDs del proveedor, proyecto y persona encargada deben ser válidos.", HttpStatus.BAD_REQUEST);
            }

            // Verificar existencia del servicio
            Optional<ServicioExternoModel> existingServicio = servicioExternoService.getServicioExternoById(idProveedor, idProyecto);
            if (existingServicio.isEmpty()) {
                return new ResponseEntity<>("El servicio externo no existe.", HttpStatus.NOT_FOUND);
            }

            // Validar fechas
            if (servicioExternoModel.getFechaInicio() != null && servicioExternoModel.getFechaFin() != null &&
                    servicioExternoModel.getFechaInicio().isAfter(servicioExternoModel.getFechaFin())) {
                return new ResponseEntity<>("La fecha de inicio no puede ser posterior a la fecha fin.", HttpStatus.BAD_REQUEST);
            }
            // Actualizar campos editables
            ServicioExternoModel toUpdate = existingServicio.get();
            toUpdate.setDescripcionServicio(servicioExternoModel.getDescripcionServicio());
            toUpdate.setCosto(servicioExternoModel.getCosto());
            toUpdate.setFechaInicio(servicioExternoModel.getFechaInicio());
            toUpdate.setFechaFin(servicioExternoModel.getFechaFin());
            toUpdate.setPersonaEncargada(servicioExternoModel.getPersonaEncargada());
            toUpdate.setTelefono(servicioExternoModel.getTelefono());

            // Guardar cambios
            ServicioExternoModel updatedServicio = servicioExternoService.updateServicioExterno(toUpdate);
            return new ResponseEntity<>(updatedServicio, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al actualizar el servicio externo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServicioExternoDTO>> getAllServiciosExternos() {
        List<ServicioExternoModel> servicios = servicioExternoService.getServicioExternos();
        List<ServicioExternoDTO> servicioDTO = servicios.stream().map(servicio -> {
            ServicioExternoDTO dto = new ServicioExternoDTO();
            dto.setIdProveedores(servicio.getProveedor().getIdProveedores());
            dto.setIdProyecto(servicio.getProyecto().getIdProyectos());
            dto.setNombreProveedor(servicio.getProveedor().getNombreProveedor());
            dto.setNombreProyecto(servicio.getProyecto().getNombreProyecto());
            dto.setDescripcion(servicio.getDescripcionServicio());
            dto.setCosto(servicio.getCosto());
            dto.setFechaInicio(servicio.getFechaInicio());
            dto.setFechaFin(servicio.getFechaFin());
            dto.setPersonaEncargada(servicio.getPersonaEncargada());
            dto.setTelefono(servicio.getTelefono());
            return dto;
        }).toList();
        return new ResponseEntity<>(servicioDTO, HttpStatus.OK);
    }

    @GetMapping("/{idProveedor}/{idProyecto}")
    public ResponseEntity<ServicioExternoDTO> getServicioExternoById(
            @PathVariable Integer idProveedor, @PathVariable Integer idProyecto) {
        Optional<ServicioExternoModel> servicio = servicioExternoService.getServicioExternoById(idProveedor, idProyecto);
        return servicio.map(model -> {
            ServicioExternoDTO dto = new ServicioExternoDTO();
            dto.setIdProveedores(model.getProveedor().getIdProveedores());
            dto.setIdProyecto(model.getProyecto().getIdProyectos());
            dto.setNombreProveedor(model.getProveedor().getNombreProveedor());
            dto.setNombreProyecto(model.getProyecto().getNombreProyecto());
            dto.setDescripcion(model.getDescripcionServicio());
            dto.setCosto(model.getCosto());
            dto.setFechaInicio(model.getFechaInicio());
            dto.setFechaFin(model.getFechaFin());
            dto.setPersonaEncargada(model.getPersonaEncargada());
            dto.setTelefono(model.getTelefono());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<ServicioExternoDTO>> getServiciosByProyecto(@PathVariable Integer idProyecto) {
        List<ServicioExternoModel> servicios = servicioExternoService.getServicioExternoByProyecto(idProyecto);
        List<ServicioExternoDTO> dtos = servicios.stream().map(servicio -> {
            ServicioExternoDTO dto = new ServicioExternoDTO();
            dto.setIdProveedores(servicio.getProveedor().getIdProveedores());
            dto.setIdProyecto(servicio.getProyecto().getIdProyectos());
            dto.setNombreProveedor(servicio.getProveedor().getNombreProveedor());
            dto.setNombreProyecto(servicio.getProyecto().getNombreProyecto());
            dto.setDescripcion(servicio.getDescripcionServicio());
            dto.setCosto(servicio.getCosto());
            dto.setFechaInicio(servicio.getFechaInicio());
            dto.setFechaFin(servicio.getFechaFin());
            dto.setPersonaEncargada(servicio.getPersonaEncargada());
            dto.setTelefono(servicio.getTelefono());
            return dto;
        }).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<ServicioExternoDTO>> getServiciosByProveedor(@PathVariable Integer idProveedor) {
        List<ServicioExternoModel> servicios = servicioExternoService.getServicioExternoByProveedor(idProveedor);
        List<ServicioExternoDTO> dtos = servicios.stream().map(servicio -> {
            ServicioExternoDTO dto = new ServicioExternoDTO();
            dto.setIdProveedores(servicio.getProveedor().getIdProveedores());
            dto.setIdProyecto(servicio.getProyecto().getIdProyectos());
            dto.setNombreProveedor(servicio.getProveedor().getNombreProveedor());
            dto.setNombreProyecto(servicio.getProyecto().getNombreProyecto());
            dto.setDescripcion(servicio.getDescripcionServicio());
            dto.setCosto(servicio.getCosto());
            dto.setFechaInicio(servicio.getFechaInicio());
            dto.setFechaFin(servicio.getFechaFin());
            dto.setPersonaEncargada(servicio.getPersonaEncargada());
            return dto;
        }).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping("/{idProveedor}/{idProyecto}")
    public ResponseEntity<Void> deleteServicioExterno(
            @PathVariable Integer idProveedor, @PathVariable Integer idProyecto) {
        Optional<ServicioExternoModel> servicio = servicioExternoService.getServicioExternoById(idProveedor, idProyecto);
        if (servicio.isPresent()) {
            servicioExternoService.deleteServicioExterno(idProveedor, idProyecto);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}