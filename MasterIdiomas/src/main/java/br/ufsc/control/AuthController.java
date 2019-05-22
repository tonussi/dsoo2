package br.ufsc.control;

import br.ufsc.constant.*;
import br.ufsc.model.*;
import br.ufsc.service.*;
import java.text.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

public class AuthController {

    PessoaService ps = new PessoaService();

    public Autoridade check(String cpf, char[] senha) {
        Autoridade a = Autoridade.NENHUMA;

        Pessoa p = ps.findByCpf(cpf);

        if (p != null) {
            try {
                for (int i = 0; i < senha.length; i++) {
                    if (p.getSenha().charAt(i) != senha[i]) {
                        return a;
                    }
                }
            } catch (StringIndexOutOfBoundsException e) {
                return a;
            }
        } else {
            return a;
        }

        if (p instanceof Estudante) {
            a = ((Estudante) p).getAutoridade();
        } else if (p instanceof Professor) {
            a = ((Professor) p).getAutoridade();
        } else if (p instanceof Administrador) {
            a = ((Administrador) p).getAutoridade();
        }

        return a;
    }

    public boolean solveNewEstudante(String nome, String cpf,
            String rg, String senha, String dataNascimento, String tel,
            String endr) {

        if (nome.equals("") || cpf.equals("") || rg.equals("")
                || senha.equals("") || dataNascimento.equals("")
                || tel.equals("") || endr.equals("")) {
            return false;
        } else {
            Estudante estudante = new Estudante();
            estudante.setNome(nome);
            estudante.setCpf(cpf);
            estudante.setRg(rg);

            DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            Calendar cal = new GregorianCalendar();

            try {
                Date date = df.parse(dataNascimento);
                cal.setTime(date);
            } catch (ParseException ex) {
                Logger.getLogger(AuthController.class.getName()).log(Level.INFO, null, ex);
                return false;
            }

            estudante.setDataNascimento(cal);
            estudante.setSenha(senha);
            estudante.setEndr(endr);
            estudante.setTelefone(tel);

            estudante.setAutoridade(Autoridade.ESTUDANTE);

            ps.saveOrUpdate(estudante);
            return true;
        }
    }

    public boolean solveNewProfessor(String nome, String cpf,
            String rg, String senha, String dataNascimento, String tel,
            String endr, DefaultListModel idiomasEnsinados) {

        if (nome.equals("") || cpf.equals("") || rg.equals("")
                || senha.equals("") || dataNascimento.equals("")
                || tel.equals("") || endr.equals("")) {
            return false;
        } else {
            Professor professor = new Professor();
            professor.setNome(nome);
            professor.setCpf(cpf);
            professor.setRg(rg);

            DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            Calendar cal = new GregorianCalendar();

            try {
                Date date = df.parse(dataNascimento);
                cal.setTime(date);
            } catch (ParseException ex) {
                Logger.getLogger(AuthController.class.getName()).log(Level.INFO, null, ex);
                return false;
            }

            professor.setDataNascimento(cal);
            professor.setSenha(senha);
            professor.setEndr(endr);
            professor.setTelefone(tel);

            professor.setAutoridade(Autoridade.PROFESSOR);

            IdiomaService is = new IdiomaService();
            HashSet<Idioma> idiomas = new HashSet<Idioma>();

            for (int i = 0; i < idiomasEnsinados.getSize(); i++) {
                String idiomaEnsinado = (String) idiomasEnsinados.get(i);
                Idioma idioma = is.findByNome(idiomaEnsinado);
                if (idioma == null) {
                    idioma = new Idioma(idiomaEnsinado);
                }
                idiomas.add(idioma);
                is.saveOrUpdate(idioma);
            }

            professor.setIdiomas(idiomas);
            ps.saveOrUpdate(professor);
            return true;
        }
    }

    public boolean solveNewAdministrador(String nome, String cpf,
            String rg, String senha, String dataNascimento, String tel,
            String endr) {

        if (nome.equals("") || cpf.equals("") || rg.equals("")
                || senha.equals("") || dataNascimento.equals("")
                || tel.equals("") || endr.equals("")) {
            return false;
        } else {
            Administrador admin = new Administrador();
            admin.setNome(nome);
            admin.setCpf(cpf);
            admin.setRg(rg);

            DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            Calendar cal = new GregorianCalendar();

            try {
                Date date = df.parse(dataNascimento);
                cal.setTime(date);
            } catch (ParseException ex) {
                Logger.getLogger(AuthController.class.getName()).log(Level.INFO, null, ex);
                return false;
            }

            admin.setDataNascimento(cal);
            admin.setSenha(senha);
            admin.setEndr(endr);
            admin.setTelefone(tel);

            admin.setAutoridade(Autoridade.ADMIN);
            ps.saveOrUpdate(admin);
            return true;
        }
    }

}
