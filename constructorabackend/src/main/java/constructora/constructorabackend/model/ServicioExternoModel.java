package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

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
    private Integer costo;

    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    @ManyToOne
    @JoinColumn(name = "persona_encargada_id_persona_encargada", nullable = false)
    private PersonaEncargadaModel personaEncargada;
}
