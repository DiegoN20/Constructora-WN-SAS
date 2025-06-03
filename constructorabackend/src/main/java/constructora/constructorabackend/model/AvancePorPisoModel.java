package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "avance_por_pisos")
public class AvancePorPisoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avance_Por_Piso")
    private int idAvancePorPiso;

    @ManyToOne
    @JoinColumn(name = "proyectos_id_proyectos", nullable = false)
    private ProyectModel proyecto;

    @ManyToOne
    @JoinColumn(name = "insumos_id_insumos", nullable = false)
    private InsumoModel insumo;

    @Column(name = "numero_piso", nullable = false)
    private int numeroPiso;

    @Column(name = "cantidad_comprada", nullable = false)
    private int cantidadComprada;

    @Column(name = "costo_insumos", nullable = false)
    private int costoInsumos;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    @Column(name = "cantidad_usada", nullable = false)
    private int cantidadUsada;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvancePorPisoModel that = (AvancePorPisoModel) o;
        return Objects.equals(idAvancePorPiso, that.idAvancePorPiso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAvancePorPiso);
    }

    public Integer getIdAvancePorPiso() {
        return idAvancePorPiso;
    }

    public void setIdAvancePorPiso(int idAvancePorPiso) {
        this.idAvancePorPiso = idAvancePorPiso;
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

    public int getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public int getCostoInsumos() {
        return costoInsumos;
    }

    public void setCostoInsumos(int costoInsumos) {
        this.costoInsumos = costoInsumos;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }
}
