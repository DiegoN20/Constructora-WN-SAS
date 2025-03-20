package constructora.constructorabackend.seguridad.repository;

import constructora.constructorabackend.seguridad.model.UserModel;

public interface IUserRepository {
    public UserModel findByCorreo(String user);
    public UserModel findByTokenPassword(String tokenPassword);
}
