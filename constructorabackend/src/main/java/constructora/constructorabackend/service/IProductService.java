package constructora.constructorabackend.service;

import constructora.constructorabackend.model.ProductModel;

import java.util.List;

public interface IProductService {
    public List<ProductModel> findAll();
}
