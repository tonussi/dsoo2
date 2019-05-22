package learning.encontreitroquei.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length=140)
    private String mensagem;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime = new Date();

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.fromUser.getId());
        hash = 67 * hash + Objects.hashCode(this.toUser.getId());
        hash = 67 * hash + Objects.hashCode(this.createDateTime);
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
        final Message other = (Message) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fromUser.getId(), other.fromUser.getId())) {
            return false;
        }
        if (!Objects.equals(this.toUser.getId(), other.toUser.getId())) {
            return false;
        }
        if (!Objects.equals(this.createDateTime, other.createDateTime)) {
            return false;
        }
        return true;
    }
}
