package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.AvancePorPisoModel;
import constructora.constructorabackend.service.AvancePorPisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/AvancePorPisos")
public class AvancePorPisoController {

    @Autowired
    AvancePorPisoService AvancePorPisoService;

    @PostMapping()
    public ResponseEntity<AvancePorPisoModel> saveAvancePorPiso(@RequestBody AvancePorPisoModel AvancePorPisoModel) {
        try{
            AvancePorPisoModel savedAvancePorPiso = AvancePorPisoService.saveAvancePorPiso(AvancePorPisoModel);
            return new ResponseEntity<>(savedAvancePorPiso, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<AvancePorPisoModel> updateAvancePorPiso(@RequestBody AvancePorPisoModel AvancePorPisoModel) {
        try{
            AvancePorPisoModel savedAvancePorPiso = AvancePorPisoService.updateAvancePorPiso(AvancePorPisoModel);
            return new ResponseEntity<>(savedAvancePorPiso, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<AvancePorPisoModel>> getAllAvancePorPisos() {
        return new ResponseEntity<>(AvancePorPisoService.getAvancePorPisos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvancePorPisoModel> getAvancePorPisoById(@PathVariable Integer id){
        Optional<AvancePorPisoModel> AvancePorPisoModel = AvancePorPisoService.getAvancePorPisoById(id);
        return AvancePorPisoModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvancePorPiso(@PathVariable Integer id){
        Optional<AvancePorPisoModel> AvancePorPisoModel = AvancePorPisoService.getAvancePorPisoById(id);
        if (AvancePorPisoModel.isPresent()){
            AvancePorPisoService.deleteAvancePorPiso(AvancePorPisoModel.get().getIdAvancePorPiso());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
