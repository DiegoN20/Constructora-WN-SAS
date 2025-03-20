package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "avance_por_pisos")
public class AvancePorPisoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avance_Por_Piso")
    private Integer idAvancePorPiso;

    @ManyToOne
    @JoinColumn(name = "proyectos_id_proyectos", nullable = false)
    private ProyectModel proyecto;

    @ManyToOne
    @JoinColumn(name = "insumos_id_insumos", nullable = false)
    private InsumoModel insumo;

    @Column(name = "numero_piso", nullable = false)
    private Integer numeroPiso;

    @Column(name = "cantidad_comprada", nullable = false)
    private Integer cantidadComprada;

    @Column(name = "costo_insumos", nullable = false)
    private Integer costoInsumos;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;
}
