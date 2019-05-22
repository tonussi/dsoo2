package br.ufsc.control;

import br.ufsc.constant.*;
import br.ufsc.service.*;
import br.ufsc.model.*;
import java.util.*;
import java.text.*;
import javax.swing.table.*;

public class ProfessorController {

    AnyService as;
    PessoaService ps;
    HistoricoService hs;
    IdiomaService is;
    SimpleDateFormat sdf;

    int COLUNA_APROVACAO = 2;
    int COLUNA_REPROVACAO = 3;

    public ProfessorController() {
        as = new AnyService();
        ps = new PessoaService();
        hs = new HistoricoService();
        is = new IdiomaService();
        sdf = new SimpleDateFormat("HH:mm");
    }

    public String getNome(String cpf) {
        return ps.findByCpf(cpf).getNome();
    }

    public ArrayList<String[]> getTurmasProfessorByCpf(String cpf) {
        Professor professor = (Professor) ps.findByCpf(cpf);
        HashSet<Turma> turmas = professor.getTurmas();
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        if (turmas.size() > 0) {
            for (Turma turma : turmas) {
                String auxhorarios = "";
                for (Horario h : turma.getHorarios()) {
                    auxhorarios += h.toString();
                }
                String[] aux = new String[]{
                    String.valueOf(turma.getId()),
                    auxhorarios,
                    turma.getIdioma().getNome(),
                    String.valueOf(turma.getNivel()),
                    turma.getProfessor().getNome()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }

    public ArrayList<String[]> getProfessores() {
        List<Professor> professores = ps.findAll(Professor.class);
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        if (professores.size() > 0) {
            for (Professor prof : professores) {
                String[] aux = new String[]{
                    prof.getCpf(),
                    prof.getNome()
                };
                resultado.add(aux);
            }
        }
        return resultado;
    }

    public ArrayList<String> getIdiomasProfessorByCpf(String cpf) {
        Professor professor = (Professor) ps.findByCpf(cpf);
        return professor.getNomesIdiomasSendoEnsinados();
    }

    public ArrayList<String[]> getProfessor(String cpf) {
        Professor professor = (Professor) ps.findByCpf(cpf);
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        if (professor != null) {
            String[] aux = new String[]{
                professor.getCpf(),
                professor.getNome()
            };
            resultado.add(aux);
        }
        return resultado;
    }

    public Historico addHistoricoEstudante(Turma turma, Estudante estudante, Avaliacao avaliacao, EstadoEstudante estadoEstudante) throws Exception {
        return new Historico(turma.getIdioma(), turma.getNivel(), estudante, avaliacao, estadoEstudante);
    }

    public boolean avaliar(Integer idTurma, String nomeIdioma, Integer nivel,
            String cpfProfessor, DefaultTableModel tabelaEstudanteSendoAvaliados) throws Exception {

        Turma velhaTurma = (Turma) as.findById(Turma.class, idTurma);

        for (int rindex = 0; rindex < tabelaEstudanteSendoAvaliados.getRowCount(); rindex++) {

            Boolean aprovacao = (Boolean) tabelaEstudanteSendoAvaliados.getValueAt(rindex, COLUNA_APROVACAO);
            Boolean reprovacao = (Boolean) tabelaEstudanteSendoAvaliados.getValueAt(rindex, COLUNA_REPROVACAO);

            if (aprovacao != null) {
                if (aprovacao) {
                    String cpf = (String) tabelaEstudanteSendoAvaliados.getValueAt(rindex, 1);
                    Estudante estudante = (Estudante) ps.findByCpf(cpf);

                    Historico novoHistorico = new Historico(velhaTurma.getIdioma(), velhaTurma.getNivel(), estudante, Avaliacao.APROVADO, EstadoEstudante.FINALIZADO);

                    HashSet<Turma> variasTurmas = estudante.getTurmas();
                    variasTurmas.remove(velhaTurma);
                    estudante.setTurmas(variasTurmas);
                    HashSet<Historico> historicos = estudante.getHistorico();
                    historicos.add(novoHistorico);
                    estudante.setHistorico(historicos);

                    hs.saveOrUpdate(novoHistorico);
                    ps.saveOrUpdate(estudante);
                }
            } else if (reprovacao != null && aprovacao == null) {
                if (reprovacao) {
                    String cpf = (String) tabelaEstudanteSendoAvaliados.getValueAt(rindex, 1);
                    Estudante estudante = (Estudante) ps.findByCpf(cpf);
                    Historico novoHistorico = new Historico(velhaTurma.getIdioma(), velhaTurma.getNivel(), estudante, Avaliacao.REPROVADO, EstadoEstudante.FINALIZADO);

                    HashSet<Turma> variasTurmas = estudante.getTurmas();
                    variasTurmas.remove(velhaTurma);
                    estudante.setTurmas(variasTurmas);
                    HashSet<Historico> historicos = estudante.getHistorico();
                    historicos.add(novoHistorico);
                    estudante.setHistorico(historicos);

                    hs.saveOrUpdate(novoHistorico);
                    ps.saveOrUpdate(estudante);
                }
            }
        }

        return true;
    }
}
