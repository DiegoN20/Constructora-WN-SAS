package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.AvancePorPisoDTO;
import constructora.constructorabackend.dto.InventarioInicialDTO;
import constructora.constructorabackend.model.AvancePorPisoModel;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.InventarioInicialModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import constructora.constructorabackend.repository.IProyectRepository;
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
    private AvancePorPisoService avancePorPisoService;

    @Autowired
    private IInsumoRepository iInsumoRepository;

    @Autowired
    private IProyectRepository iProyectRepository;

    @PostMapping()
    public ResponseEntity<?> saveAvancePorPiso(@RequestBody AvancePorPisoModel avancePorPisoModel) {
        try{
            if (avancePorPisoModel.getProyecto() == null || avancePorPisoModel.getInsumo() == null) {
                return new ResponseEntity<>("Debe proporcionar proyecto e insumo", HttpStatus.BAD_REQUEST);
            }

            Optional<ProyectModel> optionalProyecto = iProyectRepository.findById(avancePorPisoModel.getProyecto().getIdProyectos());
            Optional<InsumoModel> optionalInsumo = iInsumoRepository.findById(avancePorPisoModel.getInsumo().getIdInsumos());

            if (optionalProyecto.isEmpty()) {
                return new ResponseEntity<>("El proyecto proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }
            if (optionalInsumo.isEmpty()) {
                return new ResponseEntity<>("El insumo proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }

            Optional<AvancePorPisoModel> existente = avancePorPisoService.findByProyectoAndInsumo(
                    optionalProyecto.get(),
                    optionalInsumo.get()
            );
            if (existente.isPresent()){
                return new ResponseEntity<>( "Ya existe un registro de avance por piso para este proyecto e insumo.", HttpStatus.CONFLICT);
            }
            avancePorPisoModel.setProyecto(optionalProyecto.get());
            avancePorPisoModel.setInsumo(optionalInsumo.get());

            AvancePorPisoModel savedAvancePorPiso = avancePorPisoService.saveAvancePorPiso(avancePorPisoModel);
            return new ResponseEntity<>(savedAvancePorPiso, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAvancePorPiso(@RequestBody AvancePorPisoModel avancePorPisoModel) {
        try{
            if (avancePorPisoModel.getInsumo() == null || avancePorPisoModel.getProyecto() == null) {
                return new ResponseEntity<>("El insumo y el proyecto son obligatorios.", HttpStatus.BAD_REQUEST);
            }
            int idInsumo = avancePorPisoModel.getInsumo().getIdInsumos();
            int idProyecto = avancePorPisoModel.getProyecto().getIdProyectos();
            int idAvance = avancePorPisoModel.getIdAvancePorPiso();

            if (idInsumo <= 0 || idProyecto <= 0){
                return new ResponseEntity<>("Los ID del insumo y del proyecto deben ser válidos (mayores que 0).", HttpStatus.BAD_REQUEST);
            }

            Optional<AvancePorPisoModel> existingAvance = avancePorPisoService.getAvancePorPisoById(idAvance);
            if (existingAvance.isEmpty()){
                return new ResponseEntity<>("El avance especificado no existe", HttpStatus.NOT_FOUND);
            }
            AvancePorPisoModel avanceParaActualizar = existingAvance.get();
            avanceParaActualizar.setNumeroPiso(avancePorPisoModel.getNumeroPiso());
            avanceParaActualizar.setCantidadComprada(avancePorPisoModel.getCantidadComprada());
            avanceParaActualizar.setCostoInsumos(avancePorPisoModel.getCostoInsumos());
            avanceParaActualizar.setFechaCompra(avancePorPisoModel.getFechaCompra());
            avanceParaActualizar.setCantidadUsada(avancePorPisoModel.getCantidadUsada());

            AvancePorPisoModel updateAvance = avancePorPisoService.updateAvancePorPiso(avanceParaActualizar);
            return new ResponseEntity<>(updateAvance, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<AvancePorPisoDTO>> getAllAvancePorPisos() {
        List<AvancePorPisoModel> avances = avancePorPisoService.getAvancePorPisos();
        List<AvancePorPisoDTO> avancesDTO = avances.stream().map(avance -> {
            AvancePorPisoDTO dto = new AvancePorPisoDTO();
            dto.setIdAvance(avance.getIdAvancePorPiso());
            dto.setInsumo(avance.getInsumo().getIdInsumos());
            dto.setProyecto(avance.getProyecto().getIdProyectos());
            dto.setNombreInsumo(avance.getInsumo().getNombreInsumo());
            dto.setNombreProyecto(avance.getProyecto().getNombreProyecto());
            dto.setNumeroPiso(avance.getNumeroPiso());
            dto.setCantidadComprada(avance.getCantidadComprada());
            dto.setCostoInsumo(avance.getCostoInsumos());
            dto.setFechaCompra(avance.getFechaCompra());
            dto.setCantidadUsada(avance.getCantidadUsada());
            return dto;
        }).toList();
        return new ResponseEntity<>(avancesDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvancePorPisoModel> getAvancePorPisoById(@PathVariable Integer id){
        Optional<AvancePorPisoModel> avancePorPisoModel = avancePorPisoService.getAvancePorPisoById(id);
        return avancePorPisoModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<AvancePorPisoDTO>> getAvancePorPisoByProyecto(@PathVariable Integer idProyecto) {
        List<AvancePorPisoModel> avances = avancePorPisoService.getAvancePorPisoByProyecto(idProyecto);
        List<AvancePorPisoDTO> avancesDTO = avances.stream().map(avance -> {
            AvancePorPisoDTO dto = new AvancePorPisoDTO();
            dto.setIdAvance(avance.getIdAvancePorPiso());
            dto.setInsumo(avance.getInsumo().getIdInsumos());
            dto.setProyecto(avance.getProyecto().getIdProyectos());
            dto.setNombreInsumo(avance.getInsumo().getNombreInsumo());
            dto.setNombreProyecto(avance.getProyecto().getNombreProyecto());
            dto.setNumeroPiso(avance.getNumeroPiso());
            dto.setCantidadComprada(avance.getCantidadComprada());
            dto.setCostoInsumo(avance.getCostoInsumos());
            dto.setFechaCompra(avance.getFechaCompra());
            dto.setCantidadUsada(avance.getCantidadUsada());
            return dto;
        }).toList();
        return new ResponseEntity<>(avancesDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvancePorPiso(@PathVariable Integer id){
        Optional<AvancePorPisoModel> avancePorPisoModel = avancePorPisoService.getAvancePorPisoById(id);
        if (avancePorPisoModel.isPresent()){
            avancePorPisoService.deleteAvancePorPiso(avancePorPisoModel.get().getIdAvancePorPiso());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
