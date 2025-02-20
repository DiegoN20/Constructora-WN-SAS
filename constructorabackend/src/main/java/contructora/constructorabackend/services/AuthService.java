package contructora.constructorabackend.services;

import contructora.constructorabackend.DTO.SignupRequest;
import contructora.constructorabackend.entities.User;

public interface AuthService {
    User createUser(SignupRequest signupRequest);
}
