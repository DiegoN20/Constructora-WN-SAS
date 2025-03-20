package constructora.constructorabackend.controller;

import constructora.constructorabackend.service.ProyectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import constructora.constructorabackend.model.ProyectModel;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/Proyectos")
public class ProyectController {

    @Autowired
    ProyectService proyectService;

    @PostMapping()
    public ResponseEntity<ProyectModel> saveProyecto(@RequestBody ProyectModel proyectModel) {
        try{
            ProyectModel savedProyecto = proyectService.saveProyecto(proyectModel);
            return new ResponseEntity<>(savedProyecto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ProyectModel> updateProyecto(@RequestBody ProyectModel proyectModel) {
        try{
            ProyectModel savedProyecto = proyectService.updateProyecto(proyectModel);
            return new ResponseEntity<>(savedProyecto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProyectModel>> getAllProyectos() {
        return new ResponseEntity<>(proyectService.getProyectos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectModel> getProyectoById(@PathVariable Integer id){
        Optional<ProyectModel> proyectModel = proyectService.getProyectoById(id);
        return proyectModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable Integer id){
        Optional<ProyectModel> proyectModel = proyectService.getProyectoById(id);
        if (proyectModel.isPresent()){
            proyectService.deleteProyecto(proyectModel.get().getIdProyectos());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}