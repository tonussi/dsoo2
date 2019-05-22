package learning.encontreitroquei.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Trade implements Serializable {

    public Trade() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User source;

    @ManyToOne
    private User target;

    @ManyToOne
    private Product desiredProduct;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> offer;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime = new Date();

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getSource() {
        return source;
    }

    public void setSource(User source) {
        this.source = source;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public List<Product> getOffer() {
        return offer;
    }

    public void setOffer(List<Product> offer) {
        this.offer = offer;
    }

    public Product getDesiredProduct() {
        return desiredProduct;
    }

    public void setDesiredProduct(Product desiredProduct) {
        this.desiredProduct = desiredProduct;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.source);
        hash = 97 * hash + Objects.hashCode(this.target);
        hash = 97 * hash + Objects.hashCode(this.desiredProduct);
        hash = 97 * hash + Objects.hashCode(this.offer);
        hash = 97 * hash + Objects.hashCode(this.createDateTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trade other = (Trade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        if (!Objects.equals(this.desiredProduct, other.desiredProduct)) {
            return false;
        }
        if (!Objects.equals(this.offer, other.offer)) {
            return false;
        }
        if (!Objects.equals(this.createDateTime, other.createDateTime)) {
            return false;
        }
        return true;
    }

}
