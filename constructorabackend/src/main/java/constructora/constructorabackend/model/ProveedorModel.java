package constructora.constructorabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
public class ProveedorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedores")
    private int idProveedores;

    @OneToMany(mappedBy = "proveedor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<ServicioExternoModel> servicioexterno = new HashSet<>();

    @Column(name = "nombre_proveedor", length = 45,nullable = false)
    private String nombreProveedor;
    @Column(name = "tipo_servicio", length = 45, nullable = false)
    private String tipoServicio;
    @Column(name = "correo", length = 45)
    private String correo;
    @Column(name = "telefono")
    private long  telefono;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedorModel that = (ProveedorModel) o;
        return Objects.equals(idProveedores, that.idProveedores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProveedores);
    }

    public int getIdProveedores() {
        return idProveedores;
    }

    public void setIdProveedores(int idProveedores) {
        this.idProveedores = idProveedores;
    }

    public Set<ServicioExternoModel> getServicioexterno() {
        return servicioexterno;
    }

    public void setServicioexterno(Set<ServicioExternoModel> servicioexterno) {
        this.servicioexterno = servicioexterno;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }
}
