package tests;

import java.util.*;
import br.ufsc.model.*;
import br.ufsc.constant.*;
import br.ufsc.main.FakeData;
import br.ufsc.service.*;

import org.junit.*;
import static org.junit.Assert.*;

public class MatriculasEstudanteTest {

    PessoaService ps = new PessoaService();
    HorarioService hos = new HorarioService();
    AnyService as = new AnyService();
    IdiomaService is = new IdiomaService();
    TurmaService ts = new TurmaService();
    Estudante estudante = new Estudante();
    Professor professor = new Professor();

    @Before
    public void setUp() {
        estudante.setNome("Lucas");
        estudante.setCpf("766");
        estudante.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        estudante.setEndr("Rua João Jorge Mussi");
        estudante.setSenha("lucas");
        estudante.setRg("1455");
        estudante.setTelefone("65");
        estudante.setAutoridade(Autoridade.ESTUDANTE);

        professor.setNome("João");
        professor.setCpf("81324");
        professor.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        professor.setEndr("Rua João Jorge Mussi");
        professor.setSenha("lucas");
        professor.setRg("8122324");
        professor.setTelefone("81");
        professor.setAutoridade(Autoridade.PROFESSOR);

        ps.saveOrUpdate(estudante);
        ps.saveOrUpdate(professor);

        FakeData fd = new FakeData();
        fd.adicionarHorarios();
        fd.adicionarIdiomas();
    }

//    @After
//    public void tearDown() {
//
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
//        List<Pessoa> pessoas = as.findAll(Pessoa.class);
//        for (Pessoa pessoa : pessoas) {
//            as.delete(pessoa);
//        }
//    }

    @Test
    public void testEstudanteMatriculasECursosFeitos() throws Exception {
        Professor p = (Professor) ps.findByCpf("81324");
        Estudante e = (Estudante) ps.findByCpf("766");

        Idioma ingles = is.findByNome("inglês");
        Idioma japones = is.findByNome("japonês");
        Idioma portugues = is.findByNome("português");

        is.saveOrUpdate(ingles);
        is.saveOrUpdate(japones);
        is.saveOrUpdate(portugues);

        HashSet<Idioma> idiomas = new HashSet<Idioma>();
        idiomas.add(ingles);
        idiomas.add(japones);
        idiomas.add(portugues);
        p.setIdiomas(idiomas);

        HashSet<Horario> horarios1 = new HashSet<Horario>();
        horarios1.add(hos.findById(1, "07:30"));

        HashSet<Horario> horarios2 = new HashSet<Horario>();
        horarios2.add(hos.findById(2, "07:30"));

        HashSet<Horario> horarios3 = new HashSet<Horario>();
        horarios3.add(hos.findById(3, "07:30"));

        Turma turmaIngles = new Turma(horarios1, ingles, p, 1);
        Turma turmaJapones = new Turma(horarios2, japones, p, 1);
        Turma turmaPortugues = new Turma(horarios3, portugues, p, 1);

        HashSet<Turma> turmas = new HashSet<Turma>();
        turmas.add(turmaIngles);
        turmas.add(turmaJapones);
        turmas.add(turmaPortugues);

        as.saveOrUpdate(turmaIngles);
        as.saveOrUpdate(turmaJapones);
        as.saveOrUpdate(turmaPortugues);

        ps.saveOrUpdate(p);

        Historico h1 = new Historico(turmaIngles.getIdioma(), turmaIngles.getNivel(), e, Avaliacao.NAO_AVALIADO, EstadoEstudante.EM_ANDAMENTO);
        Historico h2 = new Historico(turmaJapones.getIdioma(), turmaJapones.getNivel(), e, Avaliacao.NAO_AVALIADO, EstadoEstudante.EM_ANDAMENTO);
        Historico h3 = new Historico(turmaPortugues.getIdioma(), turmaPortugues.getNivel(), e, Avaliacao.NAO_AVALIADO, EstadoEstudante.EM_ANDAMENTO);

        HashSet<Historico> historico = new HashSet<Historico>();
        historico.add(h1);
        historico.add(h2);
        historico.add(h3);

        as.saveOrUpdate(h1);
        as.saveOrUpdate(h2);
        as.saveOrUpdate(h3);

        p.setTurmas(turmas);
        e.setHistorico(historico);

        HashSet<Turma> turmasAbertas = new HashSet<Turma>();
        turmasAbertas.add(turmaIngles);
        turmasAbertas.add(turmaJapones);
        turmasAbertas.add(turmaPortugues);
        e.setTurmas(turmas);

        ps.saveOrUpdate(e);
        ps.saveOrUpdate(p);

        List<Turma> turmasBanco = ts.findAll(Turma.class);

        Estudante testEstudante = (Estudante) ps.findByCpf("766");

        assertEquals(3, testEstudante.getHistorico().size());
        assertEquals(3, testEstudante.getTurmas().size());
        assertEquals(3, turmasBanco.size());
    }
}
