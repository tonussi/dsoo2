package br.ufsc.service;

import br.ufsc.model.*;
import java.util.List;

import org.hibernate.*;

public class PessoaService extends GenericService {

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

    public Pessoa findByCpf(String cpf) {
        startOperation();
        Query q = session.createQuery("FROM Pessoa WHERE cpf = :cpf");
        q.setParameter("cpf", cpf);
        List objects = q.list();
        Pessoa o = null;
        if (objects.size() > 0) {
            o = (Pessoa) q.list().get(0);
        }
        endOperation();
        return o;
    }
}
