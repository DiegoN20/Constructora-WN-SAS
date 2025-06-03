package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.ProveedorDTO;
import constructora.constructorabackend.repository.IProveedorRepository;
import constructora.constructorabackend.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import constructora.constructorabackend.model.ProveedorModel;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/Proveedors")
public class ProveedorController {

    @Autowired
    private IProveedorRepository iProveedorRepository;

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping()
    public ResponseEntity<ProveedorModel> saveProveedor(@RequestBody ProveedorModel proveedorModel) {
        try{
            ProveedorModel savedProveedor = proveedorService.saveProveedor(proveedorModel);
            return new ResponseEntity<>(savedProveedor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProveedor(@RequestBody ProveedorModel proveedorModel) {
        try{
            Optional<ProveedorModel> existingProveedor = proveedorService.getProveedorById(proveedorModel.getIdProveedores());
            if (existingProveedor.isEmpty()) {
                return new ResponseEntity<>("El proveedor con el ID especificado no existe.", HttpStatus.NOT_FOUND);
            }
            ProveedorModel proveedorParaActualizar = existingProveedor.get();
            proveedorParaActualizar.setNombreProveedor(proveedorModel.getNombreProveedor());
            proveedorParaActualizar.setTipoServicio(proveedorModel.getTipoServicio());
            proveedorParaActualizar.setCorreo(proveedorModel.getCorreo());
            proveedorParaActualizar.setTelefono(proveedorModel.getTelefono());

            ProveedorModel savedProveedor = proveedorService.updateProveedor(proveedorModel);
            return new ResponseEntity<>(savedProveedor, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAllProveedors() {
        List<ProveedorModel> proveedores = proveedorService.getProveedors();
        List<ProveedorDTO> proveedorDTO = proveedores.stream().map(proveedor -> {
            ProveedorDTO dto = new ProveedorDTO();
            dto.setIdProveedores(proveedor.getIdProveedores());
            dto.setNombreProveedor(proveedor.getNombreProveedor());
            dto.setTipoServicio(proveedor.getTipoServicio());
            dto.setCorreo(proveedor.getCorreo());
            dto.setTelefono(proveedor.getTelefono());
            return dto;
        }).toList();
        return new ResponseEntity<>(proveedorDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorModel> getProveedorById(@PathVariable Integer id){
        Optional<ProveedorModel> proveedorModel = proveedorService.getProveedorById(id);
        return proveedorModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Integer id){
        Optional<ProveedorModel> proveedorModel = proveedorService.getProveedorById(id);
        if (proveedorModel.isPresent()){
            proveedorService.deleteProveedor(proveedorModel.get().getIdProveedores());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}