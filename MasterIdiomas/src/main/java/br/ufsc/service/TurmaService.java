package br.ufsc.service;

import br.ufsc.model.Idioma;
import br.ufsc.model.Professor;
import br.ufsc.model.Turma;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;

public class TurmaService extends GenericService {

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

    public List<Turma> findAll(Integer diaSemana, Date horarioInicio, Date horarioFim) {
        startOperation();
        Query query = session.createQuery("from Turma where diaSemana = "
                + ":diaSemana and horarioInicio = :horarioInicio and "
                + "horarioFim = :horarioFim");
        query.setParameter("diaSemana", diaSemana);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        List<Turma> objects = query.list();
        endOperation();
        return objects;
    }

    public Turma findByCompositeId(Integer diaSemana, Date horarioInicio, Date horarioFim) {
        startOperation();
        Query query = session.createQuery("from Turma t where "
                + "t.turmaIdentity.diaSemana = :diaSemana and "
                + "t.turmaIdentity.horarioInicio = :horarioInicio "
                + "and t.turmaIdentity.horarioFim = :horarioFim");
        query.setParameter("diaSemana", diaSemana);
        query.setParameter("horarioInicio", horarioInicio);
        query.setParameter("horarioFim", horarioFim);
        List objects = query.list();
        Object obj = null;
        if (objects.size() > 0) {
            obj = query.list().get(0);
        }
        endOperation();
        return (Turma) obj;
    }

    public List<Turma> findByIdiomaProfessorTurma(Idioma idioma, Professor professor, Integer nivel) {
        startOperation();
        Query query = session.createQuery("from Turma t where "
                + "t.idioma = :idioma and "
                + "t.professor = :professor "
                + "and t.nivel = :nivel");
        query.setParameter("idioma", idioma);
        query.setParameter("professor", professor);
        query.setParameter("nivel", nivel);
        List objects = query.list();
        endOperation();
        return objects;
    }

    public Turma findByIdiomaCpfNivel(String nome, String cpf, Integer nivel) {
        startOperation();
        Query query = session.createQuery(
                "from Turma t where t.idioma.nome = :nome and "
                + "t.professor.cpf = :cpf and t.nivel = :nivel"
        );
        query.setParameter("nome", nome);
        query.setParameter("cpf", cpf);
        query.setParameter("nivel", nivel);
        List objects = query.list();
        Object obj = null;
        if (objects.size() > 0) {
            obj = query.list().get(0);
        }
        endOperation();
        return (Turma) obj;
    }

    public List<Object[]> findEstudantesJoinTurma(Integer turmaId) {
        startOperation();
        Query query = session.createQuery("from Estudante e join e.turmas t where t.id = :turmaId");
        query.setParameter("turmaId", turmaId);
        List objects = query.list();
        endOperation();
        return objects;
    }
}
