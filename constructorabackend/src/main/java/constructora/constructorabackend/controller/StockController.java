package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.StockModel;
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
    StockService StockService;

    @PostMapping()
    public ResponseEntity<StockModel> saveStock(@RequestBody StockModel StockModel) {
        try{
            StockModel savedStock = StockService.saveStock(StockModel);
            return new ResponseEntity<>(savedStock, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<StockModel> updateStock(@RequestBody StockModel StockModel) {
        try{
            StockModel savedStock = StockService.updateStock(StockModel);
            return new ResponseEntity<>(savedStock, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<StockModel>> getAllStocks() {
        return new ResponseEntity<>(StockService.getStocks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockModel> getStockById(@PathVariable Integer id){
        Optional<StockModel> StockModel = StockService.getStockById(id);
        return StockModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id){
        Optional<StockModel> StockModel = StockService.getStockById(id);
        if (StockModel.isPresent()){
            StockService.deleteStock(StockModel.get().getIdStock());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
