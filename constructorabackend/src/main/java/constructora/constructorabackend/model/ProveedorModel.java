package constructora.constructorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
public class ProveedorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedores")
    private int idProveedores;

    @Column(name = "nombre_proveedor", length = 45)
    private String nombreProveedor;
    @Column(name = "tipo_servicio", length = 45)
    private String tipoServicio;
    @Column(name = "correo", length = 45)
    private String correo;
    @Column(name = "telefono")
    private Integer  telefono;

    public int getIdProveedores() {
        return idProveedores;
    }

    public void setIdProveedores(int idProveedores) {
        this.idProveedores = idProveedores;
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

    public Integer  getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer  telefono) {
        this.telefono = telefono;
    }
}
