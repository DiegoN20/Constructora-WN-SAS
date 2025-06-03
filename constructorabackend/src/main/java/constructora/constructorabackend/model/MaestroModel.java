package constructora.constructorabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
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

    @OneToMany(mappedBy = "maestro", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<AsignacionMaestroModel> asignaciones = new HashSet<>();

    @Column(name = "cedula", nullable = false)
    private long cedula;
    @Column(name = "nombre", length = 45, nullable = false)
    private String nombre;
    @Column(name = "apellido", length = 45, nullable = false)
    private String apellido;
    @Column(name = "telefono", nullable = false)
    private long telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_maestro", nullable = false)
    private EstadoMaestro estadoMaestro;

    public enum EstadoMaestro{
        Asignado_A_Proyecto, Disponible, Desvinculado, Incapacitado
    }
    @Column(name = "salario", nullable = false)
    private long salario;

    @Temporal(TemporalType.DATE)
    private Date fechaVinculacion;

    @Temporal(TemporalType.DATE)
    private Date fechaDesvinculacion;

    // Métodos helper para manejar la relación bidireccional
    public void addAsignacion(AsignacionMaestroModel asignacion) {
        this.asignaciones.add(asignacion);
        asignacion.setMaestro(this);
    }

    public void removeAsignacion(AsignacionMaestroModel asignacion) {
        this.asignaciones.remove(asignacion);
        asignacion.setMaestro(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaestroModel that = (MaestroModel) o;
        return Objects.equals(id_maestros_de_obra, that.id_maestros_de_obra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_maestros_de_obra); // Evita incluir colecciones
    }

    public int getId_maestros_de_obra() {
        return id_maestros_de_obra;
    }

    public void setId_maestros_de_obra(int id_maestros_de_obra) {
        this.id_maestros_de_obra = id_maestros_de_obra;
    }

    public Set<AsignacionMaestroModel> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(Set<AsignacionMaestroModel> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
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

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public EstadoMaestro getEstadoMaestro() {
        return estadoMaestro;
    }

    public void setEstadoMaestro(EstadoMaestro estadoMaestro) {
        this.estadoMaestro = estadoMaestro;
    }

    public long getSalario() {
        return salario;
    }

    public void setSalario(long salario) {
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

