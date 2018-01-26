
package ar.gob.ambiente.rest.restsecurity.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rincostante
 */
@Entity
@XmlRootElement
public class Guia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
    
    @Column (nullable=false, length=20, unique=true)
    @NotNull(message = "El campo codigo no puede ser nulo")
    @Size(message = "El campo codigo no puede tener más de 20 caracteres", min = 1, max = 20)    
    private String codigo;
    
    @ManyToOne
    @JoinColumn(name="destino_id")
    private Persona destino;    
    
    @ManyToOne
    @JoinColumn(name="titular_id")
    private Persona titular;        
    
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo provincia no puede ser nulo")
    @Size(message = "El campo provincia no puede tener más de 50 caracteres", min = 1, max = 50)  
    private String provincia;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "guia_id", referencedColumnName = "id")
    private List<Producto> items;         

    public List<Producto> getItems() {
        return items;
    }

    public void setItems(List<Producto> items) {
        this.items = items;
    }

    public Persona getDestino() {
        return destino;
    }

    public void setDestino(Persona destino) {
        this.destino = destino;
    }

    public Persona getTitular() {
        return titular;
    }

    public void setTitular(Persona titular) {
        this.titular = titular;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Guia(){
        items = new ArrayList<>();
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
        if (!(object instanceof Guia)) {
            return false;
        }
        Guia other = (Guia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.rest.restsecurity.entities.Guia[ id=" + id + " ]";
    }
    
}
