package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.MaestroDTO;
import constructora.constructorabackend.model.MaestroModel;
import constructora.constructorabackend.repository.IMaestroRepository;
import constructora.constructorabackend.service.MaestroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/Maestros")
public class MaestroController {
    
    @Autowired
    private IMaestroRepository iMaestroRepository;
    
    @Autowired
    private MaestroService maestroService;

    @PostMapping()
    public ResponseEntity<MaestroModel> saveMaestro(@RequestBody MaestroModel maestroModel) {
        try{
            MaestroModel savedMaestro = maestroService.saveMaestro(maestroModel);
            return new ResponseEntity<>(savedMaestro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateMaestro(@RequestBody MaestroModel maestroModel) {
        try{
            Optional<MaestroModel> existingMaestro = maestroService.getMaestroById(maestroModel.getId_maestros_de_obra());
            if (existingMaestro.isEmpty()) {
                return new ResponseEntity<>("El maestro con el ID especificado no existe.", HttpStatus.NOT_FOUND);
            }
            MaestroModel maestroParaActualizar = existingMaestro.get();
            maestroParaActualizar.setCedula(maestroModel.getCedula());
            maestroParaActualizar.setNombre(maestroModel.getNombre());
            maestroParaActualizar.setApellido(maestroModel.getApellido());
            maestroParaActualizar.setTelefono(maestroModel.getTelefono());
            maestroParaActualizar.setEstadoMaestro(maestroModel.getEstadoMaestro());
            maestroParaActualizar.setSalario(maestroModel.getSalario());
            maestroParaActualizar.setFechaVinculacion(maestroModel.getFechaVinculacion());
            maestroParaActualizar.setFechaDesvinculacion(maestroModel.getFechaDesvinculacion());
            
            MaestroModel savedMaestro = maestroService.updateMaestro(maestroModel);
            return new ResponseEntity<>(savedMaestro, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<MaestroDTO>> getAllMaestros() {
        List<MaestroModel> maestros = maestroService.getMaestros();
        List<MaestroDTO> maestroDTO = maestros.stream().map(maestro -> {
            MaestroDTO dto = new MaestroDTO();
            dto.setId_maestros_de_obra(maestro.getId_maestros_de_obra());
            dto.setCedula(maestro.getCedula());
            dto.setNombre(maestro.getNombre());
            dto.setApellido(maestro.getApellido());
            dto.setTelefono(maestro.getTelefono());
            dto.setEstadoMaestro(maestro.getEstadoMaestro().name());
            dto.setSalario(maestro.getSalario());
            dto.setFechaVinculacion(maestro.getFechaVinculacion());
            dto.setFechaDesvinculacion(maestro.getFechaDesvinculacion());
            return dto;
        }).toList();
        return new ResponseEntity<>(maestroDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaestroModel> getMaestroById(@PathVariable Integer id){
        Optional<MaestroModel> maestroModel = maestroService.getMaestroById(id);
        return maestroModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<MaestroDTO>> getMaestrosDisponibles() {
        try {
            List<MaestroModel> maestrosDisponibles = maestroService.getMaestrosDisponibles(); // Llama al servicio
            List<MaestroDTO> maestroDTO = maestrosDisponibles.stream().map(maestro -> {
                MaestroDTO dto = new MaestroDTO();
                dto.setId_maestros_de_obra(maestro.getId_maestros_de_obra());
                dto.setCedula(maestro.getCedula());
                dto.setNombre(maestro.getNombre());
                dto.setApellido(maestro.getApellido());
                dto.setTelefono(maestro.getTelefono());
                dto.setEstadoMaestro(maestro.getEstadoMaestro().name());
                dto.setSalario(maestro.getSalario());
                dto.setFechaVinculacion(maestro.getFechaVinculacion());
                dto.setFechaDesvinculacion(maestro.getFechaDesvinculacion());
                return dto;
            }).toList();
            return new ResponseEntity<>(maestroDTO, HttpStatus.OK); // Retorna los maestros disponibles
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Manejo de errores
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaestro(@PathVariable Integer id){
        Optional<MaestroModel> maestroModel = maestroService.getMaestroById(id);
        if (maestroModel.isPresent()){
            maestroService.deleteMaestro(maestroModel.get().getId_maestros_de_obra());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}