package learning.encontreitroquei.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import learning.encontreitroquei.util.Util;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 50)
    private String nome;

    @Column
    private Float preco;

    @Column(length = 20)
    private String console;

    @Column(length = 288)
    private String descricao;

    @Temporal(TemporalType.DATE)
    private Date dataCompra;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(length = 100)
    private String graphic;

    public Product() {
    }

    public String getGraphic() {
        return graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setDataCompraStringFormat(String dataCompra) {
        this.dataCompra = Util.parseDate(dataCompra);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Product{" + "nome=" + nome + ", preco=" + preco + ", owner=" + owner + '}';
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
