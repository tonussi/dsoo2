package tests;

import br.ufsc.constant.Autoridade;
import br.ufsc.model.*;
import br.ufsc.service.*;
import java.util.*;
import org.junit.After;

import static org.junit.Assert.*;
import org.junit.*;

public class PessoaServiceTest {

    PessoaService ps = new PessoaService();
    Estudante estudante = new Estudante();
    Professor professor = new Professor();
    Administrador admin = new Administrador();

    @Before
    public void setUp() {
        estudante.setNome("Lucas");
        estudante.setCpf("0929");
        estudante.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        estudante.setEndr("Rua João Jorge Mussi");
        estudante.setSenha("lucas");
        estudante.setRg("092909");
        estudante.setTelefone("0192909");
        estudante.setAutoridade(Autoridade.ESTUDANTE);

        professor.setNome("João");
        professor.setCpf("6732425");
        professor.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        professor.setEndr("Rua João Jorge Mussi");
        professor.setSenha("lucas");
        professor.setRg("611");
        professor.setTelefone("32331");
        professor.setAutoridade(Autoridade.PROFESSOR);

        admin.setNome("Marcos");
        admin.setCpf("528973");
        admin.setDataNascimento(new GregorianCalendar(4, 5, 1998));
        admin.setEndr("Rua João Jorge Mussi");
        admin.setSenha("lucas");
        admin.setRg("56183");
        admin.setTelefone("6531");
        admin.setAutoridade(Autoridade.ADMIN);

        ps.saveOrUpdate(estudante);
        ps.saveOrUpdate(professor);
        ps.saveOrUpdate(admin);
    }

//    @After
//    public void tearDown() {
//        List<Pessoa> pessoas = ps.findAll(Pessoa.class);
//        for (Pessoa p : pessoas) {
//            ps.delete(p);
//        }
//    }

    @Test
    public void testF() {
        ps.delete(admin);
        List<Pessoa> emptyList = ps.findAll(Pessoa.class);
        assertEquals(2, emptyList.size());
    }

    @Test
    public void testE() {
        ps.delete(professor);
        List<Pessoa> pessoas = ps.findAll(Pessoa.class);
        assertEquals(2, pessoas.size());
    }

    @Test
    public void testD() {
        estudante.setCpf("123812738912");
        estudante.setNome("Amanda");
        ps.saveOrUpdate(estudante);
        Pessoa p = ps.findByCpf("123812738912");
        assertEquals("123812738912", p.getCpf());
    }

    @Test
    public void testC() {
        Pessoa p = ps.findByCpf("6732425");
        assertEquals("6732425", p.getCpf());
    }

    @Test
    public void testB() {
        Pessoa p = ps.findByCpf("43312312");
        assertEquals(null, p);
    }

    @Test
    public void testA() {
        List<Pessoa> pessoas = ps.findAll(Pessoa.class);
        assertEquals(3, pessoas.size());
        assertEquals(Autoridade.ESTUDANTE, ((Estudante) pessoas.get(0)).getAutoridade());
        assertEquals(Autoridade.PROFESSOR, ((Professor) pessoas.get(1)).getAutoridade());
        assertEquals(Autoridade.ADMIN, ((Administrador) pessoas.get(2)).getAutoridade());
    }
}
