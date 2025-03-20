package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.service.InventarioInicialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/InventarioInicials")
public class InventarioInicialController {
    @Autowired
    InventarioInicialService InventarioInicialService;

    @PostMapping()
    public ResponseEntity<InventarioInicialModel> saveInventarioInicial(@RequestBody InventarioInicialModel InventarioInicialModel) {
        try{
            InventarioInicialModel savedInventarioInicial = InventarioInicialService.saveInventarioInicial(InventarioInicialModel);
            return new ResponseEntity<>(savedInventarioInicial, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<InventarioInicialModel> updateInventarioInicial(@RequestBody InventarioInicialModel InventarioInicialModel) {
        try{
            InventarioInicialModel savedInventarioInicial = InventarioInicialService.updateInventarioInicial(InventarioInicialModel);
            return new ResponseEntity<>(savedInventarioInicial, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<InventarioInicialModel>> getAllInventarioInicials() {
        return new ResponseEntity<>(InventarioInicialService.getInventarioInicials(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioInicialModel> getInventarioInicialById(@PathVariable Integer id){
        Optional<InventarioInicialModel> InventarioInicialModel = InventarioInicialService.getInventarioInicialById(id);
        return InventarioInicialModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventarioInicial(@PathVariable Integer id){
        Optional<InventarioInicialModel> InventarioInicialModel = InventarioInicialService.getInventarioInicialById(id);
        if (InventarioInicialModel.isPresent()){
            InventarioInicialService.deleteInventarioInicial(InventarioInicialModel.get().getIdInventarioInicial());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}