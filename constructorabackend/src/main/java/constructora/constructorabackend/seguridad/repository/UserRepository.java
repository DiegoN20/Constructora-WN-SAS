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

    @Override
    public UserModel findById(Integer id) {
        String SQL = "SELECT * FROM usuarios WHERE id_usuarios = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{id},
                    new BeanPropertyRowMapper<>(UserModel.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(UserModel userModel) {
        String SQL = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, rol = ?, token_password = ? WHERE id_usuarios = ?";
        jdbcTemplate.update(SQL, userModel.getNombre(), userModel.getApellido(), userModel.getCorreo(), userModel.getContrasena(), userModel.getRol(), userModel.getTokenPassword(), userModel.getId_usuarios());
    }

    @Override
    public int countTotalProyectosByUsuario(Integer usuarioId) {
        String SQL = "SELECT COUNT(*) FROM proyectos WHERE usuarios_id_usuarios = ?";
        return jdbcTemplate.queryForObject(SQL, Integer.class, usuarioId);
    }

    @Override
    public int countProyectosEnCursoByUsuario(Integer usuarioId) {
        String SQL = "SELECT COUNT(*) FROM proyectos WHERE usuarios_id_usuarios = ? AND estado_proyecto = 'En_Proceso'";
        return jdbcTemplate.queryForObject(SQL, Integer.class, usuarioId);
    }

    @Override
    public int countProyectosSuspendidosByUsuario(Integer usuarioId) {
        String SQL = "SELECT COUNT(*) FROM proyectos WHERE usuarios_id_usuarios = ? AND estado_proyecto = 'Suspendido'";
        return jdbcTemplate.queryForObject(SQL, Integer.class, usuarioId);
    }

    @Override
    public int countProyectosFinalizadosByUsuario(Integer usuarioId) {
        String SQL = "SELECT COUNT(*) FROM proyectos WHERE usuarios_id_usuarios = ? AND estado_proyecto = 'Finalizado'";
        return jdbcTemplate.queryForObject(SQL, Integer.class, usuarioId);
    }

    @Override
    public int countTotalMaestros() {
        String SQL = "SELECT COUNT(*) FROM maestros_de_obra";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    @Override
    public int countMaestrosDisponibles() {
        String SQL = "SELECT COUNT(*) FROM maestros_de_obra WHERE estado_maestro = 'Disponible'";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    @Override
    public int countMaestrosAsignados() {
        String SQL = "SELECT COUNT(*) FROM maestros_de_obra WHERE estado_maestro = 'Asignado_A_Proyecto'";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }

    @Override
    public int countProveedoresTotal(){
        String SQL = "SELECT COUNT(*) FROM proveedores";
        return jdbcTemplate.queryForObject(SQL, Integer.class);
    }
}