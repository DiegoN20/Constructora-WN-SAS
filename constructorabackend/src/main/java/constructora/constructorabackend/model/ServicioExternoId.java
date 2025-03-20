package constructora.constructorabackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ServicioExternoId implements Serializable {

    @Column(name = "proveedores_id_proveedores") // Este nombre debe coincidir con la columna de la tabla
    private Integer proveedoresIdProveedores;

    @Column(name = "proyectos_id_proyectos") // Este nombre debe coincidir con la columna de la tabla
    private Integer proyectosIdProyectos;

    // Getters y Setters
    public Integer getProveedoresIdProveedores() {
        return proveedoresIdProveedores;
    }

    public void setProveedoresIdProveedores(Integer proveedoresIdProveedores) {
        this.proveedoresIdProveedores = proveedoresIdProveedores;
    }

    public Integer getProyectosIdProyectos() {
        return proyectosIdProyectos;
    }

    public void setProyectosIdProyectos(Integer proyectosIdProyectos) {
        this.proyectosIdProyectos = proyectosIdProyectos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicioExternoId that = (ServicioExternoId) o;
        return Objects.equals(proveedoresIdProveedores, that.proveedoresIdProveedores)
                && Objects.equals(proyectosIdProyectos, that.proyectosIdProyectos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proveedoresIdProveedores, proyectosIdProyectos);
    }
}
