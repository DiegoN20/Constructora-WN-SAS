package contructora.constructorabackend.repository;

import contructora.constructorabackend.model.UserModel;

public interface IUserRepository {
    public UserModel findByCorreo(String user);
}
