package constructora.constructorabackend.model;

import constructora.constructorabackend.seguridad.model.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "proyectos")
public class ProyectModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyectos")
    private int idProyectos;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private Set<AsignacionMaestroModel> asignaciones = new HashSet<>();


    @Column(name = "nombre_proyecto", length = 45)
    private String nombreProyecto;
    @Column(name = "direccion", length = 45)
    private String direccion;
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "presupuesto", nullable = false)
    private int presupuesto;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado {
        En_Proceso, Finalizado, Suspendido
    }
    @Column(name = "cantidad_pisos", nullable = false)
    private int cantidadPisos;

    @ManyToOne
    @JoinColumn(name = "usuarios_id_usuarios", nullable = false)
    private UserModel usuario;

    public int getIdProyectos() {
        return idProyectos;
    }

    public void setIdProyectos(int idProyectos) {
        this.idProyectos = idProyectos;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCantidadPisos() {
        return cantidadPisos;
    }

    public void setCantidadPisos(int cantidadPisos) {
        this.cantidadPisos = cantidadPisos;
    }

    public UserModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;
    }
}
