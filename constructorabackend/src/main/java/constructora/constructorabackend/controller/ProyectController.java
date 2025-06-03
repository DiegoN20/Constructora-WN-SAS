package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.ProyectDTO;
import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.seguridad.model.UserModel;
import constructora.constructorabackend.seguridad.repository.IUserRepository;
import constructora.constructorabackend.service.ProyectService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import constructora.constructorabackend.model.ProyectModel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/Proyectos")
public class ProyectController {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    ProyectService proyectService;

    @PostMapping
    public ResponseEntity<?> saveProyecto(@RequestBody ProyectModel proyectModel) {
        try {
            // Validar que el usuario existe en el repositorio
            if (proyectModel.getUsuario() == null || proyectModel.getUsuario().getId_usuarios() == null) {
                return new ResponseEntity<>("El usuario proporcionado no es válido.", HttpStatus.BAD_REQUEST);
            }

            UserModel usuario = iUserRepository.findById(proyectModel.getUsuario().getId_usuarios());
            if (usuario == null) {
                return new ResponseEntity<>("El usuario proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }

            System.out.println(proyectModel.getPresupuestoPrevisto());
            if (proyectModel.getPresupuestoPrevisto() <= 0) {
                return new ResponseEntity<>("El presupuesto debe ser mayor a cero.", HttpStatus.BAD_REQUEST);
            }
            if (proyectModel.getFechaInicio() != null && proyectModel.getFechaFin() != null &&
                    proyectModel.getFechaInicio().after(proyectModel.getFechaFin())) {
                return new ResponseEntity<>("La fecha de inicio no puede ser posterior a la fecha de fin.", HttpStatus.BAD_REQUEST);
            }

            // Asociar el usuario al proyecto
            proyectModel.setUsuario(usuario);

            // Validar otros campos del proyecto (opcional)
            if (proyectModel.getNombreProyecto() == null || proyectModel.getNombreProyecto().isEmpty()) {
                return new ResponseEntity<>("El nombre del proyecto es obligatorio.", HttpStatus.BAD_REQUEST);
            }

            // Guardar el proyecto
            ProyectModel savedProyecto = proyectService.saveProyecto(proyectModel);
            return new ResponseEntity<>(savedProyecto, HttpStatus.CREATED); // Cambia el status a 201 Created

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error al guardar el proyecto.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProyecto(@RequestBody ProyectModel proyectModel) {
        try {
            // Verificar si el proyecto existe
            Optional<ProyectModel> existingProyecto = proyectService.getProyectoById(proyectModel.getIdProyectos());
            if (existingProyecto.isEmpty()) {
                return new ResponseEntity<>("El proyecto con el ID especificado no existe.", HttpStatus.NOT_FOUND);
            }

            // Validar que el usuario asociado no sea nulo
            if (proyectModel.getUsuario() == null || proyectModel.getUsuario().getId_usuarios() == null) {
                return new ResponseEntity<>("El usuario asociado no puede ser nulo.", HttpStatus.BAD_REQUEST);
            }

            // Verificar que el usuario existe
            UserModel usuario = iUserRepository.findById(proyectModel.getUsuario().getId_usuarios());
            if (usuario == null) {
                return new ResponseEntity<>("El usuario proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }

            // Actualizar los datos del proyecto
            ProyectModel proyectoParaActualizar = existingProyecto.get();
            proyectoParaActualizar.setNombreProyecto(proyectModel.getNombreProyecto());
            proyectoParaActualizar.setDireccion(proyectModel.getDireccion());
            proyectoParaActualizar.setDescripcion(proyectModel.getDescripcion());
            proyectoParaActualizar.setFechaInicio(proyectModel.getFechaInicio());
            proyectoParaActualizar.setFechaFin(proyectModel.getFechaFin());
            proyectoParaActualizar.setPresupuestoPrevisto(proyectModel.getPresupuestoPrevisto());
            proyectoParaActualizar.setEstadoProyecto(proyectModel.getEstadoProyecto());
            proyectoParaActualizar.setCantidadPisos(proyectModel.getCantidadPisos());
            proyectoParaActualizar.setUsuario(usuario);

            // Guardar el proyecto actualizado
            ProyectModel savedProyecto = proyectService.updateProyecto(proyectoParaActualizar);
            return new ResponseEntity<>(savedProyecto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error interno al actualizar el proyecto.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProyectDTO>> getAllProyectos() {
        List<ProyectModel> proyectos = proyectService.getProyectos();
        List<ProyectDTO> proyectosDTO = proyectos.stream().map(proyecto -> {
            ProyectDTO dto = new ProyectDTO();
            dto.setIdProyectos(proyecto.getIdProyectos());
            dto.setNombreProyecto(proyecto.getNombreProyecto());
            dto.setDireccion(proyecto.getDireccion());
            dto.setDescripcion(proyecto.getDescripcion());
            dto.setFechaInicio(proyecto.getFechaInicio());
            dto.setFechaFin(proyecto.getFechaFin());
            dto.setPresupuestoPrevisto(proyecto.getPresupuestoPrevisto());
            dto.setPresupuestoActual(proyecto.getPresupuestoActual());
            dto.setPresupuestoRestante(proyecto.getPresupuestoRestante());
            dto.setEstadoProyecto(proyecto.getEstadoProyecto().name());
            dto.setCantidadPisos(proyecto.getCantidadPisos());
            return dto;
        }).toList();
        return new ResponseEntity<>(proyectosDTO, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProyectModel> getProyectoById(@PathVariable Integer id) {
        Optional<ProyectModel> proyectModel = proyectService.getProyectoById(id);
        return proyectModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/asignaciones")
    public ResponseEntity<Set<AsignacionMaestroModel>> getAsignacionesByProyectoId(@PathVariable Integer id) {
        Optional<ProyectModel> proyecto = proyectService.getProyectoById(id);
        if (proyecto.isPresent()) {
            Hibernate.initialize(proyecto.get().getAsignaciones()); // Inicializar la colección
            return new ResponseEntity<>(proyecto.get().getAsignaciones(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProyecto(@PathVariable Integer id) {
        try {
            Optional<ProyectModel> proyectModel = proyectService.getProyectoById(id);
            if (proyectModel.isPresent()) {
                proyectService.deleteProyecto(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}