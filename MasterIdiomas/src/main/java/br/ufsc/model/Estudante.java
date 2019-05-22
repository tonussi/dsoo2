package br.ufsc.model;

import br.ufsc.constant.*;
import java.util.*;
import javax.persistence.*;

@Entity
public class Estudante extends Pessoa {

    public Estudante() {
    }

    public Estudante(Autoridade autoridade) {
        this.autoridade = autoridade;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<Historico> historico;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "Matricula")
    private Set<Turma> turmas;

    @Enumerated(EnumType.STRING)
    private Autoridade autoridade;

    public HashSet<Historico> getHistorico() {
        return new HashSet<Historico>(historico);
    }

    public void setHistorico(HashSet<Historico> historico) {
        this.historico = historico;
    }

    public HashSet<Turma> getTurmas() {
        return new HashSet<Turma>(turmas);
    }

    public void setTurmas(HashSet<Turma> turmas) {
//        HistoricoService hs = new HistoricoService();
//        for (Turma turma : turmas) {
//            if (turma.getNivel() > 1) {
//                Historico ultimoHistorico = (Historico) hs.findHistorico(turma.getIdioma(), turma.getNivel() - 1, this);
//                if (ultimoHistorico == null) {
//                    throw new Exception("Estudante precisa ter nível suficiente e aprovado para cursar nessa turma.");
//                }
//                if (ultimoHistorico.getNivel() + 1 != turma.getNivel()) {
//                    throw new Exception("Estudante precisa ter nível suficiente e aprovado para cursar nessa turma.");
//                }
//            }
//        }
        this.turmas = turmas;
    }

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
