package contructora.constructorabackend.repository;

import contructora.constructorabackend.model.ProductModel;

import java.util.List;

public interface IProductRepository {
    public List<ProductModel> findAll();
}
