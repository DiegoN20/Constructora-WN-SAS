package constructora.constructorabackend.unit;

import constructora.constructorabackend.dto.StockDTO;
import constructora.constructorabackend.service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockControllerUnitTest {

    @Test
    public void test1(){
        StockService stockService = new StockService();
        final StockDTO resultado = stockService.saveStock():
        Assertions.assertEquals();
    }
}
