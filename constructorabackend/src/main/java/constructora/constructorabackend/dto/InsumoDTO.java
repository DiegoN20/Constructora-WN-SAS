package constructora.constructorabackend.dto;

public class InsumoDTO {
    private int idInsumos;
    private String nombreInsumo;
    private String descripcion;
    private String tipo;

    public InsumoDTO() {
    }

    public int getIdInsumos() {
        return idInsumos;
    }

    public void setIdInsumos(int idInsumos) {
        this.idInsumos = idInsumos;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
