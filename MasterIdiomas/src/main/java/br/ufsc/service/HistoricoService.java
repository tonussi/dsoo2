package br.ufsc.service;

import br.ufsc.constant.*;
import br.ufsc.model.*;
import java.util.*;
import org.hibernate.*;

public class HistoricoService extends GenericService {

    @Override
    public Object findById(Class clazz, Integer id) {
        return super.findById(clazz, id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAll(Class clazz) {
        return super.findAll(clazz); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object obj) {
        super.delete(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOrUpdate(Object obj) {
        super.saveOrUpdate(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Object[]> findAllCursosFinalizados(String cpf) {
        startOperation();
        Query query = session.createQuery("from Historico h join h.estudante e where h.estadoEstudante = :es and e.cpf = :cpf");
        query.setParameter("es", EstadoEstudante.FINALIZADO);
        query.setParameter("cpf", cpf);
        List objects = query.list();
        endOperation();
        return objects;
    }

    public List<Object[]> findAllCursosEmAndamento(String cpf) {
        startOperation();
        Query query = session.createQuery("from Estudante e join e.turmas t where e.cpf = :cpf");
        query.setParameter("cpf", cpf);
        List objects = query.list();
        endOperation();
        return objects;
    }

    public List findTurmaByIdAndCpfEstudante(Turma turma, Estudante estudante) {
        startOperation();
        Query query = session.createQuery("from Estudante e inner join e.turmas t where e.cpf = :cpf and t.id = :idTurma");
        query.setParameter("idTurma", turma.getId());
        query.setParameter("cpf", estudante.getCpf());
        List objects = query.list();
        endOperation();
        return objects;
    }

    public Historico findHistorico(Idioma idioma, Integer nivel, Estudante estudante) {
        startOperation();
        Query query = session.createQuery("from Estudante e join e.historico h where h.idioma = :idioma "
                + "and h.estudante = :estudante and h.nivel = :nivel "
                + "and h.estadoEstudante = :es "
                + "and h.avaliacao = :ava");
        query.setParameter("idioma", idioma);
        query.setParameter("estudante", estudante);
        query.setParameter("nivel", nivel);
        query.setParameter("es", EstadoEstudante.FINALIZADO);
        query.setParameter("ava", Avaliacao.APROVADO);
        List<Object[]> objects = query.list();
        endOperation();
        if (objects.size() > 0) {
            return (Historico) objects.get(0)[1];
        }
        return null;
    }

}
