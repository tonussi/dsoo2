package br.ufsc.control;

import br.ufsc.constant.Avaliacao;
import br.ufsc.constant.EstadoEstudante;
import br.ufsc.model.*;
import br.ufsc.service.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatriculaController {

    AnyService as = new AnyService();
    PessoaService ps = new PessoaService();
    IdiomaService is = new IdiomaService();
    MatriculaService ms = new MatriculaService();
    TurmaService ts = new TurmaService();
    HistoricoService hs = new HistoricoService();

    public boolean adicionarMatricula(String cpfEstudante, Integer idTurma) {
        Estudante estudante = (Estudante) ps.findByCpf(cpfEstudante);
        Turma turma = (Turma) ts.findById(Turma.class, idTurma);

        if (turma == null) {
            return false;
        }

        if (estudante.getTurmas().contains(turma)) {
            return false;
        }

        Historico historicoInteresse1 = null, historicoInteresse2 = null;

        try {
            historicoInteresse1 = new Historico(turma.getIdioma(),
                    turma.getNivel(), estudante, Avaliacao.APROVADO,
                    EstadoEstudante.FINALIZADO);
            historicoInteresse2 = new Historico(turma.getIdioma(),
                    turma.getNivel() - 1, estudante, Avaliacao.APROVADO,
                    EstadoEstudante.FINALIZADO);
        } catch (Exception ex) {
            Logger.getLogger(MatriculaController.class.getName()).log(Level.WARNING,
                    ex.getCause().toString(), ex);
        }

        if (estudante.getHistorico().contains(historicoInteresse1)) {
            return false;
        }

        if (turma.getNivel() > 1) {
//            Historico ultimoHistorico = (Historico) hs.findHistorico(turma.getIdioma(), turma.getNivel() - 1, estudante);
            Historico ultimoHistoricoEstudante = null;

            for (Historico historicoEstudante : estudante.getHistorico()) {
                if (historicoEstudante.equals(historicoInteresse2)) {
                    ultimoHistoricoEstudante = historicoEstudante;
                }
            }

            if (ultimoHistoricoEstudante == null) {
                return false;
            }

            if (ultimoHistoricoEstudante.getNivel() + 1 != turma.getNivel()) {
                return false;
            }
        }

        if (estudante.getTurmas().contains(turma)) {
            return false;
        } else {
            HashSet<Turma> variasTurmas = estudante.getTurmas();
            variasTurmas.add(turma);
            estudante.setTurmas(variasTurmas);
            ps.saveOrUpdate(estudante);
        }

        return true;
    }

    public ArrayList<String[]> getEstudantesMatriculadosEmTurma(Integer id,
            String nomeIdioma, Integer nivel, String cpfProfessor) {

        List<Object[]> estudantesTurma = ts.findEstudantesJoinTurma(id);
        ArrayList<String[]> resultado = new ArrayList<String[]>();

        if (estudantesTurma.size() > 0) {
            for (Object[] elements : estudantesTurma) {
                Estudante e = (Estudante) elements[0];
                String[] aux = new String[]{
                    e.getNome(),
                    e.getCpf()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }

}
