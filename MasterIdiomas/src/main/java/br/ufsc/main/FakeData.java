package br.ufsc.main;

import br.ufsc.constant.*;
import br.ufsc.model.*;
import br.ufsc.service.*;
import java.util.*;
import util.Util;

public class FakeData {

    PessoaService ps = new PessoaService();
    TurmaService ts = new TurmaService();
    AnyService as = new AnyService();
    IdiomaService is = new IdiomaService();
    HistoricoService hs = new HistoricoService();
    HorarioService hos = new HorarioService();

    public void adicionarTurmas() throws Exception {
        Professor professor1 = (Professor) ps.findByCpf("093");
        Professor professor2 = (Professor) ps.findByCpf("111");

        Estudante estudante1 = (Estudante) ps.findByCpf("012");
        Estudante estudante2 = (Estudante) ps.findByCpf("653");

        Idioma idioma1 = is.findByNome("Inglês");
        Idioma idioma2 = is.findByNome("Francês");
        Idioma idioma3 = is.findByNome("Hindi");
        Idioma idioma4 = is.findByNome("Bengali");

        HashSet<Idioma> idiomas1 = new HashSet<Idioma>();
        idiomas1.add(idioma1);
        idiomas1.add(idioma2);
        professor1.setIdiomas(idiomas1);

        HashSet<Idioma> idiomas2 = new HashSet<Idioma>();
        idiomas2.add(idioma3);
        idiomas2.add(idioma4);
        professor2.setIdiomas(idiomas2);

        HashSet<Horario> horarios1 = new HashSet<Horario>();
        Horario hor1 = hos.findById(1, "07:30");
        Horario hor2 = hos.findById(1, "13:30");
        horarios1.add(hor1);
        horarios1.add(hor2);

        HashSet<Horario> horarios2 = new HashSet<Horario>();
        Horario hor3 = hos.findById(4, "07:30");
        Horario hor4 = hos.findById(4, "13:30");
        horarios2.add(hor3);
        horarios2.add(hor4);

        HashSet<Horario> horarios3 = new HashSet<Horario>();
        Horario hor5 = hos.findById(3, "07:30");
        horarios3.add(hor5);

        HashSet<Horario> horarios4 = new HashSet<Horario>();
        Horario hor6 = hos.findById(5, "07:30");
        Horario hor7 = hos.findById(5, "13:30");
        horarios4.add(hor6);
        horarios4.add(hor7);

        HashSet<Horario> horarios5 = new HashSet<Horario>();
        Horario hor8 = hos.findById(6, "09:10");
        horarios5.add(hor8);

        HashSet<Turma> turmas1 = new HashSet<Turma>();
        Turma turma1 = new Turma(horarios1, idioma1, professor1, 4);
        turmas1.add(turma1);
        Turma turma2 = new Turma(horarios2, idioma2, professor1, 1);
        turmas1.add(turma2);
        Turma turma3 = new Turma(horarios3, idioma1, professor1, 3);
        turmas1.add(turma3);

        professor1.setTurmas(turmas1);

        HashSet<Turma> turmas2 = new HashSet<Turma>();
        Turma turma4 = new Turma(horarios4, idioma3, professor2, 1);
        turmas2.add(turma4);
        Turma turma5 = new Turma(horarios5, idioma4, professor2, 1);
        turmas2.add(turma5);

        professor2.setTurmas(turmas2);

        HashSet<Historico> historicos = new HashSet<Historico>();
        Historico historico1 = new Historico(idioma1, 1, estudante1, Avaliacao.APROVADO, EstadoEstudante.FINALIZADO);
        historicos.add(historico1);
        Historico historico2 = new Historico(idioma1, 2, estudante1, Avaliacao.APROVADO, EstadoEstudante.FINALIZADO);
        historicos.add(historico2);
        Historico historico3 = new Historico(idioma1, 3, estudante1, Avaliacao.REPROVADO, EstadoEstudante.FINALIZADO);
        historicos.add(historico3);

        estudante1.setHistorico(historicos);

        HashSet<Turma> variasTurmas = new HashSet<Turma>();
        variasTurmas.add(turma2);
        estudante2.setTurmas(variasTurmas);

        ts.saveOrUpdate(turma1);
        ts.saveOrUpdate(turma2);
        ts.saveOrUpdate(turma3);

        hs.saveOrUpdate(historico1);
        hs.saveOrUpdate(historico2);
        hs.saveOrUpdate(historico3);

        ps.saveOrUpdate(professor1);
        ps.saveOrUpdate(professor2);

        ps.saveOrUpdate(estudante1);
        ps.saveOrUpdate(estudante2);
    }

    public void adicionarIdiomas() {
        ArrayList<Idioma> linguasFamosas = new ArrayList<Idioma>();
        linguasFamosas.add(new Idioma("Mandarim"));
        linguasFamosas.add(new Idioma("Castelhano"));
        linguasFamosas.add(new Idioma("Espanhol"));
        linguasFamosas.add(new Idioma("Inglês"));
        linguasFamosas.add(new Idioma("Bengali"));
        linguasFamosas.add(new Idioma("Hindi"));
        linguasFamosas.add(new Idioma("Português"));
        linguasFamosas.add(new Idioma("Russo"));
        linguasFamosas.add(new Idioma("Japonês"));
        linguasFamosas.add(new Idioma("Alemão "));
        linguasFamosas.add(new Idioma("Chinês"));
        linguasFamosas.add(new Idioma("Coreano"));
        linguasFamosas.add(new Idioma("Francês"));
        linguasFamosas.add(new Idioma("Vietnamita"));
        linguasFamosas.add(new Idioma("Telugo"));
        linguasFamosas.add(new Idioma("Cantonês"));
        linguasFamosas.add(new Idioma("Turco"));
        linguasFamosas.add(new Idioma("Urdu"));
        linguasFamosas.add(new Idioma("Polaco"));
        linguasFamosas.add(new Idioma("Egípcio"));
        linguasFamosas.add(new Idioma("Ucraniano"));
        linguasFamosas.add(new Idioma("Italiano"));
        linguasFamosas.add(new Idioma("Xiang"));
        linguasFamosas.add(new Idioma("Malaio"));
        linguasFamosas.add(new Idioma("Romeno"));
        linguasFamosas.add(new Idioma("Azerbaijão"));
        linguasFamosas.add(new Idioma("Farsi"));
        linguasFamosas.add(new Idioma("Tailandês"));
        linguasFamosas.add(new Idioma("Holandês"));

        for (Idioma next : linguasFamosas) {
            if (is.findByNome(next.getNome()) == null) {
                is.saveOrUpdate(next);
            }
        }
    }

    public void adicionarHorarios() {
        HashSet<Horario> horarios = new HashSet<Horario>();

        horarios.add(new Horario(1, Util.parseHorario("07:30")));
        horarios.add(new Horario(1, Util.parseHorario("09:10")));
        horarios.add(new Horario(1, Util.parseHorario("13:30")));
        horarios.add(new Horario(1, Util.parseHorario("17:10")));
        horarios.add(new Horario(2, Util.parseHorario("07:30")));
        horarios.add(new Horario(2, Util.parseHorario("09:10")));
        horarios.add(new Horario(2, Util.parseHorario("13:30")));
        horarios.add(new Horario(2, Util.parseHorario("17:10")));
        horarios.add(new Horario(3, Util.parseHorario("07:30")));
        horarios.add(new Horario(3, Util.parseHorario("09:10")));
        horarios.add(new Horario(3, Util.parseHorario("13:30")));
        horarios.add(new Horario(3, Util.parseHorario("17:10")));
        horarios.add(new Horario(4, Util.parseHorario("07:30")));
        horarios.add(new Horario(4, Util.parseHorario("09:10")));
        horarios.add(new Horario(4, Util.parseHorario("13:30")));
        horarios.add(new Horario(4, Util.parseHorario("17:10")));
        horarios.add(new Horario(5, Util.parseHorario("07:30")));
        horarios.add(new Horario(5, Util.parseHorario("09:10")));
        horarios.add(new Horario(5, Util.parseHorario("13:30")));
        horarios.add(new Horario(5, Util.parseHorario("17:10")));
        horarios.add(new Horario(6, Util.parseHorario("07:30")));
        horarios.add(new Horario(6, Util.parseHorario("09:10")));
        horarios.add(new Horario(6, Util.parseHorario("13:30")));
        horarios.add(new Horario(6, Util.parseHorario("17:10")));

        for (Horario horario : horarios) {
            if (hos.findById(horario.getDia(), horario.getHorario()) == null) {
                hos.saveOrUpdate(horario);
            }
        }
    }

    public void adicionarPessoas() {
        Estudante estudante = new Estudante();
        Estudante estudante1 = new Estudante();
        Professor professor = new Professor();
        Professor professor1 = new Professor();
        Administrador admin = new Administrador();

        estudante.setNome("Débora");
        estudante.setCpf("012");
        estudante.setDataNascimento(new GregorianCalendar(4, 5, 1718));
        estudante.setEndr("Trindade");
        estudante.setSenha("debora");
        estudante.setRg("665");
        estudante.setTelefone("888321");
        estudante.setAutoridade(Autoridade.ESTUDANTE);

        estudante1.setNome("Nathália");
        estudante1.setCpf("653");
        estudante1.setDataNascimento(new GregorianCalendar(4, 5, 1428));
        estudante1.setEndr("Continente");
        estudante1.setSenha("nat");
        estudante1.setRg("43122");
        estudante1.setTelefone("09213");
        estudante1.setAutoridade(Autoridade.ESTUDANTE);

        professor.setNome("Rute");
        professor.setCpf("093");
        professor.setDataNascimento(new GregorianCalendar(4, 5, 1748));
        professor.setEndr("Kobrasol");
        professor.setSenha("rute");
        professor.setRg("933");
        professor.setTelefone("998192");
        professor.setAutoridade(Autoridade.PROFESSOR);

        professor1.setNome("Amanda");
        professor1.setCpf("111");
        professor1.setDataNascimento(new GregorianCalendar(4, 5, 1668));
        professor1.setEndr("Lagoa");
        professor1.setSenha("amanda");
        professor1.setRg("0230");
        professor1.setTelefone("0800233");
        professor1.setAutoridade(Autoridade.PROFESSOR);

        admin.setNome("Laura");
        admin.setCpf("085");
        admin.setDataNascimento(new GregorianCalendar(4, 5, 1598));
        admin.setEndr("Via Láctea");
        admin.setSenha("admin");
        admin.setRg("600");
        admin.setTelefone("99887756");
        admin.setAutoridade(Autoridade.ADMIN);

        ps.saveOrUpdate(estudante);
        ps.saveOrUpdate(estudante1);
        ps.saveOrUpdate(professor);
        ps.saveOrUpdate(professor1);
        ps.saveOrUpdate(admin);
    }
}
