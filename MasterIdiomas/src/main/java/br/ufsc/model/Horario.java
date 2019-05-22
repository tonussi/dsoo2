package br.ufsc.model;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Horario {

    public Horario() {
    }

    @Id
    @GeneratedValue
    private Integer id;

    private Integer dia;

    @Temporal(TemporalType.TIME)
    private Date horario;

    public Horario(Integer dia, Date horario) {
        this.dia = dia;
        this.horario = horario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.dia != null ? this.dia.hashCode() : 0);
        hash = 53 * hash + (this.horario != null ? this.horario.hashCode() : 0);
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
        final Horario other = (Horario) obj;
        if (this.horario != other.horario && (this.horario == null || !this.horario.equals(other.horario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "D." + dia + "/H." + horario.getHours() + ':' + horario.getMinutes() + '/';
    }
}
