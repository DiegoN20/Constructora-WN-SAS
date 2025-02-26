package constructora.constructorabackend.repository;

import constructora.constructorabackend.model.UserModel;

public interface IUserRepository {
    public UserModel findByCorreo(String user);
}
