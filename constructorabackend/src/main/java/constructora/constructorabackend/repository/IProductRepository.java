package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.ProductModel;

import java.util.List;

public interface IProductRepository {
    public List<ProductModel> findAll();
}
