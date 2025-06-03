package constructora.constructorabackend.seguridad.repository;

import constructora.constructorabackend.seguridad.model.UserModel;

public interface IUserRepository {
    public UserModel findByCorreo(String user);
    public UserModel findByTokenPassword(String tokenPassword);
    public UserModel findById(Integer id);
    public int countTotalProyectosByUsuario(Integer usuarioId);
    public int countProyectosEnCursoByUsuario(Integer usuarioId);
    public int countProyectosSuspendidosByUsuario(Integer usuarioId);
    public int countProyectosFinalizadosByUsuario(Integer usuarioId);
    public int countTotalMaestros();
    public int countMaestrosDisponibles();
    public int countMaestrosAsignados();
    public int countProveedoresTotal();
}
