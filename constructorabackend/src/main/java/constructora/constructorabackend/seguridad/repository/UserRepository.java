package constructora.constructorabackend.seguridad.repository;

import constructora.constructorabackend.seguridad.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserModel findByCorreo(String user) {
        String SQL = "SELECT * FROM usuarios WHERE correo = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{user},
                    new BeanPropertyRowMapper<>(UserModel.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UserModel findByTokenPassword(String tokenPassword) {
        String SQL = "SELECT * FROM usuarios WHERE token_password = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{tokenPassword},
                    new BeanPropertyRowMapper<>(UserModel.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(UserModel userModel) {
        String SQL = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, rol = ?, token_password = ? WHERE id_usuarios = ?";
        jdbcTemplate.update(SQL, userModel.getNombre(), userModel.getApellido(), userModel.getCorreo(), userModel.getContrasena(), userModel.getRol(), userModel.getTokenPassword(), userModel.getId_usuarios());
    }
}