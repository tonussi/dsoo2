package tests;

import br.ufsc.constant.*;
import br.ufsc.main.FakeData;
import java.util.*;
import br.ufsc.model.*;
import br.ufsc.service.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import org.junit.*;

public class HistoricoTest {

    PessoaService ps = new PessoaService();
    HorarioService hos = new HorarioService();
    TurmaService ts = new TurmaService();
    AnyService as = new AnyService();
    IdiomaService is = new IdiomaService();
    HistoricoService hs = new HistoricoService();
    Estudante estudante = new Estudante();
    Professor professor = new Professor();

    @Before
    public void setUp() {
        estudante.setNome("Lucas");
        estudante.setCpf("1024");
        estudante.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        estudante.setEndr("Rua João Jorge Mussi");
        estudante.setSenha("lucas");
        estudante.setRg("2024");
        estudante.setTelefone("154141");
        estudante.setAutoridade(Autoridade.ESTUDANTE);

        professor.setNome("João");
        professor.setCpf("423");
        professor.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        professor.setEndr("Rua João Jorge Mussi");
        professor.setSenha("lucas");
        professor.setRg("876");
        professor.setTelefone("41");
        professor.setAutoridade(Autoridade.PROFESSOR);

        ps.saveOrUpdate(estudante);
        ps.saveOrUpdate(professor);

        FakeData fd = new FakeData();
        fd.adicionarHorarios();
        fd.adicionarIdiomas();
    }

//    @After
//    public void tearDown() {
//        List<Pessoa> pessoas = as.findAll(Pessoa.class);
//        for (Pessoa pessoa : pessoas) {
//            as.delete(pessoa);
//        }
//
//        List<Turma> turmas = as.findAll(Turma.class);
//        for (Turma turma : turmas) {
//            as.delete(turma);
//        }
//
//        List<Historico> historico = as.findAll(Historico.class);
//        for (Historico h : historico) {
//            as.delete(h);
//        }
//
//        List<Idioma> idiomas = as.findAll(Idioma.class);
//        for (Idioma idioma : idiomas) {
//            as.delete(idioma);
//        }
//
//        List<Horario> horarios = as.findAll(Horario.class);
//        for (Horario horario : horarios) {
//            as.delete(horario);
//        }
//    }

    @Test
    public void testCursosFeitosOuAndamento() throws Exception {
        Professor professorAlvo = (Professor) ps.findByCpf("423");
        Estudante estudanteAlvo = (Estudante) ps.findByCpf("1024");

        Idioma ingles = is.findByNome("inglês");
        Idioma japones = is.findByNome("japonês");
        Idioma portugues = is.findByNome("português");

        HashSet<Idioma> idiomas = new HashSet<Idioma>();
        idiomas.add(ingles);
        idiomas.add(japones);
        idiomas.add(portugues);

        professorAlvo.setIdiomas(idiomas);

        HashSet<Horario> horarios1 = new HashSet<Horario>();
        horarios1.add(hos.findById(1, "07:30"));

        HashSet<Horario> horarios2 = new HashSet<Horario>();
        horarios2.add(hos.findById(2, "07:30"));

        HashSet<Horario> horarios3 = new HashSet<Horario>();
        horarios3.add(hos.findById(3, "07:30"));

        Turma turmaIngles = new Turma(horarios1, ingles, professorAlvo, 1);
        Turma turmaJapones = new Turma(horarios2, japones, professorAlvo, 1);
        Turma turmaPortugues = new Turma(horarios3, portugues, professorAlvo, 1);

        HashSet<Turma> turmas = new HashSet<Turma>();
        turmas.add(turmaIngles);
        ts.saveOrUpdate(turmaIngles);
        professorAlvo.setTurmas(turmas);
        ps.saveOrUpdate(professorAlvo);
        ps.saveOrUpdate(estudanteAlvo);

        Historico h1 = null, h2 = null, h3 = null;

        try {
            h1 = new Historico(turmaIngles.getIdioma(), turmaIngles.getNivel(),
                    estudanteAlvo, Avaliacao.REPROVADO, EstadoEstudante.FINALIZADO);
            h2 = new Historico(turmaJapones.getIdioma(), turmaJapones.getNivel(),
                    estudanteAlvo, Avaliacao.NAO_AVALIADO, EstadoEstudante.EM_ANDAMENTO);
            h3 = new Historico(turmaPortugues.getIdioma(), turmaPortugues.getNivel(),
                    estudanteAlvo, Avaliacao.NAO_AVALIADO, EstadoEstudante.EM_ANDAMENTO);
        } catch (Exception ex) {
            Logger.getLogger(HistoricoTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        HashSet<Historico> historico = new HashSet<Historico>();
        if (h1 != null) {
            historico.add(h1);
            hs.saveOrUpdate(h1);
        }
        if (h2 != null) {
            historico.add(h2);
            hs.saveOrUpdate(h2);
        }
        if (h3 != null) {
            historico.add(h3);
            hs.saveOrUpdate(h3);
        }

        if (historico.size() > 0) {
            estudanteAlvo.setHistorico(historico);
        }

        ps.saveOrUpdate(estudanteAlvo);

        List<Object[]> andamento = hs.findAllCursosEmAndamento(estudanteAlvo.getCpf());
        List<Object[]> finalizados = hs.findAllCursosFinalizados(estudanteAlvo.getCpf());

        Estudante testEstudante = (Estudante) ps.findByCpf("1024");
        assertEquals(3, testEstudante.getHistorico().size());
        assertEquals(2, andamento.size());
        assertEquals(1, finalizados.size());
    }

}
