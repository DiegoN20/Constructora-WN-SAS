package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.AsignacionMaestroDTO;
import constructora.constructorabackend.dto.InventarioInicialDTO;
import constructora.constructorabackend.model.AsignacionMaestroModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import constructora.constructorabackend.repository.IProyectRepository;
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
@RequestMapping("api/v1/InventarioInicial")
public class InventarioInicialController {
    @Autowired
    private InventarioInicialService inventarioInicialService;

    @Autowired
    private IInsumoRepository iInsumoRepository;

    @Autowired
    private IProyectRepository iProyectRepository;

    @PostMapping()
    public ResponseEntity<?> saveInventarioInicial(@RequestBody InventarioInicialModel inventarioInicialModel) {
        try{
            if (inventarioInicialModel.getProyecto() == null || inventarioInicialModel.getInsumo() == null) {
                return new ResponseEntity<>("Debe proporcionar proyecto e insumo", HttpStatus.BAD_REQUEST);
            }

            System.out.println(inventarioInicialModel.getProyecto().getIdProyectos());
            Optional<ProyectModel> optionalProyecto = iProyectRepository.findById(inventarioInicialModel.getProyecto().getIdProyectos());
            Optional<InsumoModel> optionalInsumo = iInsumoRepository.findById(inventarioInicialModel.getInsumo().getIdInsumos());

            if (optionalProyecto.isEmpty()) {
                return new ResponseEntity<>("El proyecto proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }
            if (optionalInsumo.isEmpty()) {
                return new ResponseEntity<>("El insumo proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }
            Optional<InventarioInicialModel> existente = inventarioInicialService.findByProyectoAndInsumo(
                    optionalProyecto.get(),
                    optionalInsumo.get()
            );
            if (existente.isPresent()){
                return new ResponseEntity<>("Ya existe un registro de inventario inicial para este proyecto e insumo.", HttpStatus.CONFLICT);
            }
            inventarioInicialModel.setProyecto(optionalProyecto.get());
            inventarioInicialModel.setInsumo(optionalInsumo.get());

            InventarioInicialModel savedInventarioInicial = inventarioInicialService.saveInventarioInicial(inventarioInicialModel);
            return new ResponseEntity<>(savedInventarioInicial, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateInventarioInicial(@RequestBody InventarioInicialModel inventarioInicialModel) {
        try{
            if (inventarioInicialModel.getInsumo() == null || inventarioInicialModel.getProyecto() == null) {
                return new ResponseEntity<>("El insumo y el proyecto son obligatorios.", HttpStatus.BAD_REQUEST);
            }
            int idInsumo = inventarioInicialModel.getInsumo().getIdInsumos();
            int idProyecto = inventarioInicialModel.getProyecto().getIdProyectos();
            int idInventario = inventarioInicialModel.getIdInventarioInicial();

            if (idInsumo <= 0 || idProyecto <= 0){
                return new ResponseEntity<>("Los ID del insumo y del proyecto deben ser válidos (mayores que 0).", HttpStatus.BAD_REQUEST);
            }

            Optional<InventarioInicialModel> existingInventario = inventarioInicialService.getInventarioInicialById(idInventario);
            if (existingInventario.isEmpty()) {
                return new ResponseEntity<>("El inventario especificado no existe", HttpStatus.NOT_FOUND);
            }

            InventarioInicialModel inventarioParaActualizar = existingInventario.get();
            inventarioParaActualizar.setCantidad(inventarioInicialModel.getCantidad());
            inventarioParaActualizar.setPrecio(inventarioInicialModel.getPrecio());
            inventarioParaActualizar.setUnidad(inventarioInicialModel.getUnidad());

            InventarioInicialModel updateInventario = inventarioInicialService.updateInventarioInicial(inventarioParaActualizar);
            return new ResponseEntity<>(updateInventario, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<InventarioInicialDTO>> getAllInventarioInicials() {
        List<InventarioInicialModel> inventarios = inventarioInicialService.getInventarioInicials();
        List<InventarioInicialDTO> inventariosDTO = inventarios.stream().map(inventario -> {
            InventarioInicialDTO dto = new InventarioInicialDTO();
            dto.setIdInventario(inventario.getIdInventarioInicial());
            dto.setInsumo(inventario.getInsumo().getIdInsumos());
            dto.setProyecto(inventario.getProyecto().getIdProyectos());
            dto.setNombreInsumo(inventario.getInsumo().getNombreInsumo());
            dto.setNombreProyecto(inventario.getProyecto().getNombreProyecto());
            dto.setCantidad(inventario.getCantidad());
            dto.setPrecio(inventario.getPrecio());
            dto.setUnidad(inventario.getUnidad());
            return dto;
        }).toList();
        return new ResponseEntity<>(inventariosDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioInicialModel> getInventarioInicialById(@PathVariable Integer id){
        Optional<InventarioInicialModel> inventarioInicialModel = inventarioInicialService.getInventarioInicialById(id);
        return inventarioInicialModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<InventarioInicialDTO>> getInventarioInicialByProyecto(@PathVariable Integer idProyecto) {
        List<InventarioInicialModel> inventarios = inventarioInicialService.getInventarioInicialByProyecto(idProyecto);
        List<InventarioInicialDTO> inventariosDTO = inventarios.stream().map(inventario -> {
            InventarioInicialDTO dto = new InventarioInicialDTO();
            dto.setIdInventario(inventario.getIdInventarioInicial());
            dto.setInsumo(inventario.getInsumo().getIdInsumos());
            dto.setProyecto(inventario.getProyecto().getIdProyectos());
            dto.setNombreInsumo(inventario.getInsumo().getNombreInsumo());
            dto.setNombreProyecto(inventario.getProyecto().getNombreProyecto());
            dto.setCantidad(inventario.getCantidad());
            dto.setPrecio(inventario.getPrecio());
            dto.setUnidad(inventario.getUnidad());
            return dto;
        }).toList();
        return new ResponseEntity<>(inventariosDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventarioInicial(@PathVariable Integer id){
        Optional<InventarioInicialModel> inventarioInicialModel = inventarioInicialService.getInventarioInicialById(id);
        if (inventarioInicialModel.isPresent()){
            inventarioInicialService.deleteInventarioInicial(inventarioInicialModel.get().getIdInventarioInicial());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}