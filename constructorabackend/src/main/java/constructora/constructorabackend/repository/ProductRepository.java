package constructora.constructorabackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import constructora.constructorabackend.model.ProductModel;

import java.util.List;

@Repository
public class ProductRepository implements  IProductRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProductModel> findAll() {
        String SQL = "SELECT * FROM proyectos";
        return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(ProductModel.class));
    }

}