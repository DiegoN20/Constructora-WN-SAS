package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "servicios_externos")
public class ServicioExternoModel {

    @EmbeddedId
    private ServicioExternoId id = new ServicioExternoId();

    @ManyToOne
    @MapsId("proveedoresIdProveedores") // Coincide con el campo en ServicioExternoId
    @JoinColumn(name = "proveedores_id_proveedores", nullable = false)
    private ProveedorModel proveedor;

    @ManyToOne
    @MapsId("proyectosIdProyectos") // Coincide con el campo en ServicioExternoId
    @JoinColumn(name = "proyectos_id_proyectos", nullable = false)
    private ProyectModel proyecto;

    @Column(name = "descripcion_servicio", nullable = false, length = 200)
    private String descripcionServicio;

    @Column(name = "costo", nullable = false)
    private long costo;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "persona_encargada", length = 100)
    private String personaEncargada;

    @Column(name = "telefono")
    private long telefono;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicioExternoModel that = (ServicioExternoModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ServicioExternoId getId() {
        return id;
    }

    public void setId(ServicioExternoId id) {
        this.id = id;
    }

    public ProveedorModel getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorModel proveedor) {
        this.proveedor = proveedor;
    }

    public ProyectModel getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectModel proyecto) {
        this.proyecto = proyecto;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public long getCosto() {
        return costo;
    }

    public void setCosto(long costo) {
        this.costo = costo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPersonaEncargada() {
        return personaEncargada;
    }

    public void setPersonaEncargada(String personaEncargada) {
        this.personaEncargada = personaEncargada;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }
}
