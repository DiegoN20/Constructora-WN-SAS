package contructora.constructorabackend.controller;

import contructora.constructorabackend.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import contructora.constructorabackend.model.ProductModel;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1")
public class ProductController {

    @Autowired
    IProductService iProductService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<ProductModel> products = this.iProductService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}