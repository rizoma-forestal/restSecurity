
package ar.gob.ambiente.rest.restsecurity.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rincostante
 */
@Entity
@XmlRootElement
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (nullable=false, length=100, unique=true)
    @NotNull(message = "El campo nombreCientifico no puede ser nulo")
    @Size(message = "El campo nombreCientifico no puede tener más de 100 caracteres", min = 1, max = 100)
    private String nombreCientifico;

    @Column (nullable=false, length=50, unique=true)
    @NotNull(message = "El campo nombreVulgar no puede ser nulo")
    @Size(message = "El campo nombreVulgar no puede tener más de 50 caracteres", min = 1, max = 50) 
    private String nombreVulgar;    
    
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo clase no puede ser nulo")
    @Size(message = "El campo clase no puede tener más de 50 caracteres", min = 1, max = 50) 
    private String clase;    
    
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo unidad no puede ser nulo")
    @Size(message = "El campo unidad no puede tener más de 50 caracteres", min = 1, max = 50) 
    private String unidad; 
    
    @Column
    private float total;

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getNombreVulgar() {
        return nombreVulgar;
    }

    public void setNombreVulgar(String nombreVulgar) {
        this.nombreVulgar = nombreVulgar;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.rest.restsecurity.entities.Producto[ id=" + id + " ]";
    }
    
}
