package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.ProveedorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/Proveedors")
public class ProveedorController {
    @Autowired
    constructora.constructorabackend.service.ProveedorService ProveedorService;

    @PostMapping()
    public ResponseEntity<ProveedorModel> saveProveedor(@RequestBody ProveedorModel ProveedorModel) {
        try{
            ProveedorModel savedProveedor = ProveedorService.saveProveedor(ProveedorModel);
            return new ResponseEntity<>(savedProveedor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ProveedorModel> updateProveedor(@RequestBody ProveedorModel ProveedorModel) {
        try{
            ProveedorModel savedProveedor = ProveedorService.updateProveedor(ProveedorModel);
            return new ResponseEntity<>(savedProveedor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProveedorModel>> getAllProveedors() {
        return new ResponseEntity<>(ProveedorService.getProveedors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorModel> getProveedorById(@PathVariable Integer id){
        Optional<ProveedorModel> ProveedorModel = ProveedorService.getProveedorById(id);
        return ProveedorModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Integer id){
        Optional<ProveedorModel> ProveedorModel = ProveedorService.getProveedorById(id);
        if (ProveedorModel.isPresent()){
            ProveedorService.deleteProveedor(ProveedorModel.get().getIdProveedores());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}