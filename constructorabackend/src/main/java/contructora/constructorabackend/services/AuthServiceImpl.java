package contructora.constructorabackend.services;

import contructora.constructorabackend.DTO.SignupRequest;
import contructora.constructorabackend.entities.User;
import contructora.constructorabackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(SignupRequest signupRequest) {
        //Verificar si el Usuario existe
        if(userRepository.existsByCorreo(signupRequest.getCorreo())) {
            return null;
        }

        User user = new User();
        BeanUtils.copyProperties(signupRequest,user);

        //Hash en la contrasena antes de guardar
        String hashPassword = passwordEncoder.encode(signupRequest.getContrasena());
        user.setContrasena(hashPassword);
        User createdUser = userRepository.save(user);
        user.setIdUsuarios(createdUser.getIdUsuarios());;
        return user;
    }
}
