package br.ufsc.model;

import br.ufsc.constant.*;
import java.util.*;
import javax.persistence.*;

@Entity
public class Professor extends Pessoa {

    public Professor() {
    }

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<Idioma> idiomas;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<Turma> turmas;

    @Enumerated(EnumType.STRING)
    private Autoridade autoridade;

    public HashSet<Turma> getTurmas() {
        return new HashSet<Turma>(turmas);
    }

    public void setTurmas(HashSet<Turma> turmas) {
        this.turmas.addAll(turmas);
    }

    public void setTurmas(Turma turma) {
        this.turmas.add(turma);
    }    

    public Autoridade getAutoridade() {
        return autoridade;
    }

    public void setAutoridade(Autoridade autoridade) {
        this.autoridade = autoridade;
    }

    public HashSet<Idioma> getIdiomas() {
        return new HashSet<Idioma>(idiomas);
    }

    public ArrayList<String> getNomesIdiomasSendoEnsinados() {
        ArrayList<String> resultado = new ArrayList<String>();
        for (Idioma idioma : getIdiomas()) {
            resultado.add(idioma.getNome());
        }
        return resultado;
    }

    public void setIdiomas(HashSet<Idioma> idiomas) {
        this.idiomas = idiomas;
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
