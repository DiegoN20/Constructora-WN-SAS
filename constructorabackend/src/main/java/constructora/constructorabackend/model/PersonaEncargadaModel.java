package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persona_encargada")
@Data
@NoArgsConstructor
public class PersonaEncargadaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona_encargada")
    private int id_persona_encargada;

    @Column(name = "nombre", length = 45, nullable = false)
    private String nombre;
    @Column(name = "apellido", length = 45, nullable = false)
    private String apellido;

    @Column(name = "telefono", nullable = false)
    private int telefono;
    @Column(name = "cedula", nullable = false)
    private int cedula;

    public int getId_persona_encargada() {
        return id_persona_encargada;
    }

    public void setId_persona_encargada(int id_persona_encargada) {
        this.id_persona_encargada = id_persona_encargada;
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }
}