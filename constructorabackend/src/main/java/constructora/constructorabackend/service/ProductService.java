package contructora.constructorabackend.service;

import contructora.constructorabackend.model.ProductModel;
import contructora.constructorabackend.repository.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements  IProductService{
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public List<ProductModel> findAll() {
        List<ProductModel> list;
        try{
            list = iProductRepository.findAll();
        }catch (Exception e){
            throw e;
        }
        return list;
    }
}
