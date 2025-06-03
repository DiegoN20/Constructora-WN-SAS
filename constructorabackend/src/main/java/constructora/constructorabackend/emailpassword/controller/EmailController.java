package constructora.constructorabackend.emailpassword.controller;

import constructora.constructorabackend.dto.Mensaje;
import constructora.constructorabackend.emailpassword.dto.ChangePasswordDto;
import constructora.constructorabackend.emailpassword.service.EmailService;
import constructora.constructorabackend.emailpassword.dto.EmailValuesDto;
import constructora.constructorabackend.seguridad.model.UserModel;
import constructora.constructorabackend.seguridad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;


    private static final String subject = "Cambio de contraseña";

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailValuesDto dto) {
        UserModel userModel = userRepository.findByCorreo(dto.getMailTo());
        if(userModel == null)
            return new ResponseEntity<>(new Mensaje("No existe ningún usuario con esas credenciales"), HttpStatus.NOT_FOUND);

        dto.setMailFrom(mailFrom);
        dto.setMailTo(userModel.getCorreo());
        dto.setSubject(subject);
        dto.setUserName(userModel.getNombre());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        dto.setTokenPassword(tokenPassword);
        userModel.setTokenPassword(tokenPassword);
        userRepository.save(userModel); // Guardar token en la base de datos
        emailService.sendEmail(dto);
        return new ResponseEntity(new Mensaje("Te hemos enviado un correo"), HttpStatus.OK);
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto dto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
        if(!dto.getPassword().equals(dto.getConfirmPassword())) {
            return new ResponseEntity<>(new Mensaje("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);
        }
        if (dto.getTokenPassword() == null || dto.getTokenPassword().isEmpty()) {
            return new ResponseEntity<>(new Mensaje("El token no puede ser nulo o vacío"), HttpStatus.BAD_REQUEST);
        }
        UserModel userModel = userRepository.findByTokenPassword(dto.getTokenPassword());
        if(userModel == null)
            return new ResponseEntity<>(new Mensaje("No existe ningún usuario con esas credenciales"), HttpStatus.NOT_FOUND);

        String newPassword = passwordEncoder.encode(dto.getPassword());
        userModel.setContrasena(newPassword);
        userModel.setTokenPassword(null);
        userRepository.save(userModel);
        return new ResponseEntity<>(new Mensaje("Contraseña actualizada"), HttpStatus.OK);
    }

}
