package constructora.constructorabackend.seguridad.controller;

import constructora.constructorabackend.seguridad.dto.AuthRequestDto;
import constructora.constructorabackend.seguridad.dto.AuthResponseDto;
import constructora.constructorabackend.seguridad.model.UserModel;
import constructora.constructorabackend.seguridad.repository.UserRepository;
import constructora.constructorabackend.seguridad.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) {

        try {
            //1. Gestion authenticationManager
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequestDto.getUser(), authRequestDto.getPassword()
            ));

            //2. Validar el usuario en la bd
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequestDto.getUser());
            UserModel userModel = userRepository.findByCorreo(authRequestDto.getUser());

            //3. Generar token
            String jwt = this.jwtUtilService.generateToken(userDetails, userModel.getRol());
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, userModel.getRol());

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);
            authResponseDto.setRefreshToken(refreshToken);
            authResponseDto.setNombre(userModel.getNombre());
            authResponseDto.setApellido(userModel.getApellido());
            authResponseDto.setRol(userModel.getRol());
            authResponseDto.setId(userModel.getId_usuarios());
            authResponseDto.setProyectosTotal(userRepository.countTotalProyectosByUsuario(userModel.getId_usuarios()));
            authResponseDto.setProyectosEnCurso(userRepository.countProyectosEnCursoByUsuario(userModel.getId_usuarios()));
            authResponseDto.setProyectosFinalizados(userRepository.countProyectosFinalizadosByUsuario(userModel.getId_usuarios()));
            authResponseDto.setProyectosSuspendidos(userRepository.countProyectosSuspendidosByUsuario((userModel.getId_usuarios())));
            authResponseDto.setMaestrosTotal(userRepository.countTotalMaestros());
            authResponseDto.setMaestrosDisponibles(userRepository.countMaestrosDisponibles());
            authResponseDto.setMaestrosAsignados(userRepository.countMaestrosAsignados());
            authResponseDto.setProveedoresTotal(userRepository.countProveedoresTotal());


            return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Authetication:::" + e.getMessage());
        }

    }


    @PostMapping("/refresh")
    public ResponseEntity<?> auth(@RequestBody Map<String, String>  request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtUtilService.extractUsername(refreshToken);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            UserModel userModel = userRepository.findByCorreo(username);

            if(jwtUtilService.validateToken(refreshToken, userDetails)) {
                String newJwt = jwtUtilService.generateToken(userDetails, userModel.getRol());
                String newRefreshToken = jwtUtilService.generateRefreshToken(userDetails, userModel.getRol());

                AuthResponseDto authResponseDto = new AuthResponseDto();
                authResponseDto.setToken(newJwt);
                authResponseDto.setRefreshToken(newRefreshToken);

                return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
            }


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refresh token:::" + e.getMessage());
        }

    }
}