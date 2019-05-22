package br.ufsc.control;

import br.ufsc.model.*;
import br.ufsc.service.*;
import java.util.*;
import javax.swing.JTable;
import util.Util;

public class AdministradorController {

    PessoaService ps;
    AnyService as;
    TurmaService ts;
    IdiomaService is;
    HorarioService horus;

    public AdministradorController() {
        this.ps = new PessoaService();
        this.as = new AnyService();
        this.ts = new TurmaService();
        this.is = new IdiomaService();
        this.horus = new HorarioService();
    }

    public String getNome(String cpf) {
        return ps.findByCpf(cpf).getNome();
    }

    public boolean adicionarNovaTurma(String cpfProfessor, String nomeIdioma,
            JTable tabelaHorarios, Integer nivel) throws Exception {

        if (cpfProfessor == null || nomeIdioma == null || tabelaHorarios == null
                || nivel == null) {
            return false;
        }

        Professor professor = (Professor) ps.findByCpf(cpfProfessor);

        if (professor == null) {
            return false;
        }

        Idioma idioma = is.findByNome(nomeIdioma);

        HashSet<Horario> horarios = new HashSet<Horario>();

        for (Integer rindex : tabelaHorarios.getSelectedRows()) {
            if (rindex > -1) {
                tabelaHorarios.setRowSelectionInterval(rindex, rindex);
                Integer dia = Integer.valueOf(tabelaHorarios.getValueAt(rindex, 0).toString());
                String hora = tabelaHorarios.getValueAt(rindex, 1).toString();
                Horario horario = horus.findById(dia, Util.parseHorario(hora));
                horarios.add(horario);
            }
        }

        if (idioma == null) {
            return false;
        }

        Turma turma = new Turma(horarios, idioma, professor, nivel);

        professor.setTurmas(turma);

        ts.saveOrUpdate(turma);
        ps.saveOrUpdate(professor);
        return true;
    }
}
