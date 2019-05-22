package br.ufsc.service;

import br.ufsc.constant.Avaliacao;
import br.ufsc.constant.EstadoEstudante;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;

public class MatriculaService extends GenericService {

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

    public List<Object[]> findMatriculasByCompositeId(Integer diaSemana, Date horarioInicio, Date horarioFim) {
        startOperation();
        Query query = session.createQuery("from Matricula as m join m.turma as t "
                + "join m.estudante as e join m.estudante.historico as h "
                + "where t.turmaIdentity.diaSemana = :diaSemana and "
                + "t.turmaIdentity.horarioInicio = :horarioInicio and "
                + "t.turmaIdentity.horarioFim = :horarioFim and h.turma = t and "
                + "h.avaliacao = :avaliacao and h.estadoEstudante = :estado");
        query.setParameter("diaSemana", diaSemana);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        query.setParameter("avaliacao", Avaliacao.NAO_AVALIADO);
        query.setParameter("estado", EstadoEstudante.EM_ANDAMENTO);
        List objects = query.list();
        endOperation();
        return objects;
    }

    public List<Object[]> findFinalistasTurma(Integer diaSemana, Date horarioInicio, Date horarioFim) {
        startOperation();
        // SELECT * FROM Matricula m join Turma t
        // on t.diaSemana = m.turma_diaSemana and m.turma_diaSemana = 2
        // and t.horarioFim = m.turma_horarioFim and m.turma_horarioFim = '17:20'
        // and t.horarioInicio = m.turma_horarioInicio and m.turma_horarioInicio = '14:20'
        // join Pessoa as p on p.id = m.estudante_id join Estudante e on e.id = m.estudante.id;
        Query query = session.createQuery("from Matricula as m join m.turma as t "
                + "join m.estudante as e inner join m.estudante.historico as h "
                + "where t.turmaIdentity.diaSemana = :diaSemana and "
                + "t.turmaIdentity.horarioInicio = :horarioInicio and "
                + "t.turmaIdentity.horarioFim = :horarioFim and "
                + "h.turma = t and h.avaliacao = :avaliacao and "
                + "h.estadoEstudante = :estado");
        query.setParameter("diaSemana", diaSemana);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        query.setParameter("avaliacao", Avaliacao.NAO_AVALIADO);
        query.setParameter("estado", EstadoEstudante.EM_ANDAMENTO);
        List objects = query.list();
        endOperation();
        return objects;
    }    
}
