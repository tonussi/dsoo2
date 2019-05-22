package br.ufsc.model;

import br.ufsc.constant.Autoridade;
import javax.persistence.*;

@Entity
public class Administrador extends Pessoa {

    public Administrador() {
    }

    @Enumerated(EnumType.STRING)
    private Autoridade autoridade;

    public Autoridade getAutoridade() {
        return autoridade;
    }

    public void setAutoridade(Autoridade autoridade) {
        this.autoridade = autoridade;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
