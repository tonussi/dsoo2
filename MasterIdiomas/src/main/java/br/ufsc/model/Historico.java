package br.ufsc.model;

import br.ufsc.constant.*;
import javax.persistence.*;

@Entity
public class Historico {

    public Historico() {
    }
    
    public Historico(Idioma idioma, Integer nivel, Estudante estudante,
            Avaliacao avaliacao, EstadoEstudante estadoEstudante) throws Exception {
        this.idioma = idioma;
        this.nivel = nivel;
        this.estudante = estudante;
        if (avaliacao.equals(Avaliacao.NAO_AVALIADO) && estadoEstudante.equals(EstadoEstudante.FINALIZADO)) {
            throw new Exception("NÃO PODE HAVER ESTUDANTE NÃO_AVALIADO E FINALIZADO AO MESMO TEMPO!");
        }
        this.estadoEstudante = estadoEstudante;
        this.avaliacao = avaliacao;
    }

    @Id
    @GeneratedValue
    private Integer id;

    private Integer nivel;

    @OneToOne
    private Idioma idioma;

    @ManyToOne
    private Estudante estudante;

    @Enumerated(EnumType.STRING)
    private Avaliacao avaliacao;

    @Enumerated(EnumType.STRING)
    private EstadoEstudante estadoEstudante;

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public EstadoEstudante getEstadoEstudante() {
        return estadoEstudante;
    }

    public void setEstadoEstudante(EstadoEstudante estadoEstudante) {
        this.estadoEstudante = estadoEstudante;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.nivel != null ? this.nivel.hashCode() : 0);
        hash = 83 * hash + (this.idioma != null ? this.idioma.hashCode() : 0);
        hash = 83 * hash + (this.estudante != null ? this.estudante.hashCode() : 0);
        hash = 83 * hash + (this.avaliacao != null ? this.avaliacao.hashCode() : 0);
        hash = 83 * hash + (this.estadoEstudante != null ? this.estadoEstudante.hashCode() : 0);
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
        final Historico other = (Historico) obj;
        if (this.nivel != other.nivel && (this.nivel == null || !this.nivel.equals(other.nivel))) {
            return false;
        }
        if (this.idioma != other.idioma && (this.idioma == null || !this.idioma.equals(other.idioma))) {
            return false;
        }
        if (this.estudante != other.estudante && (this.estudante == null || !this.estudante.equals(other.estudante))) {
            return false;
        }
        if (this.avaliacao != other.avaliacao) {
            return false;
        }
        if (this.estadoEstudante != other.estadoEstudante) {
            return false;
        }
        return true;
    }

}
