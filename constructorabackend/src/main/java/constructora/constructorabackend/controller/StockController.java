package constructora.constructorabackend.controller;

import constructora.constructorabackend.dto.StockDTO;
import constructora.constructorabackend.model.InsumoModel;
import constructora.constructorabackend.model.ProyectModel;
import constructora.constructorabackend.model.StockModel;
import constructora.constructorabackend.repository.IInsumoRepository;
import constructora.constructorabackend.repository.IProyectRepository;
import constructora.constructorabackend.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/Stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private IInsumoRepository iInsumoRepository;

    @Autowired
    private IProyectRepository iProyectRepository;

    @PostMapping()
    public ResponseEntity<?> saveStock(@RequestBody StockModel stockModel) {
        try{
            if (stockModel.getProyecto() == null || stockModel.getInsumo() == null) {
                return new ResponseEntity<>("Debe proporcionar proyecto e insumo", HttpStatus.BAD_REQUEST);
            }

            Optional<ProyectModel> optionalProyecto = iProyectRepository.findById(stockModel.getProyecto().getIdProyectos());
            Optional<InsumoModel> optionalInsumo = iInsumoRepository.findById(stockModel.getInsumo().getIdInsumos());

            if (optionalProyecto.isEmpty()){
                return new ResponseEntity<>("El proyecto proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }
            if (optionalInsumo.isEmpty()) {
                return new ResponseEntity<>("El insumo proporcionado no existe.", HttpStatus.BAD_REQUEST);
            }

            Optional<StockModel> existente = stockService.findByProyectoAndInsumo(
                    optionalProyecto.get(),
                    optionalInsumo.get()
            );
            if (existente.isEmpty()){
                return new ResponseEntity<>("Ya existe un registro de stock para este proyecto e insumo.", HttpStatus.CONFLICT);
            }
            stockModel.setProyecto(optionalProyecto.get());
            stockModel.setInsumo(optionalInsumo.get());

            StockModel savedStock = stockService.saveStock(stockModel);
            return new ResponseEntity<>(savedStock, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateStock(@RequestBody StockModel stockModel) {
        try{
            if (stockModel.getInsumo() == null || stockModel.getProyecto() == null){
                return new ResponseEntity<>("El insumo y el proyecto son obligatorios.", HttpStatus.BAD_REQUEST);
            }
            int idInsumo = stockModel.getInsumo().getIdInsumos();
            int idProyecto = stockModel.getProyecto().getIdProyectos();
            int idStock = stockModel.getIdStock();

            if (idInsumo <= 0 || idProyecto <= 0){
                return new ResponseEntity<>("Los ID del insumo y del proyecto deben ser válidos (mayores que 0).", HttpStatus.BAD_REQUEST);
            }

            Optional<StockModel> existingStock = stockService.getStockById(idStock);
            if (existingStock.isEmpty()){
                return new ResponseEntity<>("Eñ stock especificado no existe", HttpStatus.NOT_FOUND);
            }
            StockModel stockParaActualizar = existingStock.get();
            stockParaActualizar.setCantidadInvertida(stockModel.getCantidadInvertida());
            stockParaActualizar.setCantidadRestante(stockModel.getCantidadRestante());

            StockModel savedStock = stockService.updateStock(stockParaActualizar);
            return new ResponseEntity<>(savedStock, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        List<StockModel> stocks = stockService.getStocks();
        List<StockDTO> stocksDTO = stocks.stream().map(stock -> {
           StockDTO dto = new StockDTO();
           dto.setIdStock(stock.getIdStock());
           dto.setIdInsumo(stock.getInsumo().getIdInsumos());
           dto.setIdProyecto(stock.getProyecto().getIdProyectos());
           dto.setNombreInsumo(stock.getInsumo().getNombreInsumo());
           dto.setNombreProyecto(stock.getProyecto().getNombreProyecto());
           dto.setCantidadInvertida(stock.getCantidadInvertida());
           dto.setCantidadRestante(stock.getCantidadRestante());
           return dto;
        }).toList();
        return new ResponseEntity<>(stocksDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockModel> getStockById(@PathVariable Integer id){
        Optional<StockModel> StockModel = stockService.getStockById(id);
        return StockModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<StockDTO>> getStockByProyecto(@PathVariable Integer idProyecto) {
        List<StockModel> stocks = stockService.getStockByProyecto(idProyecto);
        List<StockDTO> stockDTO = stocks.stream().map(stock -> {
            StockDTO dto = new StockDTO();
            dto.setIdStock(stock.getIdStock());
            dto.setIdInsumo(stock.getInsumo().getIdInsumos());
            dto.setIdProyecto(stock.getProyecto().getIdProyectos());
            dto.setNombreInsumo(stock.getInsumo().getNombreInsumo());
            dto.setNombreProyecto(stock.getProyecto().getNombreProyecto());
            dto.setCantidadTotal(stock.getCantidadTotal());
            dto.setCantidadInvertida(stock.getCantidadInvertida());
            dto.setCantidadRestante(stock.getCantidadRestante());
            return dto;
        }).toList();
        return new ResponseEntity<>(stockDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id){
        Optional<StockModel> StockModel = stockService.getStockById(id);
        if (StockModel.isPresent()){
            stockService.deleteStock(StockModel.get().getIdStock());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
