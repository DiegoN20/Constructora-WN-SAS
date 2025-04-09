package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.InsumoDTO;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.repository.IInsumoRepository;
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
    private IInsumoRepository iInsumoRepository;

    @Autowired
    private InsumoService insumoService;

    @PostMapping()
    public ResponseEntity<InsumoModel> saveInsumo(@RequestBody InsumoModel insumoModel) {
        try{
            InsumoModel savedInsumo = insumoService.saveInsumo(insumoModel);
            return new ResponseEntity<>(savedInsumo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateInsumo(@RequestBody InsumoModel insumoModel) {
        try{
            Optional<InsumoModel> existingInsumo = insumoService.getInsumoById(insumoModel.getIdInsumos());
            if (existingInsumo.isEmpty()){
                return new ResponseEntity<>("El insumo con el ID especificado no existe.", HttpStatus.NOT_FOUND);
            }
            InsumoModel insumoParaActualizar = existingInsumo.get();
            insumoParaActualizar.setNombreInsumo(insumoModel.getNombreInsumo());
            insumoParaActualizar.setDescripcion(insumoModel.getDescripcion());
            insumoParaActualizar.setTipo(insumoModel.getTipo());

            InsumoModel savedInsumo = insumoService.updateInsumo(insumoModel);
            return new ResponseEntity<>(savedInsumo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<InsumoDTO>> getAllInsumos() {
        List<InsumoModel> insumos = insumoService.getInsumos();
        List<InsumoDTO> insumoDTO = insumos.stream().map(insumo -> {
            InsumoDTO dto = new InsumoDTO();
            dto.setIdInsumos(insumo.getIdInsumos());
            dto.setNombreInsumo(insumo.getNombreInsumo());
            dto.setDescripcion(insumo.getDescripcion());
            dto.setTipo(insumo.getTipo().name());
            return dto;
        }).toList();
        return new ResponseEntity<>(insumoDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoModel> getInsumoById(@PathVariable Integer id){
        Optional<InsumoModel> insumoModel = insumoService.getInsumoById(id);
        return insumoModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsumo(@PathVariable Integer id){
        Optional<InsumoModel> insumoModel = insumoService.getInsumoById(id);
        if (insumoModel.isPresent()){
            insumoService.deleteInsumo(insumoModel.get().getIdInsumos());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}