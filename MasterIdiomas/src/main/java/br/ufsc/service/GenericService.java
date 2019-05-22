package br.ufsc.service;

import java.util.List;
import org.hibernate.*;

public abstract class GenericService {

    Session session;
    Transaction tx;

    public GenericService() {
        HibernateFactory.buildIfNeeded();
    }

    public void saveOrUpdate(Object obj) {
        try {
            startOperation();
            session.saveOrUpdate(obj);
            endOperation();
        } catch (HibernateException e) {
            handleException(e);
        }
    }

    public void delete(Object obj) {
        startOperation();
        session.delete(obj);
        endOperation();
    }

    public List findAll(Class clazz) {
        startOperation();
        Query query = session.createQuery("from " + clazz.getName());
        List objects = query.list();
        endOperation();
        return objects;
    }

    public Object findById(Class clazz, Integer id) {
        startOperation();
        Object obj = session.load(clazz, id);
        endOperation();
        return obj;
    }

    public void handleException(HibernateException e) {
        HibernateFactory.rollback(tx);
//        new br.ufsc.ui.Mensagem(e.toString()).launch();
    }

    public void startOperation() {
        session = HibernateFactory.openSession();
        tx = session.beginTransaction();
    }

    public void endOperation() {
        tx.commit();
        HibernateFactory.close(session);
        
    }

}
