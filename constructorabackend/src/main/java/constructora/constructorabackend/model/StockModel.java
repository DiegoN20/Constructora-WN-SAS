package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class StockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private Integer idStock;

    @ManyToOne
    @JoinColumn(name = "proyectos_id_proyectos", nullable = false)
    private ProyectModel proyecto;

    @ManyToOne
    @JoinColumn(name = "insumos_id_insumos", nullable = false)
    private InsumoModel insumo;

    @Column(name = "cantidad_invertida", nullable = false)
    private Integer cantidadInvertida = 0;

    @Column(name = "cantidad_restante", nullable = false)
    private Integer cantidadRestante;
}
