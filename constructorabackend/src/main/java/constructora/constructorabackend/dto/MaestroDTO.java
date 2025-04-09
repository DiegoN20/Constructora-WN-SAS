package constructora.constructorabackend.dto;

import java.sql.Date;

public class MaestroDTO {
    private int id_maestros_de_obra;
    private long cedula;
    private String nombre;
    private String apellido;
    private long telefono;
    private String estadoMaestro;
    private long salario;
    private Date fechaVinculacion;
    private Date fechaDesvinculacion;

    public MaestroDTO() {
    }

    public int getId_maestros_de_obra() {
        return id_maestros_de_obra;
    }

    public void setId_maestros_de_obra(int id_maestros_de_obra) {
        this.id_maestros_de_obra = id_maestros_de_obra;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getEstadoMaestro() {
        return estadoMaestro;
    }

    public void setEstadoMaestro(String estadoMaestro) {
        this.estadoMaestro = estadoMaestro;
    }

    public long getSalario() {
        return salario;
    }

    public void setSalario(long salario) {
        this.salario = salario;
    }

    public Date getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(Date fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    public Date getFechaDesvinculacion() {
        return fechaDesvinculacion;
    }

    public void setFechaDesvinculacion(Date fechaDesvinculacion) {
        this.fechaDesvinculacion = fechaDesvinculacion;
    }
}
