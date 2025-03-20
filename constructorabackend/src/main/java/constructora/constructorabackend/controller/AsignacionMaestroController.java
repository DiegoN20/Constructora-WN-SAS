package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.AsignacionMaestroModel;
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
    AsignacionMaestroService AsignacionMaestroService;

    @PostMapping()
    public ResponseEntity<AsignacionMaestroModel> saveAsignacionMaestro(@RequestBody AsignacionMaestroModel AsignacionMaestroModel) {
        try{
            AsignacionMaestroModel savedAsignacionMaestro = AsignacionMaestroService.saveAsignacionMaestro(AsignacionMaestroModel);
            return new ResponseEntity<>(savedAsignacionMaestro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<AsignacionMaestroModel> updateAsignacionMaestro(@RequestBody AsignacionMaestroModel AsignacionMaestroModel) {
        try{
            AsignacionMaestroModel savedAsignacionMaestro = AsignacionMaestroService.updateAsignacionMaestro(AsignacionMaestroModel);
            return new ResponseEntity<>(savedAsignacionMaestro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<AsignacionMaestroModel>> getAllAsignacionMaestros() {
        return new ResponseEntity<>(AsignacionMaestroService.getAsignacionMaestros(), HttpStatus.OK);
    }

    @GetMapping("/{idMaestro}/{idProyecto}")
    public ResponseEntity<AsignacionMaestroModel> getAsignacionMaestroById(
            @PathVariable Integer idMaestro, @PathVariable Integer idProyecto) {
        Optional<AsignacionMaestroModel> asignacion = AsignacionMaestroService.getAsignacionMaestroById(idMaestro, idProyecto);
        return asignacion.map(model -> new ResponseEntity<>(model, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{idMaestro}/{idProyecto}")
    public ResponseEntity<Void> deleteAsignacionMaestro(
            @PathVariable Integer idMaestro, @PathVariable Integer idProyecto) {
        Optional<AsignacionMaestroModel> asignacion = AsignacionMaestroService.getAsignacionMaestroById(idMaestro, idProyecto);
        if (asignacion.isPresent()) {
            AsignacionMaestroService.deleteAsignacionMaestro(idMaestro, idProyecto);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}