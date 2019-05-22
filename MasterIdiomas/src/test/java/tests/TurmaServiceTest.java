package tests;

import br.ufsc.constant.*;
import br.ufsc.main.FakeData;
import java.util.*;
import br.ufsc.model.*;
import br.ufsc.service.*;

import static org.junit.Assert.*;
import org.junit.*;

public class TurmaServiceTest {

    PessoaService ps = new PessoaService();
    HorarioService horos = new HorarioService();
    AnyService as = new AnyService();
    TurmaService ts = new TurmaService();
    IdiomaService is = new IdiomaService();

    @Before
    public void setUp() {
        Professor professor1 = new Professor();
        professor1.setNome("João");
        professor1.setCpf("11415");
        professor1.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        professor1.setEndr("Rua João Jorge Mussi");
        professor1.setSenha("lucas");
        professor1.setRg("11151");
        professor1.setTelefone("20611");
        professor1.setAutoridade(Autoridade.PROFESSOR);
        ps.saveOrUpdate(professor1);

        FakeData fd = new FakeData();
        fd.adicionarHorarios();
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
    public void testProfessorComDuasTurmasDistintas() throws Exception {
        Professor p = (Professor) ps.findByCpf("11415");

        Idioma idioma1 = new Idioma("mandarim");
        Idioma idioma2 = new Idioma("alemão");

        HashSet<Idioma> idiomas = new HashSet<Idioma>();
        idiomas.add(idioma1);
        idiomas.add(idioma2);
        p.setIdiomas(idiomas);

        is.saveOrUpdate(idioma1);
        is.saveOrUpdate(idioma2);

        ps.saveOrUpdate(p);

        HashSet<Horario> horarios1 = new HashSet<Horario>();
        horarios1.add(horos.findById(1, "07:30"));
        HashSet<Horario> horarios2 = new HashSet<Horario>();
        horarios1.add(horos.findById(2, "07:30"));

        Turma turma1 = new Turma(horarios1, idioma1, p, 1);
        Turma turma2 = new Turma(horarios2, idioma2, p, 1);

        HashSet<Turma> turmas = new HashSet<Turma>();
        turmas.add(turma1);
        turmas.add(turma2);

        p.setTurmas(turmas);
        turma1.setProfessor(p);
        turma2.setProfessor(p);

        ts.saveOrUpdate(turma1);
        ts.saveOrUpdate(turma2);
        ps.saveOrUpdate(p);

        Professor testProfessor = (Professor) ps.findByCpf("11415");

        assertEquals(2, testProfessor.getTurmas().size());
        assertEquals(2, testProfessor.getIdiomas().size());
    }

    @Test
    public void testProfessorComDuasTurmasMesmoIdiomaHorariosDiferentes() throws Exception {
        Professor p = (Professor) ps.findByCpf("11415");

        Idioma idiomaMandarim = new Idioma("mandarim");
        Idioma idiomaAlemao = new Idioma("alemão");

        HashSet<Idioma> idiomas = new HashSet<Idioma>();
        idiomas.add(idiomaMandarim);
        idiomas.add(idiomaAlemao);
        p.setIdiomas(idiomas);

        is.saveOrUpdate(idiomaMandarim);
        is.saveOrUpdate(idiomaAlemao);

        ps.saveOrUpdate(p);

        HashSet<Horario> horarios1 = new HashSet<Horario>();
        horarios1.add(horos.findById(1, "07:30"));
        HashSet<Horario> horarios2 = new HashSet<Horario>();
        horarios1.add(horos.findById(2, "07:30"));
        HashSet<Horario> horarios3 = new HashSet<Horario>();
        horarios1.add(horos.findById(3, "07:30"));

        Turma turmaMandarim1 = new Turma(horarios1, idiomaMandarim, p, 1);
        Turma turmaMandarim2 = new Turma(horarios2, idiomaMandarim, p, 1);
        Turma turmaAlemao1 = new Turma(horarios3, idiomaAlemao, p, 1);

        HashSet<Turma> turmas = new HashSet<Turma>();
        turmas.add(turmaMandarim1);
        turmas.add(turmaMandarim2);
        turmas.add(turmaAlemao1);

        p.setTurmas(turmas);

        ts.saveOrUpdate(turmaMandarim1);
        ts.saveOrUpdate(turmaMandarim2);
        ts.saveOrUpdate(turmaAlemao1);

        ps.saveOrUpdate(p);

        Professor testProfessor = (Professor) ps.findByCpf("11415");
        List<Turma> turmasMandarim = ts.findByIdiomaProfessorTurma(idiomaMandarim, p, turmaMandarim1.getNivel());

        assertEquals(2, turmasMandarim.size());
        assertEquals("MANDARIM", turmasMandarim.get(0).getIdioma().getNome());
        assertEquals("MANDARIM", turmasMandarim.get(0).getIdioma().getNome());
        assertEquals(3, testProfessor.getTurmas().size());
        assertEquals(2, testProfessor.getIdiomas().size());
    }
}
