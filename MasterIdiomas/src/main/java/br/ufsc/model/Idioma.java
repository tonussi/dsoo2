package br.ufsc.model;

import javax.persistence.*;

@Entity
public class Idioma {

    public Idioma() {
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String nome;

//    @OneToMany
//    private Set<Professor> professores;
//
//    @OneToMany
//    private Set<Turma> turmas;

//    public HashSet<Professor> getProfessores() {
//        return new HashSet(professores);
//    }
//
//    public void setProfessores(HashSet<Professor> professores) {
//        this.professores = professores;
//    }
//
//    public HashSet<Turma> getTurmas() {
//        return new HashSet(turmas);
//    }
//
//    public void setTurmas(HashSet<Turma> turmas) {
//        this.turmas = turmas;
//    }

    public Idioma(String nome) {
        this.nome = nome.toUpperCase();
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
        this.nome = nome.toUpperCase();
    }

//    public HashSet<Professor> getProfessores() {
//        return new HashSet<Professor>(professores);
//    }
//
//    public void setProfessores(Set<Professor> professores) {
//        this.professores = professores;
//    }

//    public HashSet<Turma> getTurmas() {
//        return new HashSet<Turma>(turmas);
//    }
//
//    public void setTurmas(HashSet<Turma> turmas) {
//        this.turmas = turmas;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.nome != null ? this.nome.hashCode() : 0);
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
        final Idioma other = (Idioma) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
}
