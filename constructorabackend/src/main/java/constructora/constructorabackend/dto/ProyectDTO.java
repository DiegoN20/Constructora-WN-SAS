package constructora.constructorabackend.dto;

import java.sql.Date;

public class ProyectDTO {
    private int idProyectos;
    private String nombreProyecto;
    private String direccion;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private long presupuestoPrevisto;
    private long presupuestoActual;
    private long presupuestoRestante;
    private String estadoProyecto;
    private int cantidadPisos;

    public ProyectDTO() {
    }

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

    public String getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(String estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public int getCantidadPisos() {
        return cantidadPisos;
    }

    public void setCantidadPisos(int cantidadPisos) {
        this.cantidadPisos = cantidadPisos;
    }
}
