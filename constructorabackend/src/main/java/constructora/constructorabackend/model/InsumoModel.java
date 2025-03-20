package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insumos")
@Data
@NoArgsConstructor
public class InsumoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumos", nullable = false)
    private int id_insumos;

    @Column(name = "nombre_insumo", length = 45, nullable = false)
    private String nombreInsumo;
    @Column(name = "descripcion", length = 45)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private enum Tipo{
        Herramienta, Material
    }

    public int getId_insumos() {
        return id_insumos;
    }

    public void setId_insumos(int id_insumos) {
        this.id_insumos = id_insumos;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
