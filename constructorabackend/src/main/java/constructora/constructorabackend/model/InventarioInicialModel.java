package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario_inicial")
@Data
@NoArgsConstructor
public class InventarioInicialModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario_inicial")
    private Integer idInventarioInicial;

    @ManyToOne
    @JoinColumn(name = "proyectos_id_proyectos", nullable = false)
    private ProyectModel proyecto;

    @ManyToOne
    @JoinColumn(name = "insumos_id_insumos", nullable = false)
    private InsumoModel insumo;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", nullable = false)
    private Integer precio;

    @Column(name = "unidad", nullable = false, length = 25)
    private String unidad;

    public Integer getIdInventarioInicial() {
        return idInventarioInicial;
    }

    public void setIdInventarioInicial(Integer idInventarioInicial) {
        this.idInventarioInicial = idInventarioInicial;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
