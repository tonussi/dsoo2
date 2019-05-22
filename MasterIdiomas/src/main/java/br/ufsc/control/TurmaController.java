package br.ufsc.control;

import br.ufsc.model.*;
import br.ufsc.service.*;
import java.util.*;

public class TurmaController {

    TurmaService ts;
    HistoricoService hs;
    PessoaService ps;

    public TurmaController() {
        this.ts = new TurmaService();
        this.ps = new PessoaService();
        this.hs = new HistoricoService();
    }

    public ArrayList<Object[]> getTurmas() {
        List<Turma> turmas = ts.findAll(Turma.class);
        ArrayList<Object[]> resultado = new ArrayList<Object[]>();
        if (turmas.size() > 0) {
            for (Turma turma : turmas) {
                Object[] aux = new Object[]{
                    turma.toString()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }

    public ArrayList<String[]> getTurmasParaUmEstudanteSeMatricular(String cpf) {
        List<Turma> turmas = hs.findAll(Turma.class);
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        if (turmas.size() > 0) {
            for (Turma turma : turmas) {
                String[] aux = new String[]{
                    String.valueOf(turma.getId()),
                    turma.getHorarios().toString(),
                    turma.getIdioma().getNome(),
                    String.valueOf(turma.getNivel()),
                    turma.getProfessor().getNome()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }
}
