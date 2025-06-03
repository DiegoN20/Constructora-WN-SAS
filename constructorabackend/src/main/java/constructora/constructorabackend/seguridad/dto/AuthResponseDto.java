package constructora.constructorabackend.seguridad.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    String token;
    String refreshToken;
    String nombre;
    String apellido;
    String rol;
    int id;
    int proyectosTotal;
    int proyectosEnCurso;
    int proyectosFinalizados;
    int proyectosSuspendidos;
    int maestrosTotal;
    int maestrosDisponibles;
    int maestrosAsignados;
    int proveedoresTotal;
}