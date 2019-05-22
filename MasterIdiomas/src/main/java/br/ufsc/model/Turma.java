package br.ufsc.model;

import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=false)
public class Turma {

    public Turma() {
    }

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Horario> horarios;

    @OneToOne
    private Idioma idioma;

    @OneToOne
    private Professor professor;

    private Integer nivel;

    public Turma(HashSet<Horario> horarios, Idioma idioma, Professor professor,
            Integer nivel) throws Exception {

        if (horarios.isEmpty()) {
            throw new Exception("Precisa haver ao menos 1 horário para essa disciplina.");
        }

        this.horarios = horarios;

        if (nivel >= 1 && nivel <= 5) {
            this.nivel = nivel;
        } else {
            throw new Exception("Nível precisa ser entre 1 e 5, "
                    + "incluindo 1 e 5.");
        }

        for (Idioma auxIdioma : professor.getIdiomas()) {
            if (auxIdioma.equals(idioma)) {
                this.professor = professor;
                this.idioma = idioma;
            }
        }

        if (this.professor == null) {
            throw new Exception("Esse idioma " + idioma.getNome()
                    + ", não está associado ao professor de nome "
                    + professor.getNome());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashSet<Horario> getHorarios() {
        return new HashSet<Horario>(horarios);
    }

    public void setHorarios(HashSet<Horario> horarios) {
        this.horarios.addAll(horarios);
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

//    public HashSet<Estudante> getEstudantes() {
//        return new HashSet<Estudante>(estudantes);
//    }
//
//    public void setEstudantes(HashSet<Estudante> estudantes) {
//        this.estudantes = estudantes;
//    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.horarios != null ? this.horarios.hashCode() : 0);
        hash = 41 * hash + (this.idioma != null ? this.idioma.hashCode() : 0);
        hash = 41 * hash + (this.professor != null ? this.professor.hashCode() : 0);
        hash = 41 * hash + (this.nivel != null ? this.nivel.hashCode() : 0);
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
        final Turma other = (Turma) obj;
        if (this.horarios != other.horarios && (this.horarios == null || !this.horarios.equals(other.horarios))) {
            return false;
        }
        if (this.idioma != other.idioma && (this.idioma == null || !this.idioma.equals(other.idioma))) {
            return false;
        }
        if (this.professor != other.professor && (this.professor == null || !this.professor.equals(other.professor))) {
            return false;
        }
        if (this.nivel != other.nivel && (this.nivel == null || !this.nivel.equals(other.nivel))) {
            return false;
        }
        return true;
    }
}
