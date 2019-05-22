package br.ufsc.service;

import br.ufsc.model.*;
import java.util.List;
import org.hibernate.Query;

public class IdiomaService extends GenericService {

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
        super.saveOrUpdate(obj);
    }

    public Idioma findByNome(String nome) {
        startOperation();
        Query query = session.createQuery("from Idioma where nome = :nome");
        query.setParameter("nome", nome.toUpperCase());
        List objects = query.list();
        Object obj = null;
        if (objects.size() > 0) {
            obj = objects.get(0);
        }
        endOperation();
        return (Idioma) obj;
    }
}
