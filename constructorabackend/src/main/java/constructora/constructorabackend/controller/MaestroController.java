package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.MaestroModel;
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
    constructora.constructorabackend.service.MaestroService MaestroService;

    @PostMapping()
    public ResponseEntity<MaestroModel> saveMaestro(@RequestBody MaestroModel MaestroModel) {
        try{
            MaestroModel savedMaestro = MaestroService.saveMaestro(MaestroModel);
            return new ResponseEntity<>(savedMaestro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<MaestroModel> updateMaestro(@RequestBody MaestroModel MaestroModel) {
        try{
            MaestroModel savedMaestro = MaestroService.updateMaestro(MaestroModel);
            return new ResponseEntity<>(savedMaestro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<MaestroModel>> getAllMaestros() {
        return new ResponseEntity<>(MaestroService.getMaestros(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaestroModel> getMaestroById(@PathVariable Integer id){
        Optional<MaestroModel> MaestroModel = MaestroService.getMaestroById(id);
        return MaestroModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaestro(@PathVariable Integer id){
        Optional<MaestroModel> MaestroModel = MaestroService.getMaestroById(id);
        if (MaestroModel.isPresent()){
            MaestroService.deleteMaestro(MaestroModel.get().getId_maestros_de_obra());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}