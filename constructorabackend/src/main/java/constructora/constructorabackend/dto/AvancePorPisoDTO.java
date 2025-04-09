package constructora.constructorabackend.dto;

import java.time.LocalDate;

public class AvancePorPisoDTO {
    private Integer idAvance;
    private Integer insumo;
    private Integer proyecto;
    private String nombreInsumo;
    private String nombreProyecto;
    private int numeroPiso;
    private int cantidadComprada;
    private int costoInsumo;
    private LocalDate fechaCompra;
    private int cantidadUsada;

    public AvancePorPisoDTO() {
    }

    public Integer getIdAvance() {
        return idAvance;
    }

    public void setIdAvance(Integer idAvance) {
        this.idAvance = idAvance;
    }

    public Integer getInsumo() {
        return insumo;
    }

    public void setInsumo(Integer insumo) {
        this.insumo = insumo;
    }

    public Integer getProyecto() {
        return proyecto;
    }

    public void setProyecto(Integer proyecto) {
        this.proyecto = proyecto;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public int getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public int getCostoInsumo() {
        return costoInsumo;
    }

    public void setCostoInsumo(int costoInsumo) {
        this.costoInsumo = costoInsumo;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }
}
