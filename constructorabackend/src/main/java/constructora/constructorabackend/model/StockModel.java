package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Column(name = "cantidad_total", nullable = false)
    private Integer cantidadTotal = 0;

    @Column(name = "cantidad_invertida", nullable = false)
    private Integer cantidadInvertida = 0;

    @Column(name = "cantidad_restante", nullable = false)
    private Integer cantidadRestante;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockModel that = (StockModel) o;
        return Objects.equals(idStock, that.idStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStock); // Evita incluir colecciones
    }

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public ProyectModel getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectModel proyecto) {
        this.proyecto = proyecto;
    }

    public InsumoModel getInsumo() {
        return insumo;
    }

    public void setInsumo(InsumoModel insumo) {
        this.insumo = insumo;
    }

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public Integer getCantidadInvertida() {
        return cantidadInvertida;
    }

    public void setCantidadInvertida(Integer cantidadInvertida) {
        this.cantidadInvertida = cantidadInvertida;
    }

    public Integer getCantidadRestante() {
        return cantidadRestante;
    }

    public void setCantidadRestante(Integer cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }
}
