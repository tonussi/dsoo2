package tests;

import br.ufsc.constant.Autoridade;
import br.ufsc.main.FakeData;
import java.util.*;

import br.ufsc.model.*;
import br.ufsc.service.*;

import static org.junit.Assert.*;
import org.junit.*;

public class IdiomasProfessorTest {

    PessoaService ps = new PessoaService();
    HorarioService hos = new HorarioService();
    AnyService as = new AnyService();
    TurmaService ts = new TurmaService();
    IdiomaService is = new IdiomaService();
    Professor professor = new Professor();

    @Before
    public void setUp() {
        professor.setNome("Patrícia");
        professor.setCpf("0923");
        professor.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        professor.setEndr("Rua João Jorge Mussi");
        professor.setSenha("pat");
        professor.setRg("654");
        professor.setTelefone("41323");
        professor.setAutoridade(Autoridade.PROFESSOR);
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
    public void testAdicionarProfessorComSeusIdiomas() {
        Professor p = (Professor) ps.findByCpf("0923");

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

        ps.saveOrUpdate(p);

        Professor testProfessor = (Professor) ps.findByCpf("0923");
        assertEquals(3, testProfessor.getIdiomas().size());
    }
}
