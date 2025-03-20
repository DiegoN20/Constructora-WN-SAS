package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.service.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/Insumos")
public class InsumoController {
    @Autowired
    InsumoService InsumoService;

    @PostMapping()
    public ResponseEntity<InsumoModel> saveInsumo(@RequestBody InsumoModel InsumoModel) {
        try{
            InsumoModel savedInsumo = InsumoService.saveInsumo(InsumoModel);
            return new ResponseEntity<>(savedInsumo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<InsumoModel> updateInsumo(@RequestBody InsumoModel InsumoModel) {
        try{
            InsumoModel savedInsumo = InsumoService.updateInsumo(InsumoModel);
            return new ResponseEntity<>(savedInsumo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<InsumoModel>> getAllInsumos() {
        return new ResponseEntity<>(InsumoService.getInsumos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoModel> getInsumoById(@PathVariable Integer id){
        Optional<InsumoModel> InsumoModel = InsumoService.getInsumoById(id);
        return InsumoModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsumo(@PathVariable Integer id){
        Optional<InsumoModel> InsumoModel = InsumoService.getInsumoById(id);
        if (InsumoModel.isPresent()){
            InsumoService.deleteInsumo(InsumoModel.get().getId_insumos());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}