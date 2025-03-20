package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.ServicioExternoModel;
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
    ServicioExternoService ServicioExternoService;

    @PostMapping()
    public ResponseEntity<ServicioExternoModel> saveServicioExterno(@RequestBody ServicioExternoModel ServicioExternoModel) {
        try{
            ServicioExternoModel savedServicioExterno = ServicioExternoService.saveServicioExterno(ServicioExternoModel);
            return new ResponseEntity<>(savedServicioExterno, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ServicioExternoModel> updateServicioExterno(@RequestBody ServicioExternoModel ServicioExternoModel) {
        try{
            ServicioExternoModel savedServicioExterno = ServicioExternoService.updateServicioExterno(ServicioExternoModel);
            return new ResponseEntity<>(savedServicioExterno, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServicioExternoModel>> getAllServicioExternos() {
        return new ResponseEntity<>(ServicioExternoService.getServicioExternos(), HttpStatus.OK);
    }

    @GetMapping("/{idProveedor}/{idProyecto}")
    public ResponseEntity<ServicioExternoModel> getServicioExternoById(
            @PathVariable Integer idProveedor, @PathVariable Integer idProyecto) {
        Optional<ServicioExternoModel> servicio = ServicioExternoService.getServicioExternoById(idProveedor, idProyecto);
        return servicio.map(model -> new ResponseEntity<>(model, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{idProveedor}/{idProyecto}")
    public ResponseEntity<Void> deleteServicioExterno(
            @PathVariable Integer idProveedor, @PathVariable Integer idProyecto) {
        Optional<ServicioExternoModel> servicio = ServicioExternoService.getServicioExternoById(idProveedor, idProyecto);
        if (servicio.isPresent()) {
            ServicioExternoService.deleteServicioExterno(idProveedor, idProyecto);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}