package constructora.constructorabackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AsignacionMaestroId implements Serializable {

    public AsignacionMaestroId() {
    }

    @Column(name = "id_maestro")
    private Integer idMaestro;

    @Column(name = "id_proyecto")
    private Integer idProyecto;

    // Getters y Setters
    public Integer getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(Integer idMaestro) {
        this.idMaestro = idMaestro;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsignacionMaestroId that = (AsignacionMaestroId) o;
        return Objects.equals(idMaestro, that.idMaestro) && Objects.equals(idProyecto, that.idProyecto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMaestro, idProyecto);
    }
}
