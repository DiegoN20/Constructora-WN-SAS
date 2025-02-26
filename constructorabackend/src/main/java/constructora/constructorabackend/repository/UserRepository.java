package contructora.constructorabackend.repository;

import contructora.constructorabackend.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public UserModel findByCorreo(String user) {
        String SQL = "SELECT * FROM user WHERE correo = ?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{user},
                new BeanPropertyRowMapper<>(UserModel.class));
    }
}