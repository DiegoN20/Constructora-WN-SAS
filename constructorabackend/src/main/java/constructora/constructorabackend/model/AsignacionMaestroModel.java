package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

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
    private Date fechaAsignacion;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoAsignacion estado;

    public enum EstadoAsignacion {
        Activo, Finalizado, Suspendido
    }
}
