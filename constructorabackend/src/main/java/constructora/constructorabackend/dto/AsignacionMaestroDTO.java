package constructora.constructorabackend.dto;

import java.sql.Date;
import java.time.LocalDate;

public class AsignacionMaestroDTO {

    private Integer maestro;
    private String nombreMaestro;
    private Integer proyecto;
    private String nombreProyecto;
    private LocalDate fechaAsignacion;
    private LocalDate fechaFin;
    private String estadoAsignacion;

    public AsignacionMaestroDTO() {
    }

    public Integer getMaestro() {
        return maestro;
    }

    public void setMaestro(Integer maestro) {
        this.maestro = maestro;
    }

    public String getNombreMaestro() {
        return nombreMaestro;
    }

    public void setNombreMaestro(String nombreMaestro) {
        this.nombreMaestro = nombreMaestro;
    }

    public Integer getProyecto() {
        return proyecto;
    }

    public void setProyecto(Integer proyecto) {
        this.proyecto = proyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(String estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
    }
}