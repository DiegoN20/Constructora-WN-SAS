package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "asignaciones_maestros")
public class AsignacionMaestroModel {

    @EmbeddedId
    private AsignacionMaestroId id = new AsignacionMaestroId();

    @ManyToOne
    @MapsId("idMaestro")
    @JoinColumn(name = "id_maestro", nullable = false)
    private MaestroModel maestro;

    @ManyToOne
    @MapsId("idProyecto")
    @JoinColumn(name = "id_proyecto", nullable = false)
    private ProyectModel proyecto;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoAsignacion estadoAsignacion;

    public enum EstadoAsignacion {
        Activo, Finalizado, Suspendido
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsignacionMaestroModel that = (AsignacionMaestroModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Evita incluir colecciones
    }

    public AsignacionMaestroId getId() {
        return id;
    }

    public void setId(AsignacionMaestroId id) {
        this.id = id;
    }

    public MaestroModel getMaestro() {
        return maestro;
    }

    public void setMaestro(MaestroModel maestro) {
        this.maestro = maestro;
    }

    public ProyectModel getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectModel proyecto) {
        this.proyecto = proyecto;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoAsignacion getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(EstadoAsignacion estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
    }
}
