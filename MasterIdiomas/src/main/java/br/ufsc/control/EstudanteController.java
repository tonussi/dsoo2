package br.ufsc.control;

import br.ufsc.service.*;
import java.util.*;
import br.ufsc.model.*;

public class EstudanteController {

    PessoaService ps;
    HistoricoService hs;
    MatriculaService ms;
    TurmaService ts;

    public EstudanteController() {
        ps = new PessoaService();
        hs = new HistoricoService();
        ms = new MatriculaService();
        ts = new TurmaService();
    }

    public String getNome(String cpf) {
        return ps.findByCpf(cpf).getNome();
    }

    public ArrayList<String[]> getCursosFinalizados(String cpf) {
        List<Object[]> finalizados = hs.findAllCursosFinalizados(cpf);
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        if (finalizados.size() > 0) {
            for (Object[] curso : finalizados) {
                Historico c = (Historico) curso[0];
                String[] aux = new String[]{
                    c.getIdioma().getNome(),
                    c.getNivel().toString(),
                    c.getEstadoEstudante().name(),
                    c.getAvaliacao().name()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }

    public ArrayList<String[]> getCursosEmAndamento(String cpf) {
        List<Object[]> andamento = hs.findAllCursosEmAndamento(cpf);
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        if (andamento.size() > 0) {
            for (Object[] curso : andamento) {
                Turma c = (Turma) curso[1];
                String[] aux = new String[]{
                    c.getId().toString(),
                    c.getIdioma().getNome(),
                    c.getNivel().toString(),
                    c.getHorarios().toString()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }

    public ArrayList<String[]> getEstudantes() {
        List<Turma> turmas = ps.findAll(Estudante.class);
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        if (turmas.size() > 0) {
            for (Turma turma : turmas) {
                String[] aux = new String[]{
                    turma.toString()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }

    public ArrayList<String[]> getEstudantesMatriculados(Integer id) {
        List<Object[]> estudantesTurma = ts.findEstudantesJoinTurma(id);
        ArrayList<String[]> resultado = new ArrayList<String[]>();

        if (estudantesTurma.size() > 0) {
            for (Object[] o : estudantesTurma) {
                Estudante e = (Estudante) o[0];
                String[] aux = new String[]{
                    e.getCpf(),
                    e.getNome()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }
}
