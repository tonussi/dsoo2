package learning.encontreitroquei.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 40, unique = true)
    private String nome;

    @Column(length = 20, unique = true)
    private String cpf;

    @Column(length = 50, unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(length = 20, unique = true)
    private String telefone;

    private String address;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messages;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Trade> trades = new HashSet<>();

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime = new Date();

    public User() {
    }

    public Set<Trade> getTrades() {
        return trades;
    }

    public void setTrades(Set<Trade> trades) {
        this.trades = trades;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Cliente [\nid = " + id + ",\nnome= " + nome + ",\ncpf = " + cpf + ",\nemail = " + email + ",\ndata_nascimento = "
                + birthdate + ",\ntelefone =" + telefone + ",\nendr =" + address + ",\nsenha =" + password + "\n]";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.cpf != null ? this.cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.cpf == null) ? (other.cpf != null) : !this.cpf.equals(other.cpf)) {
            return false;
        }
        return true;
    }
}
