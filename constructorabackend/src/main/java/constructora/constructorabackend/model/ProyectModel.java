package constructora.constructorabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constructora.constructorabackend.seguridad.model.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
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

    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<AsignacionMaestroModel> asignaciones = new HashSet<>();

    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<ServicioExternoModel> servicios = new HashSet<>();

    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<InventarioInicialModel> inicial = new HashSet<>();

    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<AvancePorPisoModel> avance = new HashSet<>();

    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<StockModel> stock = new HashSet<>();

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

    @Column(name = "presupuesto_previsto", nullable = false)
    private long presupuestoPrevisto;

    @Column(name = "presupuesto_actual", nullable = false)
    private long presupuestoActual;

    @Column(name = "presupuesto_restante", nullable = false)
    private long presupuestoRestante;

    @Enumerated(EnumType.STRING)
    private EstadoProyecto estadoProyecto;

    public enum EstadoProyecto {
        En_Proceso, Finalizado, Suspendido
    }
    @Column(name = "cantidad_pisos", nullable = false)
    private int cantidadPisos;

    @ManyToOne
    @JoinColumn(name = "usuarios_id_usuarios", nullable = false)
    private UserModel usuario;

    // Métodos helper para manejar la relación bidireccional
    public void addAsignacion(AsignacionMaestroModel asignacion) {
        this.asignaciones.add(asignacion);
        asignacion.setProyecto(this);
    }

    public void removeAsignacion(AsignacionMaestroModel asignacion) {
        this.asignaciones.remove(asignacion);
        asignacion.setProyecto(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProyectModel that = (ProyectModel) o;
        return Objects.equals(idProyectos, that.idProyectos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProyectos); // Evita incluir colecciones
    }

    public int getIdProyectos() {
        return idProyectos;
    }

    public void setIdProyectos(int idProyectos) {
        this.idProyectos = idProyectos;
    }

    public Set<AsignacionMaestroModel> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(Set<AsignacionMaestroModel> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public Set<ServicioExternoModel> getServicios() {
        return servicios;
    }

    public void setServicios(Set<ServicioExternoModel> servicios) {
        this.servicios = servicios;
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

    public long getPresupuestoPrevisto() {
        return presupuestoPrevisto;
    }

    public void setPresupuestoPrevisto(long presupuestoPrevisto) {
        this.presupuestoPrevisto = presupuestoPrevisto;
    }

    public long getPresupuestoActual() {
        return presupuestoActual;
    }

    public void setPresupuestoActual(long presupuestoActual) {
        this.presupuestoActual = presupuestoActual;
    }

    public long getPresupuestoRestante() {
        return presupuestoRestante;
    }

    public void setPresupuestoRestante(long presupuestoRestante) {
        this.presupuestoRestante = presupuestoRestante;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
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
