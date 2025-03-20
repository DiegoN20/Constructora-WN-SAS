package constructora.constructorabackend.seguridad.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de la clave primaria
    @Column(name = "id_usuarios") // Mapea la columna "id_usuarios"
    private Integer id_usuarios;

    @Column(name = "nombre", nullable = false, length = 100) // Otros campos con restricciones adicionales
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 45)
    private String apellido;

    @Column(name = "correo", nullable = false, unique = true, length = 45)
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 70)
    private String contrasena;

    @Column(name = "rol", nullable = false, length = 45)
    private String rol;

    @Column(name = "token_password", length = 100)
    private String tokenPassword;

    public Integer getId_usuarios() {
        return id_usuarios;
    }

    public void setId_usuarios(Integer id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }
}
