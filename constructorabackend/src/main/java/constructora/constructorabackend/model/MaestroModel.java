package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "maestros_de_obra")
@Data
@NoArgsConstructor
public class MaestroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maestros_de_obra", nullable = false)
    private int id_maestros_de_obra;

    @OneToMany(mappedBy = "maestro", cascade = CascadeType.ALL)
    private Set<AsignacionMaestroModel> asignaciones = new HashSet<>();

    @Column(name = "cedula", nullable = false)
    private int cedula;
    @Column(name = "nombre", length = 45, nullable = false)
    private String nombre;
    @Column(name = "apellido", length = 45, nullable = false)
    private String apellido;
    @Column(name = "telefono", nullable = false)
    private int telefono;

    @Enumerated(EnumType.STRING)
    private Estado estado;
    private enum Estado{
        Asignado_A_Proyecto, Disponible, Desvinculado, Incapacitado
    };
    @Column(name = "salario", nullable = false)
    private int salario;

    @Temporal(TemporalType.DATE)
    private Date fechaVinculacion;

    @Temporal(TemporalType.DATE)
    private Date fechaDesvinculacion;

    public int getId_maestros_de_obra() {
        return id_maestros_de_obra;
    }

    public void setId_maestros_de_obra(int id_maestros_de_obra) {
        this.id_maestros_de_obra = id_maestros_de_obra;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public Date getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(Date fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    public Date getFechaDesvinculacion() {
        return fechaDesvinculacion;
    }

    public void setFechaDesvinculacion(Date fechaDesvinculacion) {
        this.fechaDesvinculacion = fechaDesvinculacion;
    }
}

