package constructora.constructorabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "insumos")
@Data
@NoArgsConstructor
public class InsumoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumos", nullable = false)
    private int idInsumos;

    @OneToMany(mappedBy = "insumo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<InventarioInicialModel> inicial = new HashSet<>();

    @OneToMany(mappedBy = "insumo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<AvancePorPisoModel> avance = new HashSet<>();

    @OneToMany(mappedBy = "insumo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<StockModel> stock = new HashSet<>();

    @Column(name = "nombre_insumo", length = 45, nullable = false)
    private String nombreInsumo;
    @Column(name = "descripcion", length = 45)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;

    public enum Tipo{
        Herramienta, Material
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsumoModel that = (InsumoModel) o;
        return Objects.equals(idInsumos, that.idInsumos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInsumos); // Evita incluir colecciones
    }

    public int getIdInsumos() {
        return idInsumos;
    }

    public void setIdInsumos(int idInsumos) {
        this.idInsumos = idInsumos;
    }

    public Set<InventarioInicialModel> getInicial() {
        return inicial;
    }

    public void setInicial(Set<InventarioInicialModel> inicial) {
        this.inicial = inicial;
    }

    public Set<AvancePorPisoModel> getAvance() {
        return avance;
    }

    public void setAvance(Set<AvancePorPisoModel> avance) {
        this.avance = avance;
    }

    public Set<StockModel> getStock() {
        return stock;
    }

    public void setStock(Set<StockModel> stock) {
        this.stock = stock;
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
